package ru.snowadv.kinopoiskfeaturedmovies

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class KinopoiskFeaturedMoviesApplication: Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: ImageLoader
    override fun newImageLoader(): ImageLoader {
        return imageLoader
    }
}