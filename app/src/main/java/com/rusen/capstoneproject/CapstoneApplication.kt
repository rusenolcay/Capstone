package com.rusen.capstoneproject

import android.app.Application
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.rusen.capstoneproject.data.source.local.CapstoneDatabase
import com.rusen.capstoneproject.data.source.local.ProductDao
import com.rusen.capstoneproject.data.source.remote.ProductService
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class CapstoneApplication : Application() {
    companion object {
        private const val BASE_URL = "https://api.canerture.com/ecommerce/"
        private const val STORE = "store"
        private const val STORE_NAME = "ZA'R'AZ"

        var productService: ProductService? = null
        var dao: ProductDao? = null
    }

    override fun onCreate() {
        super.onCreate()
        productService = provideRetrofit()
        dao = provideDatabase().productDao()
    }

    fun provideRetrofit(): ProductService {
        val chucker = ChuckerInterceptor.Builder(this).build()
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

    fun provideDatabase() = Room.databaseBuilder(
        applicationContext,
        CapstoneDatabase::class.java, "database-name"
    ).allowMainThreadQueries().build()
}