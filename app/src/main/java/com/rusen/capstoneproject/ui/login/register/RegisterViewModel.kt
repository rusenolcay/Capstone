package com.rusen.capstoneproject.ui.login.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.ui.BaseViewModel

class RegisterViewModel : BaseViewModel() {

    private val navigateHome = MutableLiveData<Boolean>()
    val navigateHomeEvent: LiveData<Boolean> = navigateHome

    fun attemptRegister(email: String, password: String) {
        if (email.isBlank()) {
            showResourceMessage.value = R.string.email_address
        } else if (password.length < 6) {
            showResourceMessage.value = R.string.password_message
        } else {
            register(email, password)
        }
    }

    private fun register(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                navigateHome.value = true
            }.addOnFailureListener {
                showResourceMessage.value = R.string.try_again
            }
    }
}