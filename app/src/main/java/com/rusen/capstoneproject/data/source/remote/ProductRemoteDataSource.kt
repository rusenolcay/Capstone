package com.rusen.capstoneproject.data.source.remote

import android.util.Log
import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.GetProductsResponse
import com.rusen.capstoneproject.data.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRemoteDataSource {

    private val service = CapstoneApplication.productService

    fun getProducts(
        onSuccess: (List<Product>?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        service?.getProducts()?.enqueue(object :
            Callback<GetProductsResponse> {
            override fun onResponse(
                call: Call<GetProductsResponse>,
                response: Response<GetProductsResponse>
            ) {
                val result = response.body()
                if (result?.status == 200) {
                    onSuccess(result.products)
                } else {
                    onFailure(result?.message)
                }
            }

            override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                Log.e("GetProducts", t.message.orEmpty())
            }
        })
    }
}