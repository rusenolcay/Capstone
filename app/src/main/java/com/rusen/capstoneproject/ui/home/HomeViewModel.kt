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
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val updateUI = MutableLiveData<Resource<List<Product>>>()
    val updateUIEvent: LiveData<Resource<List<Product>>> = updateUI

    private val productRepository = ProductRepository()

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
}
