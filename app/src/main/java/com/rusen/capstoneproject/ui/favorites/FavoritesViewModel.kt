package com.rusen.capstoneproject.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.local.ProductLocalDataSource
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor() : BaseViewModel() {

    private val showFavoriteProducts = MutableLiveData<List<Product>?>()
    val showFavoriteProductsEvent: LiveData<List<Product>?> = showFavoriteProducts

    private val localDataSource = ProductLocalDataSource()

    fun onProductDelete(productId: Long, currentList: MutableList<Product>) {
        localDataSource.deleteByProductId(productId)
        currentList.removeIf { it.id == productId }
        showFavoriteProducts.value = currentList
    }

    fun clearFavorites() {
        localDataSource.deleteAllProducts()
        showFavoriteProducts.value = emptyList()
    }

    fun getFavoriteProducts() {
        val products = localDataSource.getProducts()
        showFavoriteProducts.value = products
    }
}
