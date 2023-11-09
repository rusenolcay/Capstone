package com.rusen.capstoneproject.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rusen.capstoneproject.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getProducts(): List<Product>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getProduct(id : Long): Product?

    @Insert
    fun addProduct(vararg products: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("DELETE FROM product")
    fun deleteAllProducts()

    @Query("DELETE FROM product WHERE id = :productId")
    fun deleteByProductId(productId: Long)
}