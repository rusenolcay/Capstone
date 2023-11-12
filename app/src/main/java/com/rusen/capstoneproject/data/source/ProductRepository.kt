package com.rusen.capstoneproject.data.source

import com.rusen.capstoneproject.data.model.Product
import com.rusen.capstoneproject.data.source.local.ProductLocalDataSource
import com.rusen.capstoneproject.data.source.remote.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localDataSource: ProductLocalDataSource,
    private val remoteDataSource: ProductRemoteDataSource
) {

    fun getProductDetail(
        id: Long,
        onSuccess: (Product) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        return remoteDataSource.getProductDetail(
            id = id,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    fun getProducts(
        onSuccess: (List<Product>?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        remoteDataSource.getProducts({ allProducts ->
            val favoriteProducts = localDataSource.getProducts()
            allProducts?.forEach { product ->
                favoriteProducts?.find { favoriteProduct ->
                    favoriteProduct.id == product.id
                }?.let {
                    product.favorite = true
                }

            }
            onSuccess(allProducts)
        }, onFailure)
    }

    fun getProductsByQuery(
        query: String,
        onSuccess: (List<Product>?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        remoteDataSource.getProductsByQuery(query, { allProducts ->
            val favoriteProducts = localDataSource.getProducts()
            allProducts?.forEach { product ->
                favoriteProducts?.find { favoriteProduct ->
                    favoriteProduct.id == product.id
                }?.let {
                    product.favorite = true
                }

            }
            onSuccess(allProducts)
        }, onFailure)
    }

    fun getFavoriteProduct(id: Long): Product? {
        return localDataSource.getProduct(id)
    }

    fun deleteFavoriteProduct(product: Product) {
        return localDataSource.deleteProduct(product)
    }

    fun addFavoriteProduct(product: Product) {
        localDataSource.addProduct(product)
    }

    fun deleteFavoriteProductById(id: Long) {
        localDataSource.deleteByProductId(id)
    }

    fun deleteAllFavoriteProducts() {
        localDataSource.deleteAllProducts()
    }

    fun getFavoriteProducts(): List<Product>? {
        return localDataSource.getProducts()
    }
}