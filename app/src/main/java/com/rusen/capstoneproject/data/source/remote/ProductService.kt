package com.rusen.capstoneproject.data.source.remote

import com.rusen.capstoneproject.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>
}