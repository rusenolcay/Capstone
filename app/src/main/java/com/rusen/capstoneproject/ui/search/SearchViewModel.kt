package com.rusen.capstoneproject.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.GetProductSearchResponse
import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.ui.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : BaseViewModel() {

    private val showSearchProducts = MutableLiveData<List<Product>?>()
    val showSearchProductsEvent: LiveData<List<Product>?> = showSearchProducts

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
        CapstoneApplication.productService?.getProductsByQuery(query)
            ?.enqueue(object : Callback<GetProductSearchResponse> {
                override fun onResponse(
                    call: Call<GetProductSearchResponse>,
                    response: Response<GetProductSearchResponse>
                ) {
                    val result = response.body()
                    showSearchProducts.value = result?.products
                    if (result?.status != 200) {
                        showMessage.value = result?.message
                    }
                }

                override fun onFailure(call: Call<GetProductSearchResponse>, t: Throwable) {
                    Log.e("GetProductSearchResponse", t.message.orEmpty())
                }
            })
    }
}