package com.alialfayed.weathertask.widget

import com.alialfayed.weathertask.data.ResultData
import com.alialfayed.weathertask.db.CityDao
import com.alialfayed.weathertask.ui.home.repo.HomeRepositoryImp
import javax.inject.Inject

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 9/27/2021 - 3:08 PM
 */
class WidgetRepository @Inject constructor (private val  cityDao : CityDao) {

    suspend fun getCities() = try {
        ResultData.Success(data = cityDao.getAllCities())
    }catch (e : Exception){
        ResultData.Failure(msg = e.message.toString())
    }

}