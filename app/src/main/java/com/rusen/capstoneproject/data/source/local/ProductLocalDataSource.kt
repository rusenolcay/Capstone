package com.rusen.capstoneproject.data.source.local

import com.rusen.capstoneproject.data.model.Product
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProductLocalDataSource @Inject constructor(
    private val dao: ProductDao,
    private val ioDispatcher: CoroutineContext
) {

    suspend fun deleteByProductId(productId: Long) = withContext(ioDispatcher) {
        dao.deleteByProductId(productId)
    }

    suspend fun deleteAllProducts() = withContext(ioDispatcher) {
        dao.deleteAllProducts()
    }

    suspend fun getProducts() = withContext(ioDispatcher) {
        dao.getProducts()
    }

    suspend fun getProduct(id: Long) = withContext(ioDispatcher) {
        dao.getProduct(id)
    }

    suspend fun deleteProduct(product: Product) = withContext(ioDispatcher) {
        dao.deleteProduct(product)
    }

    suspend fun addProduct(product: Product) = withContext(ioDispatcher) {
        dao.addProduct(product)
    }
}