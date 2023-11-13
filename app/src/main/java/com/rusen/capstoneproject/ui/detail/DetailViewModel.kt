package com.rusen.capstoneproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.CartRepository
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {

    private val showProductDetail = MutableLiveData<Resource<Product>>()
    val showProductDetailEvent: LiveData<Resource<Product>> = showProductDetail

    private val changeFavoriteStatus = MutableLiveData<Boolean>()
    val changeFavoriteStatusEvent: LiveData<Boolean> = changeFavoriteStatus

    fun getProductDetail(id: Long) {
        viewModelScope.launch {
            try {
                val productDetail = productRepository.getProductDetail(id)
                productDetail.product?.let {
                    showProductDetail.value = Resource.Success(productDetail.product)
                    showFavoriteStatus(id)
                } ?: run {
                    showProductDetail.value = Resource.Error(UnknownError(productDetail.message))
                }
            } catch (e: Exception) {
                showProductDetail.value = Resource.Error(e)
            }
        }
    }

    private fun showFavoriteStatus(id: Long) {
        viewModelScope.launch {
            val product = productRepository.getFavoriteProduct(id)
            changeFavoriteStatus.value = product?.favorite ?: false
        }
    }

    fun addProductToCart(productId: Long) {
        viewModelScope.launch {
            FirebaseAuth.getInstance().currentUser?.let { user ->
                try {
                    val response = cartRepository.addProductToCart(
                        productId = productId,
                        userId = user.uid
                    )
                    showMessage.value = response.message
                } catch (e: Exception) {
                    showMessage.value = e.message
                }
            }
        }
    }

    fun toggleFavoriteStatus(remoteProduct: Product) {
        viewModelScope.launch {
            val localProduct = remoteProduct.id?.let { productRepository.getFavoriteProduct(it) }
            if (localProduct?.favorite == true) {
                productRepository.deleteFavoriteProduct(localProduct)
                changeFavoriteStatus.value = false
            } else {
                remoteProduct.favorite = true
                productRepository.addFavoriteProduct(remoteProduct)
                changeFavoriteStatus.value = true
            }
        }
    }
}