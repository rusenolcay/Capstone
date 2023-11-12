package com.rusen.capstoneproject.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.CartRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    private val showCartProducts = MutableLiveData<Resource<List<Product>>>()
    val showCartProductsEvent: LiveData<Resource<List<Product>>> = showCartProducts

    fun getCartProducts() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRepository.getCartProducts(
                onSuccess = {
                    showCartProducts.value = Resource.Success(it)
                },
                onFailure = {
                    showCartProducts.value = Resource.Success(emptyList())
                    showCartProducts.value = Resource.Error(it)

                },
                userId = user.uid
            )
        }
    }

    fun clearCart() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRepository.clearCart(
                onSuccess = {
                    showCartProducts.value = Resource.Success(emptyList())
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
                    currentList.removeIf { it.id == productId }
                    showCartProducts.value = Resource.Success(currentList)
                },
                onFailure = {
                    showMessage.value = it
                },
                userId = user.uid,
                productId = productId
            )

        }
    }
}