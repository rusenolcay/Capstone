package com.rusen.capstoneproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.common.Resource
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository : ProductRepository
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
        productRepository.getProductsByQuery(
            onSuccess = {
                showSearchProducts.value = Resource.Success(it ?: emptyList())
            },
            onFailure = {
                showSearchProducts.value = Resource.Error(it)
            },
            query = query
        )
    }
}