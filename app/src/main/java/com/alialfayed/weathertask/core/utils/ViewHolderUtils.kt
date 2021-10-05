package com.alialfayed.weathertask.core.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 7/26/2021 - 9:35 AM
 */

fun getBindingRow(parent: ViewGroup, @LayoutRes resId: Int): ViewDataBinding {
    return DataBindingUtil.inflate(LayoutInflater.from(parent.context), resId, parent, false)
}

