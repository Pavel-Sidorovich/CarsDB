package com.pavesid.carsdb.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pavesid.carsdb.R
import com.pavesid.carsdb.adapters.CarItemAdapter
import com.pavesid.carsdb.databinding.FragmentCarsBinding
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import javax.inject.Inject

class CarsFragment @Inject constructor(
    val carItemAdapter: CarItemAdapter,
    var viewModel: CarsViewModel? = null
) : Fragment(R.layout.fragment_cars) {

    private var _binding: FragmentCarsBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarsBinding.inflate(layoutInflater)

        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)

        subscribeToObservers()
        setupView()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = carItemAdapter.carItems[pos]
            when (direction) {
                LEFT -> {
                    viewModel?.deleteCarItem(item)
                    Snackbar.make(requireView(), getString(R.string.deleted_item), Snackbar.LENGTH_SHORT).apply {
                        setAction(getString(R.string.undo)) {
                            viewModel?.insertCarItemIntoDb(item)
                        }
                        show()
                    }
                }
                RIGHT -> {
                    Snackbar.make(requireView(), "Вправо", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(
                        CarsFragmentDirections.actionCarsFragmentToAddCarItemFragment(item)
                    )
//                    val action = SpecifyAmountFragmentDirections.confirmationAction(amount)
//                    v.findNavController().navigate(action)
                }
            }

        }
    }

    private fun setupView() {
        binding.recycler.apply {
            adapter = carItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(
                CarsFragmentDirections.actionCarsFragmentToAddCarItemFragment()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel?.carItems?.observe(viewLifecycleOwner) {
            carItemAdapter.carItems = it
        }
    }
}