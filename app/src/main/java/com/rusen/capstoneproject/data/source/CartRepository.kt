package com.rusen.capstoneproject.data.source

import com.rusen.capstoneproject.data.model.AddToCardResponse
import com.rusen.capstoneproject.data.source.remote.CartRemoteDataSource
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartRemoteDataSource: CartRemoteDataSource
) {

    suspend fun addProductToCart(
        productId: Long,
        userId: String
    ): AddToCardResponse {
        return cartRemoteDataSource.addProductToCart(productId, userId)
    }

    suspend fun getCartProducts(userId: String) = cartRemoteDataSource.getCartProducts(userId)

    suspend fun clearCart(userId: String) = cartRemoteDataSource.clearCart(userId)

    suspend fun deleteProductFromCart(userId: String, productId: Long) =
        cartRemoteDataSource.deleteProductFromCart(userId, productId)
}