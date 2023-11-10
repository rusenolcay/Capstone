package com.rusen.capstoneproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : BaseViewModel() {

    private val showSearchProducts = MutableLiveData<List<Product>?>()
    val showSearchProductsEvent: LiveData<List<Product>?> = showSearchProducts

    private val productRepository = ProductRepository()

    fun attemptSearch(query: String?) {
        if (query != null) {
            if (query.length >= 3) {
                getProductsByQuery(query)
            } else {
                showSearchProducts.value = emptyList()
            }
        }
    }

    private fun getProductsByQuery(query: String) {
        productRepository.getProductsByQuery(
            onSuccess = {
                showSearchProducts.value = it
            },
            onFailure = {
                showMessage.value = it
            },
            query = query
        )
    }
}