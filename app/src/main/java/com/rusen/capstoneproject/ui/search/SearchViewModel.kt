package com.rusen.capstoneproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.remote.ProductRemoteDataSource
import com.rusen.capstoneproject.ui.BaseViewModel

class SearchViewModel : BaseViewModel() {

    private val showSearchProducts = MutableLiveData<List<Product>?>()
    val showSearchProductsEvent: LiveData<List<Product>?> = showSearchProducts

    private val productRemoteDataSource = ProductRemoteDataSource()

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
        productRemoteDataSource.getProductsByQuery(
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