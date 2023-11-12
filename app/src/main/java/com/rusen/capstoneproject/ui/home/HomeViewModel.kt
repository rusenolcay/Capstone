package com.rusen.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val updateUI = MutableLiveData<Resource<List<Product>>>()
    val updateUIEvent: LiveData<Resource<List<Product>>> = updateUI

    fun getProducts() {
        updateUI.value = Resource.Loading
        productRepository.getProducts(
            onSuccess = {
                updateUI.value = Resource.Success(it ?: emptyList())
            },
            onFailure = {
                updateUI.value = Resource.Error(it)
            }
        )
    }

    fun onChangedFavoriteStatus(product: Product) {
        if (product.favorite) {
            product.id?.let { productRepository.deleteFavoriteProductById(it) }
            product.favorite = false
        } else {
            product.favorite = true
            productRepository.addFavoriteProduct(product)
        }
    }
}
