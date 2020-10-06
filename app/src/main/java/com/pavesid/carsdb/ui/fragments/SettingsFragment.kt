package com.pavesid.carsdb.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.pavesid.carsdb.R
import com.pavesid.carsdb.util.Constants.SORT
import com.pavesid.carsdb.util.Constants.THEME

class SettingsFragment : PreferenceFragmentCompat() {

//    private val viewModel: BaseViewModel by activityViewModels()
//
//    private lateinit var mainActivity: MainActivity
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mainActivity = context as MainActivity
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

//        switchTheme?.setOnPreferenceChangeListener { _, newValue ->
//            if (newValue is Boolean) {
//                viewModel.switchTheme(newValue)
//            }
//            return@setOnPreferenceChangeListener true
//        }
//
//        changeSort?.setOnPreferenceChangeListener { _, newValue ->
//            if (newValue is String) {
//                viewModel.updateSort(newValue)
//            }
//            return@setOnPreferenceChangeListener true
//        }
    }

//    override fun onStart() {
//        super.onStart()
//        mainActivity.supportActionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(true)
//            title = getString(R.string.settings)
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
//    }
}