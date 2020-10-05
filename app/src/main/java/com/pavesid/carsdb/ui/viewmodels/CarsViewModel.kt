package com.pavesid.carsdb.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.repositories.CarRepository
import com.pavesid.carsdb.util.Constants
import com.pavesid.carsdb.util.Event
import com.pavesid.carsdb.util.Resource
import kotlinx.coroutines.launch

class CarsViewModel @ViewModelInject constructor(
    private val repository: CarRepository
) : ViewModel() {

    val carItems = repository.observeAllCarItem()

    private val _insertCarItemStatus = MutableLiveData<Event<Resource<CarItem>>>()
    val insertCarItemStatus: LiveData<Event<Resource<CarItem>>> = _insertCarItemStatus

    fun deleteCarItem(carItem: CarItem) = viewModelScope.launch {
        repository.deleteCarItem(carItem)
    }

    fun insertCarItemIntoDb(carItem: CarItem) = viewModelScope.launch {
        repository.insertCarItem(carItem)
    }

    fun insertCarItem(
        carBrand: String,
        carModel: String,
        carClass: String,
        engineType: String,
        carPrice: String
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
        val carItem = CarItem( carBrand, carModel, carClass, engineType, carPrice )
        insertCarItemIntoDb(carItem)
        _insertCarItemStatus.postValue(Event(Resource.success(carItem)))
    }
}