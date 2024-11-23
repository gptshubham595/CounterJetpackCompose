package com.oops.counterjc

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var counter = MutableLiveData(0)

    val increment = {
        counter.value = counter.value?.plus(1)
    }
    val decrement = {
        counter.value = counter.value?.minus(1)
    }

}