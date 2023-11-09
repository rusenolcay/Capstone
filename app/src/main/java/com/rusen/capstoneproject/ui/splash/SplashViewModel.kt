package com.rusen.capstoneproject.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.ui.BaseViewModel

class SplashViewModel : BaseViewModel() {

    companion object {
        private const val DELAY = 3000L
    }

    private val navigateHome = MutableLiveData<Boolean>()
    val navigateHomeEvent: LiveData<Boolean> = navigateHome

    private val navigateLogin = MutableLiveData<Boolean>()
    val navigateLoginEvent: LiveData<Boolean> = navigateLogin

    init {
        /**
         * Looper kullanarak 3000 ms' lik cekme yaratiyoruz.
         */
        Handler(Looper.getMainLooper()).postDelayed({
            startNextScreen()
        }, DELAY)
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