package com.rusen.capstoneproject.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : BaseViewModel() {

    private val showFavoriteProducts = MutableLiveData<List<Product>?>()
    val showFavoriteProductsEvent: LiveData<List<Product>?> = showFavoriteProducts

    private val productRepository = ProductRepository()

    fun onProductDelete(productId: Long, currentList: MutableList<Product>) {
        productRepository.deleteFavoriteProductById(productId)
        currentList.removeIf { it.id == productId }
        showFavoriteProducts.value = currentList
    }

    fun clearFavorites() {
        productRepository.deleteAllFavoriteProducts()
        showFavoriteProducts.value = emptyList()
    }

    fun getFavoriteProducts() {
        val products = productRepository.getFavoriteProducts()
        showFavoriteProducts.value = products
    }
}
