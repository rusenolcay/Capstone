package com.rusen.capstoneproject.data.source.remote

import com.rusen.capstoneproject.data.model.GetProductDetailResponse
import com.rusen.capstoneproject.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id : Long
    ): Call<GetProductDetailResponse>
}