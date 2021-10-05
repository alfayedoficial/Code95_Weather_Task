package com.alialfayed.weathertask.core.utils

import com.alialfayed.weathertask.BuildConfig


/**
 * Created by ( Eng Ali Al Fayed)
 * Class do : Constants of Values
 * Date 8/12/2020 - 5:19 PM
 */
object AppConstants {

    const val CITY = "city"
    const val ID = "id"
    const val REQUEST_TIME_OUT: Long = 60
    const val WEATHER_UNIT = "metric"
    const val WEATHER_API_IMAGE_ENDPOINT = "http://openweathermap.org/img/wn/"

    const val LOCALE="LOCALE"
    const val IS_FIRST="IS_FIRST"
    const val RELOAD_START="RELOAD_START"

    const val GOOGLE_PLACE_API_QUERY : String = "key"
    const val WEATHER_API_QUERY :String = "appid"
    const val BASE_URL_RETROFIT_API: String = BuildConfig.SERVER_URL
    const val BASE_URL_GOOGLE_PLACES_API: String = BuildConfig.GOOGLE_URL
    const val WEATHER_API_KEY :String = BuildConfig.WEATHER_API_KEY
    const val GOOGLE_PLACE_API_KEY : String = BuildConfig.GOOGLE_PLACE_API_KEY

    const val MULTIPLE_LOCATION_PERMISSION = 1
    const val WIDGET_REQUEST_CODE = 0
    const val LOCATION_SETTINGS_REQUEST = 1
    const val BACK_PRESS_INTERVAL: Long = 3 * 1000
    const val SPLASH_TIME_OUT: Long = 4000
    const val UPDATE_INTERVAL_SECS = 10L
    const val FASTEST_UPDATE_INTERVAL_SECS = 2L
}