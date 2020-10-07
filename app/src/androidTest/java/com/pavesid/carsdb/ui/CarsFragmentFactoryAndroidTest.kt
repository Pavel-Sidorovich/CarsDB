package com.pavesid.carsdb.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.pavesid.carsdb.adapters.CarItemAdapter
import com.pavesid.carsdb.repositories.FakeCarRepositoryAndroidTest
import com.pavesid.carsdb.ui.fragments.CarsFragment
import com.pavesid.carsdb.ui.fragments.SettingsFragment
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import javax.inject.Inject

class CarsFragmentFactoryAndroidTest @Inject constructor(
    private val carItemAdapter: CarItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            CarsFragment::class.java.name -> CarsFragment(
                carItemAdapter,
                CarsViewModel(FakeCarRepositoryAndroidTest())
            )
            SettingsFragment::class.java.name -> SettingsFragment(
                CarsViewModel(FakeCarRepositoryAndroidTest())
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}