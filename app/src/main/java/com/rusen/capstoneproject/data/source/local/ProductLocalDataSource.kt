package com.rusen.capstoneproject.data.source.local

import com.rusen.capstoneproject.CapstoneApplication
import com.rusen.capstoneproject.data.model.Product
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor() {

    fun deleteByProductId(productId: Long) {
        CapstoneApplication.dao?.deleteByProductId(productId)
    }

    fun deleteAllProducts() {
        CapstoneApplication.dao?.deleteAllProducts()
    }

    fun getProducts(): List<Product>? {
        return CapstoneApplication.dao?.getProducts()
    }

    fun getProduct(id: Long): Product? {
        return CapstoneApplication.dao?.getProduct(id)
    }

    fun deleteProduct(product: Product){
        CapstoneApplication.dao?.deleteProduct(product)
    }
    fun addProduct(product: Product){
        CapstoneApplication.dao?.addProduct(product)
    }
}