package com.alialfayed.weathertask.ui.details.repo

import com.alialfayed.weathertask.data.ResultData
import com.alialfayed.weathertask.db.CityModel
import com.alialfayed.weathertask.db.ForecastCityDao
import com.alialfayed.weathertask.db.ForecastCityModel
import com.alialfayed.weathertask.domain.api.ApiService
import com.alialfayed.weathertask.domain.api.BaseDataSource

class DetailsRepositoryImp(
    private val apiService: ApiService,
    private val forecastCityDao : ForecastCityDao
    ) : BaseDataSource() {

    // Retrofit Api
    suspend fun findCityForecastData(city:String) = getResult {
        apiService.findCityForecastData(city)
    }

    // Local Room

    suspend fun insertForecastCity(forecastCity: ForecastCityModel) = try {
        ResultData.Success(data = forecastCityDao.insertForecastCity(forecastCity))
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }

    suspend fun getForecastCity(id : Long) = try {
        ResultData.Success(data = forecastCityDao.getForecastCity(id))
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }




}