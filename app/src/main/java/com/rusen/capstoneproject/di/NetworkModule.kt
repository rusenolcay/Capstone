package com.rusen.capstoneproject.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.rusen.capstoneproject.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.canerture.com/ecommerce/"
    private const val STORE = "store"
    private const val STORE_NAME = "ZA'R'AZ"

    @Provides
    @Singleton
    fun provideService(@ApplicationContext context: Context): ProductService {
        val chucker = ChuckerInterceptor.Builder(context).build()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(chucker)
            .addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header(STORE, STORE_NAME)
                    return@Interceptor chain.proceed(builder.build())
                }
            ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ProductService::class.java)
    }
}