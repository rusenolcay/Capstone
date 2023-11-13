package com.rusen.capstoneproject.data.source.remote

import com.rusen.capstoneproject.data.model.AddToCardRequest
import com.rusen.capstoneproject.data.model.AddToCardResponse
import com.rusen.capstoneproject.data.model.ClearCartRequest
import com.rusen.capstoneproject.data.model.GetDeleteFromCartRequest
import com.rusen.capstoneproject.data.model.GetDeleteFromCartResponse
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(
    private val service: ProductService
) {

    suspend fun getCartProducts(userId: String) = service.getProductsCart(userId)


    suspend fun clearCart(userId: String) {
        val request = ClearCartRequest(userId)
        service.clearCart(request)
    }

    suspend fun deleteProductFromCart(
        userId: String,
        productId: Long
    ): GetDeleteFromCartResponse {
        val request = GetDeleteFromCartRequest(productId, userId)
        return service.deleteCart(request)
    }

    suspend fun addProductToCart(
        productId: Long,
        userId: String
    ): AddToCardResponse {
        val request = AddToCardRequest(userId = userId, productId = productId)
        return service.addProductToCart(request)
    }
}