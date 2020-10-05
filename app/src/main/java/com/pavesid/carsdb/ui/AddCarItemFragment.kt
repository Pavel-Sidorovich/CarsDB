package com.pavesid.carsdb.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.pavesid.carsdb.R
import com.pavesid.carsdb.databinding.FragmentAddCarItemBinding
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel

class AddCarItemFragment : Fragment() {

    private var _binding: FragmentAddCarItemBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CarsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarItemBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}