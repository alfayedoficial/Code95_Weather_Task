package com.alialfayed.weathertask.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastCityDao {

    // insert new ForecastCity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertForecastCity(forecastCity : ForecastCityModel) : Long

    // Get forecastCity
    @Query("select * from forecast_city_table where id =:id")
    suspend fun getForecastCity(id : Long): ForecastCityModel

    // Get size forecastCities
    @Query("select COUNT(*) FROM forecast_city_table")
    suspend fun getSizeForecastCities(): Long

    // Delete all forecastCities
    @Query("delete from forecast_city_table")
    suspend fun deleteAllForecastCities()

    // Delete forecastCity by id
    @Query("delete from forecast_city_table where id =:id")
    suspend fun deleteForecastCity(id : Long)



}