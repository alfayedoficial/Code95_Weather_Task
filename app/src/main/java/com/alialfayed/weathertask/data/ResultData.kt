package com.alialfayed.weathertask.data

sealed class ResultData<T>(val data: T? = null, val msg: String? = null) {
    class Success<T>(data: T) : ResultData<T>(data)
    class Failure<T>(data: T? = null, msg: String?) : ResultData<T>(data, msg)
    class Loading<T> : ResultData<T>()
    class Internet<T> : ResultData<T>()

}