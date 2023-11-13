package com.rusen.capstoneproject.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val showFavoriteProducts = MutableLiveData<List<Product>?>()
    val showFavoriteProductsEvent: LiveData<List<Product>?> = showFavoriteProducts

    fun onProductDelete(productId: Long, currentList: MutableList<Product>) {
        viewModelScope.launch {
            productRepository.deleteFavoriteProductById(productId)
            currentList.removeIf { it.id == productId }
            showFavoriteProducts.value = currentList
        }
    }

    fun clearFavorites() {
        viewModelScope.launch {
            productRepository.deleteAllFavoriteProducts()
            showFavoriteProducts.value = emptyList()
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch {
            val products = productRepository.getFavoriteProducts()
            showFavoriteProducts.value = products
        }
    }
}
