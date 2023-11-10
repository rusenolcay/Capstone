package com.rusen.capstoneproject.ui.success

import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.data.source.remote.CartRemoteDataSource
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor() : BaseViewModel() {

    private val cartRemoteDataSource = CartRemoteDataSource()

    fun clearCart() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRemoteDataSource.clearCart(
                onSuccess = { },
                onFailure = {
                    showMessage.value = it
                },
                userId = user.uid
            )
        }
    }
}