package com.alialfayed.weathertask.ui.home.viewmodel


import androidx.lifecycle.*
import com.alialfayed.weathertask.data.ResultData
import com.alialfayed.weathertask.domain.model.WeatherCityResponse
import com.alialfayed.weathertask.ui.home.repo.HomeRepository
import com.alialfayed.weathertask.domain.model.placeId.Location
import com.alialfayed.weathertask.db.CityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :ViewModel() {


    var isGpsEnabled = MutableLiveData<Boolean>()
    var isNetworkAvailable = MutableLiveData<Boolean>()
    var isPermissionGranted = MutableLiveData<Boolean>()
    val searchText = MutableLiveData<String>()
    var placeMapArrayList =  mutableListOf<String>()
    val placeIdArrayList = mutableListOf<String>()

    fun setGpsStatus(value: Boolean) {
        isGpsEnabled.postValue(value)
    }

    fun setNetworkAvailable(value: Boolean) {
        isNetworkAvailable.postValue(value)
    }

    fun setPermissionGranted(value: Boolean) {
        isPermissionGranted.postValue(value)
    }


    val readyToFetch = MediatorLiveData<Map<String, Boolean>>().apply {
        addSource(isGpsEnabled) { gps ->
            value = isNetworkAvailable.value?.let { network ->
                isPermissionGranted.value?.let { permission ->
                    mapOf(
                        "permission" to permission,
                        "network" to network,
                        "gps" to gps
                    )
                }
            }
        }
        addSource(isNetworkAvailable) { network ->
            value = isGpsEnabled.value?.let { gps ->
                isPermissionGranted.value?.let { permission ->
                    mapOf(
                        "permission" to permission,
                        "network" to network,
                        "gps" to gps
                    )
                }
            }
        }
        addSource(isPermissionGranted) { permission ->
            value = isGpsEnabled.value?.let { gps ->
                isNetworkAvailable.value?.let { network ->
                    mapOf(
                        "permission" to permission,
                        "network" to network,
                        "gps" to gps
                    )
                }
            }
        }
    }


    fun fetchWeatherByCity(city:String) = homeRepository.fetchWeatherForLocation(city).asLiveData()

    fun fetchWeatherLatLong() = homeRepository.getWeatherOfLatLon().asLiveData()

    fun getWeatherOfLatLon(location : Location?) =  homeRepository.getWeatherOfLatLon(location).asLiveData()

    fun getGooglePlaces(searchText:String) = homeRepository.getGooglePlaces(searchText).asLiveData()

    suspend fun getGooglePlaceOfLatLon(placeId:String) = homeRepository.getGooglePlaceOfLatLon(placeId)

    suspend fun insertCity(city: CityModel) = homeRepository.insertCity(city)

    suspend fun getCities() = homeRepository.getCities()

    suspend fun getSizeCities() = homeRepository.getSizeCities()

    suspend fun deleteCity(id : Long) = homeRepository.deleteCity(id)

    suspend fun deleteForecastCity(id : Long) = homeRepository.deleteForecastCity(id)


}