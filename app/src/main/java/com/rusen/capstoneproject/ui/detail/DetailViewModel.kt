package com.rusen.capstoneproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.CartRepository
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productRepository : ProductRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {

    private val showProductDetail = MutableLiveData<Resource<Product>>()
    val showProductDetailEvent: LiveData<Resource<Product>> = showProductDetail

    private val changeFavoriteStatus = MutableLiveData<Boolean>()
    val changeFavoriteStatusEvent: LiveData<Boolean> = changeFavoriteStatus

    fun getProductDetail(id: Long) {
        productRepository.getProductDetail(
            id = id,
            onSuccess = {
                showProductDetail.value = Resource.Success(it)
                showFavoriteStatus(id)
            },
            onFailure = {
                showProductDetail.value = Resource.Error(it)
            })
    }

    private fun showFavoriteStatus(id: Long) {
        val product = productRepository.getFavoriteProduct(id)
        changeFavoriteStatus.value = product?.favorite ?: false
    }

    fun addProductToCart(productId: Long) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRepository.addProductToCart(
                onSuccess = {
                    showMessage.value = it
                },
                onFailure = {
                    showMessage.value = it
                },
                productId = productId,
                userId = user.uid
            )
        }
    }

    fun toggleFavoriteStatus(remoteProduct: Product) {
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