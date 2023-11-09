package com.rusen.capstoneproject.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.ui.BaseViewModel

class FavoritesViewModel : BaseViewModel() {

    private val showFavoriteProducts = MutableLiveData<List<Product>?>()
    val showFavoriteProductsEvent: LiveData<List<Product>?> = showFavoriteProducts

    fun onProductDelete(productId: Long, currentList: MutableList<Product>) {
        CapstoneApplication.dao?.deleteByProductId(productId)
        currentList.removeIf { it.id == productId }
        showFavoriteProducts.value = currentList
    }

    fun clearFavorites() {
        CapstoneApplication.dao?.deleteAllProducts()
        showFavoriteProducts.value = emptyList()
    }

    fun getFavoriteProducts(){
        val products = CapstoneApplication.dao?.getProducts()
        showFavoriteProducts.value = products
    }
}
