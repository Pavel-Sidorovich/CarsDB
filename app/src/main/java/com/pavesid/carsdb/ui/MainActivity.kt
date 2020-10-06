package com.pavesid.carsdb.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.pavesid.carsdb.R
import com.pavesid.carsdb.ui.fragments.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val host by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    private val navController by lazy { host.navController }

    /**
     * Use [ContainerActivityEntryPoint] for fragment factory. Else if use @Inject fragmentFactory would be crash after change orientation
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(this, ContainerActivityEntryPoint::class.java)
        val fragmentFactory = entryPoint.getFragmentFactory()
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}