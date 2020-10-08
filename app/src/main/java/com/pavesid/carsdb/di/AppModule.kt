package com.pavesid.carsdb.di

import android.content.Context
import androidx.room.Room
import com.pavesid.carsdb.data.local.CarItemDatabase
import com.pavesid.carsdb.data.local.CarsDao
import com.pavesid.carsdb.data.remote.CarsApi
import com.pavesid.carsdb.repositories.CarRepository
import com.pavesid.carsdb.repositories.DefaultCarRepository
import com.pavesid.carsdb.util.Constants.BASE_URL
import com.pavesid.carsdb.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

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
        dao: CarsDao,
        api: CarsApi
    ) = DefaultCarRepository(dao, api) as CarRepository

    @Singleton
    @Provides
    fun provideCarsApi(): CarsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CarsApi::class.java)
    }
}