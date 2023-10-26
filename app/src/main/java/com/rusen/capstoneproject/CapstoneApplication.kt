package com.rusen.capstoneproject

import android.app.Application
import com.rusen.capstoneproject.data.source.remote.ProductService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CapstoneApplication : Application() {

    companion object {
        private const val BASE_URL = "https://api.canerture.com/ecommerce/"
        private const val STORE = "store"
        private const val STORE_NAME = "ZA'R'AZ"

        var productService: ProductService? = null
    }

    override fun onCreate() {
        super.onCreate()
        productService = provideRetrofit()
    }

    private fun provideRetrofit(): ProductService {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header(STORE, STORE_NAME)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ProductService::class.java)
    }
}