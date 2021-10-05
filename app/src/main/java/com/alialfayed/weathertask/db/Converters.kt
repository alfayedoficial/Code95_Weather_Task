package com.alialfayed.weathertask.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 1/29/2021 - 4:47 PM
 */
class Converters {

    @TypeConverter
    fun setIntModel(ints: List<ForecastRow>): String = Gson().toJson(ints)

    @TypeConverter
    fun getIntModel(jsonModel: String): List<ForecastRow> {
        val listType: Type = object : TypeToken<List<ForecastRow>>() {}.type
        return Gson().fromJson(jsonModel, listType)
    }


}