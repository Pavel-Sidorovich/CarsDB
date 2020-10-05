package com.pavesid.carsdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pavesid.carsdb.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(this, ContainerActivityEntryPoint::class.java)
        val fragmentFactory = entryPoint.getFragmentFactory()
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}