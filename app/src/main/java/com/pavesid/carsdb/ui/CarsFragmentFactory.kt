package com.pavesid.carsdb.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.pavesid.carsdb.adapters.CarItemAdapter
import com.pavesid.carsdb.ui.fragments.CarsFragment
import com.pavesid.carsdb.ui.fragments.SettingsFragment
import javax.inject.Inject

class CarsFragmentFactory @Inject constructor(
    private val carItemAdapter: CarItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CarsFragment::class.java.name -> CarsFragment(carItemAdapter)
            SettingsFragment::class.java.name -> SettingsFragment(null)
            else -> super.instantiate(classLoader, className)
        }
    }
}