package com.rusen.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val updateUI = MutableLiveData<Resource<List<Product>>>()
    val updateUIEvent: LiveData<Resource<List<Product>>> = updateUI

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            updateUI.value = Resource.Loading
            try {
                val products = productRepository.getProducts()
                updateUI.value = Resource.Success(products ?: emptyList())
            } catch (e: Exception) {
                updateUI.value = Resource.Error(e)
            }
        }
    }

    fun onChangedFavoriteStatus(product: Product) {
        if (product.favorite) {
            product.id?.let { deleteFavoriteProductById(it) }
            product.favorite = false
        } else {
            product.favorite = true
            addFavoriteProduct(product)
        }
    }

    private fun addFavoriteProduct(product: Product) {
        viewModelScope.launch {
            productRepository.addFavoriteProduct(product)
        }
    }

    private fun deleteFavoriteProductById(productId: Long) {
        viewModelScope.launch {
            productRepository.deleteFavoriteProductById(productId)
        }
    }
}
