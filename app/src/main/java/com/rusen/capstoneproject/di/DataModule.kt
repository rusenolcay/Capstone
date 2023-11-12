package com.rusen.capstoneproject.di

import com.rusen.capstoneproject.data.source.CartRepository
import com.rusen.capstoneproject.data.source.ProductRepository
import com.rusen.capstoneproject.data.source.local.ProductLocalDataSource
import com.rusen.capstoneproject.data.source.remote.CartRemoteDataSource
import com.rusen.capstoneproject.data.source.remote.ProductRemoteDataSource
import com.rusen.capstoneproject.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // product
    @Provides
    @Singleton
    fun provideProductRemoteDataSource(
        productService: ProductService
    ) = ProductRemoteDataSource(productService)

    @Provides
    @Singleton
    fun provideProductLocalDataSource(
    ) = ProductLocalDataSource()

    @Provides
    @Singleton
    fun provideProductRepository(
        localDataSource: ProductLocalDataSource,
        remoteDataSource: ProductRemoteDataSource
    ) = ProductRepository(localDataSource, remoteDataSource)

    // cart
    @Provides
    @Singleton
    fun provideCartRemoteDataSource(
        productService: ProductService
    ) = CartRemoteDataSource(productService)

    @Provides
    @Singleton
    fun provideCartRepository(
        remoteDataSource: CartRemoteDataSource
    ) = CartRepository(remoteDataSource)
}