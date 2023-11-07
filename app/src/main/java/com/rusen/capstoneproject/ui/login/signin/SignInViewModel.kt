package com.rusen.capstoneproject.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.ui.BaseViewModel

class SignInViewModel : BaseViewModel() {

    private val navigateHome = MutableLiveData<Boolean>()
    val navigateHomeEvent: LiveData<Boolean> = navigateHome

    fun attemptSignIn(email: String, password: String) {
        if (email.isBlank()) {
            showMessage.value = R.string.email_address
        } else if (password.length < 6) {
            showMessage.value = R.string.password_message
        } else {
            signIn(email, password)
        }
    }

    private fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            password
        ).addOnSuccessListener {
            navigateHome.value = true
        }.addOnFailureListener {
            showMessage.value = R.string.try_again
        }
    }
}