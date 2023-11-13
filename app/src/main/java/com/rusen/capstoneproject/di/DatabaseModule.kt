package com.rusen.capstoneproject.di

import android.content.Context
import androidx.room.Room
import com.rusen.capstoneproject.data.source.local.CapstoneDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoritesRoomDB(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            CapstoneDatabase::class.java,
            "database-name.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideProductFavoriteDAO(database: CapstoneDatabase) = database.productDao()
}