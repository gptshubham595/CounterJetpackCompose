package com.oops.counterjc

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.dec
import kotlin.inc

class MainViewModel: ViewModel() {
    var counter = MutableLiveData(0)

    val increment = {
        counter.value = counter.value?.plus(1)
    }
    val decrement = {
        counter.value = counter.value?.minus(1)
    }

}