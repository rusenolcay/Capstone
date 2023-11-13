package com.rusen.capstoneproject.ui.success

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.data.source.CartRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SuccessViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    fun clearCart() {
        viewModelScope.launch{
            FirebaseAuth.getInstance().currentUser?.let { user ->
                try {
                    cartRepository.clearCart(user.uid)
                } catch (e: Exception) {
                    showMessage.value = e.message
                }
            }
        }
    }
}