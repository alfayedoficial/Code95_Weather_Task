package com.alialfayed.weathertask.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.alialfayed.weathertask.R
import com.alialfayed.weathertask.core.utils.AppConstants.RELOAD_START
import com.alialfayed.weathertask.core.utils.AppConstants.WEATHER_API_IMAGE_ENDPOINT
import com.alialfayed.weathertask.core.utils.AppConstants.WIDGET_REQUEST_CODE
import com.alialfayed.weathertask.core.utils.setLogCat
import com.alialfayed.weathertask.data.ResultData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 10/5/2021 - 1:51 AM
 */
@AndroidEntryPoint
class WeatherWidget : AppWidgetProvider() {

    private var views: RemoteViews? = null
    private var appWidgetManage: AppWidgetManager? = null
    private var appWidgetId = 0
    @Inject lateinit var widgetRepository: WidgetRepository

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (RELOAD_START == intent.action) {
            views = RemoteViews(context.packageName, R.layout.widget_weather)

            views?.run {
                CoroutineScope(Dispatchers.Main.immediate).launch{
                    val result = withContext(Dispatchers.Main){
                        widgetRepository.getCities()
                    }
                    when (result) {
                        is ResultData.Failure -> result.msg?.let { msg -> setLogCat("TEST_Widget" , msg)}
                        is ResultData.Loading -> { }
                        is ResultData.Internet -> { }
                        is ResultData.Success -> { result.data?.let {models ->

                            val model = models.first()
                            setTextViewText(R.id.tvCity, model.name)
                            setTextViewText(R.id.tvTemp, model.temp.toString())
                            val appWidgetTarget = AppWidgetTarget(context, R.id.imgWeather, views, appWidgetId)

                            val iconCode = model.icon?.replace("n", "d")
                            val margeLink = "${WEATHER_API_IMAGE_ENDPOINT}$iconCode@4x.png"
                            Glide.with(context.applicationContext)
                                .asBitmap()
                                .load(margeLink)
                                .into(appWidgetTarget)
                        }}
                    }
                }
                appWidgetManage!!.updateAppWidget(appWidgetId, views)
            }
        }
    }

    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        this.appWidgetManage = appWidgetManager
        this.appWidgetId = appWidgetId
        views = RemoteViews(context.packageName, R.layout.widget_weather)

        views?.run {
            CoroutineScope(Dispatchers.Main.immediate).launch{
                val result = withContext(Dispatchers.Main){
                    widgetRepository.getCities()
                }
                when (result) {
                    is ResultData.Failure -> result.msg?.let { msg -> setLogCat("TEST_Widget" , msg)}
                    is ResultData.Loading -> { }
                    is ResultData.Internet -> { }
                    is ResultData.Success -> { result.data?.let {models ->

                        val model = models.first()
                        setTextViewText(R.id.tvCity, model.name)
                        setTextViewText(R.id.tvTemp, model.temp.toString())
                        val appWidgetTarget = AppWidgetTarget(context, R.id.imgWeather, views, appWidgetId)

                        val iconCode = model.icon?.replace("n", "d")
                        val margeLink = "${WEATHER_API_IMAGE_ENDPOINT}$iconCode@4x.png"
                        Glide.with(context.applicationContext)
                            .asBitmap()
                            .load(margeLink)
                            .into(appWidgetTarget)
                    }}
                }
            }

            // Instruct the widget manager to update the widget
            val intent = Intent(context, WeatherWidget::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, WIDGET_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            setOnClickPendingIntent(R.id.reload, getPendingSelfIntent(context, RELOAD_START))
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }


    }

    private fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent {
        val intent = Intent(context, WeatherWidget::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(context, WIDGET_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

}