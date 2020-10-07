package com.pavesid.carsdb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.pavesid.carsdb.R
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import com.pavesid.carsdb.util.Constants.SORT
import com.pavesid.carsdb.util.Constants.THEME
import javax.inject.Inject

class SettingsFragment @Inject constructor(var viewModel: CarsViewModel?) :
    PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(CarsViewModel::class.java)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViews()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initViews() {
        val changeSort: ListPreference? = findPreference(SORT)
        val switchTheme: SwitchPreferenceCompat? = findPreference(THEME)

        switchTheme?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is Boolean) {
                viewModel?.switchTheme(newValue)
            }
            return@setOnPreferenceChangeListener true
        }

        changeSort?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue is String) {
                viewModel?.updateSort(newValue)
            }
            return@setOnPreferenceChangeListener true
        }
    }
}