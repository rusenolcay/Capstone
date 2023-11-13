package com.rusen.capstoneproject.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rusen.capstoneproject.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProduct(id: Long): Product?

    @Insert
    suspend fun addProduct(vararg products: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM product WHERE id = :productId")
    suspend fun deleteByProductId(productId: Long)
}