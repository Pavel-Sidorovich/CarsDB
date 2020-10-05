package com.pavesid.carsdb.ui

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ContainerActivityEntryPoint {
    fun getFragmentFactory(): CarsFragmentFactory
}