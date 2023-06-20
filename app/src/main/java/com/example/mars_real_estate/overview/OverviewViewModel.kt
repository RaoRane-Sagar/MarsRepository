package com.example.mars_real_estate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mars_real_estate.network.MarsApi
import com.example.mars_real_estate.network.MarsApiFilter
import com.example.mars_real_estate.network.MarsProperty
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


enum class MarsApiStatus{
    ERROR, LOADING, DONE
}
class OverviewViewModel: ViewModel() {


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> get() = _status

    private val _marsProperties = MutableLiveData<List<MarsProperty>>()
    val marsProperties: LiveData<List<MarsProperty>> get() = _marsProperties

    private val _navigateToPropertyDetails = MutableLiveData<MarsProperty>()
    val navigateToPropertyDetails: LiveData<MarsProperty> get() = _navigateToPropertyDetails

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {

        coroutineScope.launch {

            var getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)

            try{
                _status.value = MarsApiStatus.LOADING
                var listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                _marsProperties.value = listResult
            }catch (e:Exception){
                _status.value = MarsApiStatus.ERROR
                _marsProperties.value = ArrayList()
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateFilter(filter: MarsApiFilter){
        getMarsRealEstateProperties(filter)
    }

}