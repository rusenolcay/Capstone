package com.rusen.capstoneproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val showMessage = MutableLiveData<Int>()
    val showMessageEvent: LiveData<Int> = showMessage
}