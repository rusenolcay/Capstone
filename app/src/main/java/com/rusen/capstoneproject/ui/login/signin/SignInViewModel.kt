package com.rusen.capstoneproject.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.R
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : BaseViewModel() {

    private val navigateHome = MutableLiveData<Boolean>()
    val navigateHomeEvent: LiveData<Boolean> = navigateHome

    fun attemptSignIn(email: String, password: String) {
        if (email.isBlank()) {
            showResourceMessage.value = R.string.email_address
        } else if (password.length < 6) {
            showResourceMessage.value = R.string.password_message
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
            showResourceMessage.value = R.string.try_again
        }
    }
}