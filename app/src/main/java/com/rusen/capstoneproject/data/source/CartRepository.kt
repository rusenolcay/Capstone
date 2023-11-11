package com.rusen.capstoneproject.data.source

import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.remote.CartRemoteDataSource

class CartRepository {

    private val cartRemoteDataSource = CartRemoteDataSource()

    fun addProductToCart(
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit,
        productId: Long,
        userId: String
    ) {
        cartRemoteDataSource.addProductToCart(onSuccess, onFailure, productId, userId)
    }

    fun getCartProducts(
        onSuccess: (List<Product>) -> Unit,
        onFailure: (String?) -> Unit,
        userId: String
    ) {
        cartRemoteDataSource.getCartProducts(onSuccess, onFailure, userId)
    }

    fun clearCart(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        userId: String
    ) {
        cartRemoteDataSource.clearCart(onSuccess, onFailure, userId)
    }

    fun deleteProductFromCart(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        userId: String,
        productId: Long
    ) {
        cartRemoteDataSource.deleteProductFromCart(
            onSuccess,
            onFailure,
            userId,
            productId
        )
    }
}