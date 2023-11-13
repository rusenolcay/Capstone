package com.rusen.capstoneproject.data.source

import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.local.ProductLocalDataSource
import com.rusen.capstoneproject.data.source.remote.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource
) {

    suspend fun getProductDetail(id: Long) = remoteDataSource.getProductDetail(id)

    suspend fun getProducts(): List<Product>? {
        val allProducts = remoteDataSource.getProducts().products
        return mergeWithFavoriteProducts(allProducts)
    }

    suspend fun getProductsByQuery(query: String): List<Product>? {
        val productsByQuery = remoteDataSource.getProductsByQuery(query)
        return mergeWithFavoriteProducts(productsByQuery.products)
    }

    private suspend fun mergeWithFavoriteProducts(allProducts: List<Product>?): List<Product>? {
        val favoriteProducts = localDataSource.getProducts()
        allProducts?.forEach { product ->
            favoriteProducts.find { favoriteProduct ->
                favoriteProduct.id == product.id
            }?.let {
                product.favorite = true
            }
        }
        return allProducts
    }

    suspend fun getFavoriteProduct(id: Long) = localDataSource.getProduct(id)

    suspend fun deleteFavoriteProduct(product: Product) = localDataSource.deleteProduct(product)

    suspend fun addFavoriteProduct(product: Product) {
        localDataSource.addProduct(product)
    }

    suspend fun deleteFavoriteProductById(id: Long) {
        localDataSource.deleteByProductId(id)
    }

    suspend fun deleteAllFavoriteProducts() {
        localDataSource.deleteAllProducts()
    }

    suspend fun getFavoriteProducts(): List<Product> {
        return localDataSource.getProducts()
    }
}