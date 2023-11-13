package com.rusen.capstoneproject.data.source.local

import com.rusen.capstoneproject.data.model.Product
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val dao: ProductDao
) {

    fun deleteByProductId(productId: Long) {
        dao.deleteByProductId(productId)
    }

    fun deleteAllProducts() {
        dao.deleteAllProducts()
    }

    fun getProducts() = dao.getProducts()

    fun getProduct(id: Long) = dao.getProduct(id)

    fun deleteProduct(product: Product) {
        dao.deleteProduct(product)
    }

    fun addProduct(product: Product) {
        dao.addProduct(product)
    }
}