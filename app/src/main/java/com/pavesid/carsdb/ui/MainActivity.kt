package com.pavesid.carsdb.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.pavesid.carsdb.R
import com.pavesid.carsdb.ui.fragments.SettingsFragment
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import com.pavesid.carsdb.util.Constants.SORT
import com.pavesid.carsdb.util.Constants.THEME
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val host by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    private val navController by lazy { host.navController }

    val viewModel: CarsViewModel by viewModels()

    /**
     * Use [ContainerActivityEntryPoint] for fragment factory. If use @Inject fragmentFactory than would be crash after change orientation
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        val entryPoint =
            EntryPointAccessors.fromActivity(this, ContainerActivityEntryPoint::class.java)
        val fragmentFactory = entryPoint.getFragmentFactory()
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initValues()
        subscribeToObservers()

        val appBarConfiguration = AppBarConfiguration(navGraph = navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_settings -> {
                if (host.childFragmentManager.fragments[0].javaClass != SettingsFragment::class.java) {
                    navController.navigate(R.id.settingsFragment)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initValues() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        viewModel.switchTheme(prefs.getBoolean(THEME, false))
        viewModel.updateSort(
            prefs.getString(SORT, getString(R.string.values_brand))
                ?: getString(R.string.values_brand)
        )
    }

    private fun subscribeToObservers() {
        viewModel.appTheme.observe(this) {
            updateTheme(it)
        }
    }

    private fun updateTheme(mode: Int) {
        delegate.localNightMode = mode
    }
}