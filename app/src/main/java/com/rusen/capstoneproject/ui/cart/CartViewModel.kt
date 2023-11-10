package com.rusen.capstoneproject.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.CartRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : BaseViewModel() {

    private val showCartProducts = MutableLiveData<List<Product>>()
    val showCartProductsEvent: LiveData<List<Product>> = showCartProducts

    private val cartRepository = CartRepository()

    fun getCartProducts() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRepository.getCartProducts(
                onSuccess = {
                    showCartProducts.value = it
                },
                onFailure = {
                    showCartProducts.value = emptyList()
                    showMessage.value = it
                },
                userId = user.uid
            )
        }
    }

    fun clearCart() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRepository.clearCart(
                onSuccess = {
                    showCartProducts.value = emptyList()
                },
                onFailure = {
                    showMessage.value = it
                },
                userId = user.uid
            )
        }
    }

    fun onProductDelete(productId: Long, currentList: MutableList<Product>) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRepository.deleteProductFromCart(
                onSuccess = {
                    showCartProducts.value = it
                },
                onFailure = {
                    showMessage.value = it
                },
                userId = user.uid,
                productId = productId,
                currentList = currentList
            )

        }
    }
}