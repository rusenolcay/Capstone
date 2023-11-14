package com.rusen.capstoneproject.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val DELAY = 3000L
    }

    private val navigateHome = MutableLiveData<Boolean>()
    val navigateHomeEvent: LiveData<Boolean> = navigateHome

    private val navigateLogin = MutableLiveData<Boolean>()
    val navigateLoginEvent: LiveData<Boolean> = navigateLogin

    init {
        viewModelScope.launch {
            delay(DELAY)
            startNextScreen()
        }
    }

    /**
     * Eger current user varsa Anasayfaya yoksa login ekranına yonlendirir.
     */
    private fun startNextScreen() {
        FirebaseAuth.getInstance().currentUser?.let {
            navigateHome.value = true
        } ?: run {
            navigateLogin.value = true
        }
    }
}