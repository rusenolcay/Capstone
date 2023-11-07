package com.rusen.capstoneproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.rusen.capstoneproject.ui.home.HomeViewModel
import com.rusen.capstoneproject.ui.login.register.RegisterViewModel
import com.rusen.capstoneproject.ui.login.signin.SignInViewModel

val ViewModelFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel() as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel() as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
    }
}