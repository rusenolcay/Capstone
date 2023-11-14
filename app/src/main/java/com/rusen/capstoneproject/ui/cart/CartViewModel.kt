package com.rusen.capstoneproject.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.CartRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : BaseViewModel() {

    private val showCartProducts = MutableLiveData<Resource<List<Product>>>()
    val showCartProductsEvent: LiveData<Resource<List<Product>>> = showCartProducts

    init {
        getCartProducts()
    }

    private fun getCartProducts() {
        showCartProducts.value = Resource.Loading
        viewModelScope.launch {
            FirebaseAuth.getInstance().currentUser?.let { user ->
                try {
                    val cartProducts = cartRepository.getCartProducts(user.uid).products
                    showCartProducts.value = Resource.Success(cartProducts)
                } catch (e: Exception) {
                    showCartProducts.value = Resource.Error(e)
                }
            }
        }
    }

    fun clearCart() {
        showCartProducts.value = Resource.Loading
        viewModelScope.launch {
            FirebaseAuth.getInstance().currentUser?.let { user ->
                try {
                    cartRepository.clearCart(user.uid)
                    showCartProducts.value = Resource.Success(emptyList())
                } catch (e: Exception) {
                    showCartProducts.value = Resource.Error(e)
                }
            }
        }
    }

    fun onProductDelete(productId: Long, currentList: MutableList<Product>) {
        showCartProducts.value = Resource.Loading
        viewModelScope.launch {
            FirebaseAuth.getInstance().currentUser?.let { user ->
                try {
                    val response = cartRepository.deleteProductFromCart(
                        userId = user.uid,
                        productId = productId
                    )
                    showMessage.value = response.message
                    currentList.removeIf { it.id == productId }
                    showCartProducts.value = Resource.Success(currentList)
                } catch (e: Exception) {
                    showCartProducts.value = Resource.Error(e)
                }
            }
        }
    }
}