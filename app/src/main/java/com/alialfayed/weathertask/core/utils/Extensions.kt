package com.alialfayed.weathertask.core.utils

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Response
import javax.inject.Inject


/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 3/7/2021 - 2:24 PM
 */


fun runDelayed(delayMillis: Long = 200, action: () -> Unit) =
    Handler(Looper.myLooper()!!).postDelayed(Runnable(action), delayMillis)

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) =
    Handler(Looper.getMainLooper()).postDelayed(Runnable(action), delayMillis)

/**
 * Push Message LENGTH SHORT
 *
 * @param context the context = getAppInstance().applicationContext
 * @param msg the Message
 * Set Message
 */
fun Activity.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).show()

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (activity != null) {
        activity!!.toast(text, duration)
    }
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, text, duration).show()
}

fun Application.toast(text: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, text, duration).show()
}

fun Activity.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, stringRes, duration).show()

fun Fragment.toast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) =
    activity!!.toast(stringRes, duration)

fun setLogCat(tag: String, msg: String?) {
    Log.i("$tag :", msg!!)
}

fun Activity.setSnackBar(msg: String?): Snackbar {
    return Snackbar.make(findViewById(android.R.id.content), msg!!, Snackbar.LENGTH_LONG)
}

fun Fragment.setSnackBar(msg: String?): Snackbar {
    return activity?.setSnackBar(msg)!!
}

fun Fragment.hideSoftKeyboard() = activity?.hideSoftKeyboard()

fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun Activity.takeFocus(view: View){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.takeFocus(view: View){
   activity?.takeFocus(view)
}

fun Fragment.launchActivity(cls: Class<*>?): Intent {
    return activity?.launchActivity(cls)!!
}

fun Activity.launchActivity(cls: Class<*>?): Intent {
    return Intent(this, cls)
}

fun Activity.launchActivity(cls: Class<*>?, tagString:String, message: String): Intent {
    val intent = Intent(this,cls)
    intent.putExtra(tagString,message)
    return intent
}
class Extensions @Inject constructor(private val androidApplication: Application) {

    /**
     * Handler time 1000 millisecond ==> 1Second
     * throws  ActivityNotFoundException
     */
    fun openWifi() {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                androidApplication.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(androidApplication, "Not installed.", Toast.LENGTH_SHORT).show()
            }
        }, 500.toLong())
    }

    /**
     * Get the network info
     *
     * @param context the context
     * @return network info
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun getNetworkInfo(androidApplication: Application): NetworkInfo? {
        val cm =
            (this.androidApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        return cm.activeNetworkInfo
    }

    /**
     * Check if there is any connectivity
     *
     * @param context the context
     * @return boolean boolean
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnected(): Boolean {
        val info = getNetworkInfo(androidApplication)
        return info != null && info.isConnected
    }

}










