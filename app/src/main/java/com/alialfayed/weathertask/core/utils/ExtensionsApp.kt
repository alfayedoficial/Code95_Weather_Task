package com.alialfayed.weathertask.core.utils

import com.alialfayed.weathertask.core.utils.AppConstants.IS_FIRST
import com.alialfayed.weathertask.core.utils.AppConstants.LOCALE
import java.util.*

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do : App Extensions to Files
 * Date 1/1/2021 - 4:59 PM
 */
object ExtensionsApp {

    fun getLocale(appPreferences: AppPreferences): String =appPreferences.getStringValue(LOCALE, Locale.getDefault().language)
    fun isFirst(appPreferences: AppPreferences): Boolean =appPreferences.getBooleanValue(IS_FIRST , false)


}
