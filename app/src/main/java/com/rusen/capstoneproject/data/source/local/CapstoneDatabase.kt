package com.rusen.capstoneproject.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rusen.capstoneproject.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class CapstoneDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}