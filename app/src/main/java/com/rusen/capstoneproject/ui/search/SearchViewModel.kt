package com.rusen.capstoneproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val showSearchProducts = MutableLiveData<Resource<List<Product>>>()
    val showSearchProductsEvent: LiveData<Resource<List<Product>>> = showSearchProducts

    fun attemptSearch(query: String?) {
        if (query != null) {
            if (query.length >= 3) {
                getProductsByQuery(query)
            } else {
                showSearchProducts.value = Resource.Success(emptyList())
            }
        }
    }

    private fun getProductsByQuery(query: String) {
        viewModelScope.launch {
            try {
                val productsByQuery = productRepository.getProductsByQuery(query)
                showSearchProducts.value = Resource.Success(productsByQuery ?: emptyList())
            } catch (e: Exception) {
                showSearchProducts.value = Resource.Error(e)
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