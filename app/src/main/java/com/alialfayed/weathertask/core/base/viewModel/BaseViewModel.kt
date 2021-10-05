package com.alialfayed.weathertask.core.base.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel(){

    var job: Job = Job()
    var uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    val coroutineContext = job + uiDispatcher

    protected val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->

            GlobalScope.launch { println("Caught $throwable") }
        }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}