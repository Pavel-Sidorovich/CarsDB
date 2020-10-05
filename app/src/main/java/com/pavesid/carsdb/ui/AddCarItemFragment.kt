package com.pavesid.carsdb.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pavesid.carsdb.R
import com.pavesid.carsdb.databinding.FragmentAddCarItemBinding
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import com.pavesid.carsdb.util.Status
import javax.inject.Inject

class AddCarItemFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentAddCarItemBinding? = null
    private val binding
        get() = _binding!!

    lateinit var viewModel: CarsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarItemBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)

        subscribeToObservers()

        binding.applyButton.setOnClickListener {
            viewModel.insertCarItem(
                carBrand = binding.brandET.text.toString(),
                carModel = binding.modelET.text.toString(),
                carClass = binding.classSpinner.selectedItem.toString(),
                engineType = binding.engineTypeSpinner.selectedItem.toString(),
                carPrice = binding.priceET.text.toString()
            )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.insertCarItemStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(
                            requireActivity().findViewById(R.id.rootLayout),
                            "Added Car Item",
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
//                        findNavController().popBackStack()
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
}