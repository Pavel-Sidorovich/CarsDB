package com.pavesid.carsdb.di

import android.content.Context
import androidx.room.Room
import com.pavesid.carsdb.data.local.CarItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(context, CarItemDatabase::class.java)
        .allowMainThreadQueries()
        .build()
}