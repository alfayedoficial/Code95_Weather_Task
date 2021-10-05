package com.alialfayed.weathertask.ui.details.repo

import com.alialfayed.weathertask.core.utils.setLogCat
import com.alialfayed.weathertask.data.ResultData
import com.alialfayed.weathertask.db.CityModel
import com.alialfayed.weathertask.db.ForecastCityModel
import com.alialfayed.weathertask.domain.model.forecest.ForecastCityResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 9/27/2021 - 3:08 PM
 */
class DetailsRepository(
    private val detailsRepositoryImp: DetailsRepositoryImp
) {

    // Retrofit Api
    fun findCityForecastData(city: String): Flow<ResultData<ForecastCityResponse>> = flow {
        emit(detailsRepositoryImp.findCityForecastData(city))
    }

    // Local

    suspend fun insertForecastCity(forecastCity: ForecastCityModel) = detailsRepositoryImp.insertForecastCity(forecastCity)

    suspend fun getForecastCity(id : Long) = detailsRepositoryImp.getForecastCity(id)


}