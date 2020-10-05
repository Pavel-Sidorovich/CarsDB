package com.pavesid.carsdb.di

import android.content.Context
import androidx.room.Room
import com.pavesid.carsdb.data.local.CarItemDatabase
import com.pavesid.carsdb.data.local.CarsDao
import com.pavesid.carsdb.repositories.CarRepository
import com.pavesid.carsdb.repositories.DefaultCarRepository
import com.pavesid.carsdb.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCarItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, CarItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCarDao(
        database: CarItemDatabase
    ) = database.carsDao()

    @Singleton
    @Provides
    fun provideDefaultCarRepository(
        dao: CarsDao
    ) = DefaultCarRepository(dao) as CarRepository
}