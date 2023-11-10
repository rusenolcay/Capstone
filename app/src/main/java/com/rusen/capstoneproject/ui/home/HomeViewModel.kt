package com.rusen.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.remote.ProductRemoteDataSource
import com.rusen.capstoneproject.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val updateUI = MutableLiveData<List<Product>?>()
    val updateUIEvent: LiveData<List<Product>?> = updateUI

    private val productRemoteDataSource = ProductRemoteDataSource()

    fun getProducts() {
        productRemoteDataSource.getProducts(
            onSuccess = {
                updateUI.value = it
            },
            onFailure = {
                showMessage.value = it
            }
        )
    }
}
