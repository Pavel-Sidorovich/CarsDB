package com.pavesid.carsdb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.pavesid.carsdb.R
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.data.remote.responses.BrandsResponse
import com.pavesid.carsdb.data.remote.responses.ModelsResponse
import com.pavesid.carsdb.databinding.FragmentAddCarItemBinding
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import com.pavesid.carsdb.util.Status
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_main.rootLayout

class AddCarItemFragment @Inject constructor(
    var viewModel: CarsViewModel?
) : Fragment() {

    private var _binding: FragmentAddCarItemBinding? = null
    private val binding
        get() = _binding!!

    private var item: CarItem? = null

    private var isStart = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarItemBinding.inflate(layoutInflater)
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)

        viewModel?.getAllBrands()

        arguments?.let { bundle ->
            item = bundle.getSerializable("carItem") as? CarItem
            item?.let { carItem ->
                isStart = true
                selectSpinnerItemByValue(binding.classSpinner, carItem.carClass)
                selectSpinnerItemByValue(binding.engineTypeSpinner, carItem.engineType)
                binding.priceET.setText(carItem.carPrice)
            }
        }

        subscribeToObservers()

        binding.brandSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.brandSpinner.selectedItem?.let {
                    viewModel?.getModelsForBrand(
                        it.toString()
                    )
                }
            }
        }

        binding.applyButton.setOnClickListener {
            viewModel?.insertCarItem(
                carBrand = binding.brandSpinner.selectedItem?.toString() ?: "",
                carModel = binding.modelSpinner.selectedItem?.toString() ?: "",
                carClass = binding.classSpinner.selectedItem?.toString() ?: "",
                engineType = binding.engineTypeSpinner.selectedItem?.toString() ?: "",
                carPrice = binding.priceET.text.toString(),
                carId = item?.id
            )
        }

        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel?.insertCarItemStatus?.observe(viewLifecycleOwner) {
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

        viewModel?.brands?.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        createBrandsSpinner(result.data)
                        if (isStart && item != null) {
                            selectSpinnerItemByValue(binding.brandSpinner, item!!.carBrand)
                        }
                        binding.brandSpinner.visibility = View.VISIBLE
                        binding.progressBarBrand.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message ?: "An unknown error occurred.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.progressBarBrand.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.brandSpinner.visibility = View.GONE
                        binding.progressBarBrand.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel?.models?.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        createModelsSpinner(result.data)
                        if (isStart && item != null) {
                            selectSpinnerItemByValue(binding.modelSpinner, item!!.carModel)
                            isStart = false
                        }
                        binding.modelSpinner.visibility = View.VISIBLE
                        binding.progressBarModel.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message ?: "An unknown error occurred.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        binding.modelSpinner.visibility = View.GONE
                        binding.progressBarModel.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.modelSpinner.visibility = View.GONE
                        binding.progressBarModel.visibility = View.VISIBLE
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

    private fun createBrandsSpinner(response: BrandsResponse?) {
        val arrayList = arrayListOf<String>()
        response?.brands?.forEach {
            arrayList.add(it.Make_Name)
        }
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.brandSpinner.adapter = arrayAdapter
    }

    private fun createModelsSpinner(response: ModelsResponse?) {
        val arrayList = arrayListOf<String>()
        response?.models?.forEach {
            arrayList.add(it.Model_Name)
        }
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.modelSpinner.adapter = arrayAdapter
    }
}