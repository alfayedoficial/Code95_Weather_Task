package com.alialfayed.weathertask.ui.home.repo

import com.alialfayed.weathertask.data.ResultData
import com.alialfayed.weathertask.domain.api.ApiGooglePlaces
import com.alialfayed.weathertask.domain.api.ApiService
import com.alialfayed.weathertask.domain.api.BaseDataSource
import com.alialfayed.weathertask.db.CityDao
import com.alialfayed.weathertask.db.CityModel

class HomeRepositoryImp(
    private val apiService: ApiService,
    private val apiGooglePlaces : ApiGooglePlaces,
    private val  cityDao : CityDao
) : BaseDataSource() {

    // Retrofit Api
    suspend fun fetchWeatherForLocation(city:String) = getResult {
        apiService.getWeatherOfCity(city)
    }

    suspend fun getWeatherOfLatLon(latitude:String, longitude:String) = getResult {
        apiService.getWeatherOfLatLon(latitude, longitude)
    }

    // Google Places Api
    suspend fun fetchPredictions(searchText: String) = getResult {
        apiGooglePlaces.getPredictions(searchText)
    }

    suspend fun fetchGooglePlaceOfLatLon(placeId: String) = getResult {
        apiGooglePlaces.fetchGooglePlaceOfLatLon(placeId)
    }

    // Local Room
    suspend fun insertCity(city: CityModel) = try {
        ResultData.Success(data = cityDao.insertCity(city))
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }

    suspend fun getCities() = try {
        ResultData.Success(data = cityDao.getAllCities())
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }

    suspend fun getSizeCities() = try {
        ResultData.Success(data = cityDao.getSizeCities())
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }

    suspend fun deleteCity(id : Long) = try {
        ResultData.Success(data = cityDao.deleteCity(id))
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }

    suspend fun deleteForecastCity(id : Long) = try {
        ResultData.Success(data = cityDao.deleteForecastCity(id))
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }




}