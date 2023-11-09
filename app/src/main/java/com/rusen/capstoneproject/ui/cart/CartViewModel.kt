package com.rusen.capstoneproject.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.remote.CartRemoteDataSource
import com.rusen.capstoneproject.ui.BaseViewModel

class CartViewModel : BaseViewModel() {

    private val showCartProducts = MutableLiveData<List<Product>>()
    val showCartProductsEvent: LiveData<List<Product>> = showCartProducts

    private val cartRemoteDataSource = CartRemoteDataSource()

    fun getCartProducts() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRemoteDataSource.getCartProducts(
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
            cartRemoteDataSource.clearCart(
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
            cartRemoteDataSource.deleteProductFromCart(
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