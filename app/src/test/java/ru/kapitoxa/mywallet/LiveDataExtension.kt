package ru.kapitoxa.mywallet

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

val <T> LiveData<T>.blockingValue: T?
    get() {
        var value: T? = null
        val latch = CountDownLatch(1)
        GlobalScope.launch(Dispatchers.Main) {
            observeForever {
                value = it
                latch.countDown()
            }
        }
        return if (latch.await(2, TimeUnit.SECONDS)) {
            value
        } else {
            throw Exception("LiveData value was not set within 2 seconds")
        }
    }