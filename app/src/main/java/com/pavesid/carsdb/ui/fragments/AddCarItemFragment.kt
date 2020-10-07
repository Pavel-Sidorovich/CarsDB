package com.pavesid.carsdb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pavesid.carsdb.R
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.databinding.FragmentAddCarItemBinding
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import com.pavesid.carsdb.util.Status
import javax.inject.Inject

class AddCarItemFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentAddCarItemBinding? = null
    private val binding
        get() = _binding!!

    lateinit var viewModel: CarsViewModel

    private var item: CarItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarItemBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)

        arguments?.let { bundle ->
            item = bundle.getSerializable("carItem") as? CarItem
            item?.let { carItem ->
                binding.brandET.setText(carItem.carBrand)
                binding.modelET.setText(carItem.carModel)
                selectSpinnerItemByValue(binding.classSpinner, carItem.carClass)
                selectSpinnerItemByValue(binding.engineTypeSpinner, carItem.engineType)
                binding.priceET.setText(carItem.carPrice)
            }
        }

        subscribeToObservers()

        binding.applyButton.setOnClickListener {
            if (item == null) {
                viewModel.insertCarItem(
                    carBrand = binding.brandET.text.toString(),
                    carModel = binding.modelET.text.toString(),
                    carClass = binding.classSpinner.selectedItem.toString(),
                    engineType = binding.engineTypeSpinner.selectedItem.toString(),
                    carPrice = binding.priceET.text.toString()
                )
            } else {
                viewModel.updateCarItemIntoDb(
                    CarItem(
                        carBrand = binding.brandET.text.toString(),
                        carModel = binding.modelET.text.toString(),
                        carClass = binding.classSpinner.selectedItem.toString(),
                        engineType = binding.engineTypeSpinner.selectedItem.toString(),
                        carPrice = binding.priceET.text.toString(),
                        id = item?.id
                    )
                )
            }
        }

        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.insertCarItemStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(
                            requireActivity().findViewById(R.id.rootLayout),
                            "Added or Update Car Item",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().findViewById(R.id.rootLayout),
                            result.message ?: "An unknown error occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING -> {
                        /* No operation*/
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun selectSpinnerItemByValue(spnr: Spinner, value: String) {
        val adapter: SpinnerAdapter = spnr.adapter
        for (position in 0 until adapter.count) {
            if (adapter.getItem(position) == value) {
                spnr.setSelection(position)
                return
            }
        }
    }
}