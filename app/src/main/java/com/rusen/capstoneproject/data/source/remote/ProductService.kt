package com.rusen.capstoneproject.data.source.remote

import com.rusen.capstoneproject.data.model.AddToCardRequest
import com.rusen.capstoneproject.data.model.AddToCardResponse
import com.rusen.capstoneproject.data.model.GetProductDetailResponse
import com.rusen.capstoneproject.data.model.GetProductSearchResponse
import com.rusen.capstoneproject.data.model.GetProductsCartResponse
import com.rusen.capstoneproject.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {
    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id : Long
    ): Call<GetProductDetailResponse>

    @GET("search_product.php")
    fun getProductsByQuery(
        @Query("query") query : String
    ): Call<GetProductSearchResponse>

    @POST("add_to_cart.php")
    fun addProductToCart(
         @Body request: AddToCardRequest
    ): Call<AddToCardResponse>

    @GET("get_cart_products.php")
    fun getProductsCart(
        @Query("userId") userId : String
    ): Call<GetProductsCartResponse>
}