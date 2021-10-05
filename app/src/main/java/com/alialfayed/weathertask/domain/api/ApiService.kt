package com.alialfayed.weathertask.domain.api

import com.alialfayed.weathertask.core.utils.AppConstants
import com.alialfayed.weathertask.domain.model.WeatherCityResponse
import com.alialfayed.weathertask.domain.model.forecest.ForecastCityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("weather")
  suspend fun getWeatherOfCity(
    @Query("q") q: String,
    @Query("units") units: String = AppConstants.WEATHER_UNIT,
    @Query("appid") appid: String = AppConstants.WEATHER_API_KEY
  ): Response<WeatherCityResponse>

  @GET("weather")
  suspend fun getWeatherOfLatLon(
    @Query("lat") latitude: String,
    @Query("lon") longitude: String,
    @Query("units") units: String = AppConstants.WEATHER_UNIT,
    @Query("appid") appid: String = AppConstants.WEATHER_API_KEY
  ): Response<WeatherCityResponse>

  @GET("forecast")
  suspend fun findCityForecastData(
    @Query("q") q: String,
    @Query("units") units: String = AppConstants.WEATHER_UNIT,
    @Query("appid") appid: String = AppConstants.WEATHER_API_KEY
  ): Response<ForecastCityResponse>

}