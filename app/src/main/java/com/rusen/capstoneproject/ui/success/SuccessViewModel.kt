package com.rusen.capstoneproject.ui.success

import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.data.source.remote.CartRemoteDataSource
import com.rusen.capstoneproject.ui.BaseViewModel

class SuccessViewModel : BaseViewModel() {

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