package com.rusen.capstoneproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.local.ProductLocalDataSource
import com.rusen.capstoneproject.data.source.remote.CartRemoteDataSource
import com.rusen.capstoneproject.data.source.remote.ProductRemoteDataSource
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : BaseViewModel() {

    private val showProductDetail = MutableLiveData<Product>()
    val showProductDetailEvent: LiveData<Product> = showProductDetail

    private val changeFavoriteStatus = MutableLiveData<Boolean>()
    val changeFavoriteStatusEvent: LiveData<Boolean> = changeFavoriteStatus

    private val localDataSource = ProductLocalDataSource()
    private val cartRemoteDataSource = CartRemoteDataSource()
    private val productRemoteDataSource = ProductRemoteDataSource()

    fun getProductDetail(id: Long) {
        productRemoteDataSource.getProductDetail(
            id = id,
            onSuccess = {
                showProductDetail.value = it
                showFavoriteStatus(id)
            },
            onFailure = {
                showMessage.value = it
            })
    }

    private fun showFavoriteStatus(id: Long) {
        val product = localDataSource.getProduct(id)
        changeFavoriteStatus.value = product?.favorite ?: false
    }

    fun addProductToCart(productId: Long) {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            cartRemoteDataSource.addProductToCart(
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
        val localProduct = remoteProduct.id?.let { localDataSource.getProduct(it) }
        if (localProduct?.favorite == true) {
            localDataSource.deleteProduct(localProduct)
            changeFavoriteStatus.value = false
        } else {
            remoteProduct.favorite = true
            localDataSource.addProduct(remoteProduct)
            changeFavoriteStatus.value = true
        }
    }
}