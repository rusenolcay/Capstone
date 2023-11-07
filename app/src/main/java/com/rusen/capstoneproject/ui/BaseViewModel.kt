package com.rusen.capstoneproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val showResourceMessage = MutableLiveData<Int>()
    val showResourceMessageEvent: LiveData<Int> = showResourceMessage

    protected val showMessage = MutableLiveData<String>()
    val showMessageEvent: LiveData<String> = showMessage
}