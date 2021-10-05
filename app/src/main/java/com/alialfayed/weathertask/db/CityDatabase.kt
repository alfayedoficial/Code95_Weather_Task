package com.alialfayed.weathertask.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityModel::class , ForecastCityModel::class], version = 2, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun forecastCityDao(): ForecastCityDao

}
