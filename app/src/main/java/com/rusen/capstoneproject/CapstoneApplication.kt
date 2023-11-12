package com.rusen.capstoneproject

import android.app.Application
import androidx.room.Room
import com.rusen.capstoneproject.data.source.local.CapstoneDatabase
import com.rusen.capstoneproject.data.source.local.ProductDao
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CapstoneApplication : Application() {
    companion object {
        var dao: ProductDao? = null
    }

    override fun onCreate() {
        super.onCreate()
        dao = provideDatabase().productDao()
    }

    fun provideDatabase() = Room.databaseBuilder(
        applicationContext,
        CapstoneDatabase::class.java, "database-name"
    ).allowMainThreadQueries().build()
}