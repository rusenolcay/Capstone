package com.rusen.capstoneproject.data.source.remote

import com.rusen.capstoneproject.data.model.AddToCardRequest
import com.rusen.capstoneproject.data.model.AddToCardResponse
import com.rusen.capstoneproject.data.model.ClearCartRequest
import com.rusen.capstoneproject.data.model.GetClearCartResponse
import com.rusen.capstoneproject.data.model.GetDeleteFromCartRequest
import com.rusen.capstoneproject.data.model.GetDeleteFromCartResponse
import com.rusen.capstoneproject.data.model.GetProductDetailResponse
import com.rusen.capstoneproject.data.model.GetProductSearchResponse
import com.rusen.capstoneproject.data.model.GetProductsCartResponse
import com.rusen.capstoneproject.data.model.GetProductsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {
    @GET("get_products.php")
    suspend fun getProducts(): GetProductsResponse

    @GET("get_product_detail.php")
    suspend fun getProductDetail(
        @Query("id") id: Long
    ): GetProductDetailResponse

    @GET("search_product.php")
    suspend fun getProductsByQuery(
        @Query("query") query: String
    ): GetProductSearchResponse

    @POST("add_to_cart.php")
    suspend fun addProductToCart(
        @Body request: AddToCardRequest
    ): AddToCardResponse

    @GET("get_cart_products.php")
    suspend fun getProductsCart(
        @Query("userId") userId: String
    ): GetProductsCartResponse

    @POST("clear_cart.php")
    suspend fun clearCart(
        @Body request: ClearCartRequest
    ): GetClearCartResponse

    @POST("delete_from_cart.php")
    suspend fun deleteCart(
        @Body request: GetDeleteFromCartRequest
    ): GetDeleteFromCartResponse
}