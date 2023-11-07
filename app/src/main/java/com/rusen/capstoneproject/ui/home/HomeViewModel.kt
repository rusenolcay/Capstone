package com.rusen.capstoneproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.GetProductsResponse
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.ui.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : BaseViewModel() {

    private val updateUI = MutableLiveData<List<Product>?>()
    val updateUIEvent: LiveData<List<Product>?> = updateUI

    fun getProducts() {
        CapstoneApplication.productService?.getProducts()?.enqueue(object :
            Callback<GetProductsResponse> {
            override fun onResponse(
                call: Call<GetProductsResponse>,
                response: Response<GetProductsResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    updateUI.value = result.products
                } else {
                    showMessage.value = result?.message
                }
            }

            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                Log.e("GetProducts", t.message.orEmpty())
            }
        })
    }
}
