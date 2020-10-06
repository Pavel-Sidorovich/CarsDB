package com.pavesid.carsdb

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.pavesid.carsdb.ui.fragments.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity() {

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.toolbar, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
//            R.id.action_settings -> {
//                if (host.childFragmentManager.fragments[0].javaClass != SettingsFragment::class.java) {
//                    navController.navigate(R.id.settingsFragment)
//                }
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}