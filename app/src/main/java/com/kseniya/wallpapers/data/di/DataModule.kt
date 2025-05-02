package com.kseniya.wallpapers.data.di

import android.app.DownloadManager
import android.content.Context
import androidx.room.Room
import com.kseniya.wallpapers.data.datasource.local.ImageDatabase
import com.kseniya.wallpapers.data.datasource.remote.PexelsApiService
import com.kseniya.wallpapers.data.downloader.AndroidDownloader
import com.kseniya.wallpapers.data.downloader.Downloader
import com.kseniya.wallpapers.data.repository.ImageRepositoryImpl
import com.kseniya.wallpapers.data.repository.ThemeRepositoryImpl
import com.kseniya.wallpapers.domain.repository.ImageRepository
import com.kseniya.wallpapers.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): ImageDatabase {
            return Room.databaseBuilder(
                context,
                ImageDatabase::class.java,
                "images.db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideApi(): PexelsApiService {
            return Retrofit.Builder()
                .baseUrl(PexelsApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
        }

        @Singleton
        @Provides
        fun provideDownloadManager(@ApplicationContext context: Context): DownloadManager {
            return context.getSystemService(DownloadManager::class.java)
        }
    }

    @Binds
    abstract fun bindMovieRepository(
        repository: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    abstract fun bindThemeRepository(
        repository: ThemeRepositoryImpl
    ): ThemeRepository

    @Binds
    @Singleton
    abstract fun bindDownloader(androidDownloader: AndroidDownloader): Downloader

}