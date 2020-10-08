package com.pavesid.carsdb.ui.viewmodels

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.data.remote.responses.BrandsResponse
import com.pavesid.carsdb.data.remote.responses.ModelsResponse
import com.pavesid.carsdb.repositories.CarRepository
import com.pavesid.carsdb.util.Constants
import com.pavesid.carsdb.util.Event
import com.pavesid.carsdb.util.Resource
import kotlinx.coroutines.launch

class CarsViewModel @ViewModelInject constructor(
    private val repository: CarRepository
) : ViewModel() {

    var carItems = repository.observeAllCarItemByBrand()

    private val _insertCarItemStatus = MutableLiveData<Event<Resource<CarItem>>>()
    val insertCarItemStatus: LiveData<Event<Resource<CarItem>>> = _insertCarItemStatus

    private val _brands = MutableLiveData<Event<Resource<BrandsResponse>>>()
    val brands: LiveData<Event<Resource<BrandsResponse>>> = _brands

    private val _models = MutableLiveData<Event<Resource<ModelsResponse>>>()
    val models: LiveData<Event<Resource<ModelsResponse>>> = _models

    private val _appTheme = MutableLiveData<Int>()
    val appTheme: LiveData<Int> = _appTheme

    fun deleteCarItem(carItem: CarItem) = viewModelScope.launch {
        repository.deleteCarItem(carItem)
    }

    fun insertCarItemIntoDb(carItem: CarItem) = viewModelScope.launch {
        repository.insertCarItem(carItem)
    }

    fun updateCarItemIntoDb(carItem: CarItem) = viewModelScope.launch {
        repository.updateCarItem(carItem)
    }

    fun insertCarItem(
        carBrand: String,
        carModel: String,
        carClass: String,
        engineType: String,
        carPrice: String,
        carId: Int? = null
    ) {
        if (carBrand.isEmpty() || carModel.isEmpty() || carClass.isEmpty() || engineType.isEmpty() || carPrice.isEmpty()) {
            _insertCarItemStatus.postValue(Event(Resource.error("The fields must not be empty")))
            return
        }
        if (carBrand.length > Constants.MAX_BRAND_NAME_LENGTH) {
            _insertCarItemStatus.postValue(Event(Resource.error("The brand must not exceed ${Constants.MAX_BRAND_NAME_LENGTH} characters")))
            return
        }
        if (carModel.length > Constants.MAX_MODEL_NAME_LENGTH) {
            _insertCarItemStatus.postValue(Event(Resource.error("The model must not exceed ${Constants.MAX_MODEL_NAME_LENGTH} characters")))
            return
        }
        if (carPrice.length > Constants.MAX_PRICE_LENGTH) {
            _insertCarItemStatus.postValue(Event(Resource.error("The price must not exceed ${Constants.MAX_PRICE_LENGTH} characters")))
            return
        }
        if (carId == null) {
            val carItem = CarItem(carBrand, carModel, carClass, engineType, carPrice)
            insertCarItemIntoDb(carItem)
            _insertCarItemStatus.postValue(Event(Resource.success(carItem)))
        } else {
            val carItem = CarItem(carBrand, carModel, carClass, engineType, carPrice, carId)
            updateCarItemIntoDb(carItem)
            _insertCarItemStatus.postValue(Event(Resource.success(carItem)))
        }

    }

    fun getAllBrands() {
        _brands.value = Event(Resource.loading())
        viewModelScope.launch {
            val response = repository.getAllBrands()
            _brands.value = Event(response)
        }
    }

    fun getModelsForBrand( model: String ) {
        if (model.isEmpty()) {
            return
        }
        _models.value = Event(Resource.loading())
        viewModelScope.launch {
            val response = repository.getModelsForBrand(model)
            _models.value = Event(response)
        }
    }

    fun switchTheme(isDark: Boolean) {
        if (isDark) {
            _appTheme.value = AppCompatDelegate.MODE_NIGHT_YES
        } else {
            _appTheme.value = AppCompatDelegate.MODE_NIGHT_NO
        }
    }

    fun updateSort(newSort: String) {
        carItems = when (newSort) {
            "brand" -> repository.observeAllCarItemByBrand()
            "model" -> repository.observeAllCarItemByModel()
            "price" -> repository.observeAllCarItemByPrice()
            "class" -> repository.observeAllCarItemByClass()
            else -> repository.observeAllCarItemByEngineType()
        }
    }
}