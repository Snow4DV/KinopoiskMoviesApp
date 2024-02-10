package ru.snowadv.kinopoiskfeaturedmovies.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.snowadv.comapr.presentation.EventAggregator
import ru.snowadv.comapr.presentation.EventAggregatorImpl
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.DatabaseTypeConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverterImpl
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDao
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDb
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.HeaderAuthenticator
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.KinopoiskApi
import ru.snowadv.kinopoiskfeaturedmovies.data.repository.FilmRepositoryImpl
import ru.snowadv.kinopoiskfeaturedmovies.domain.repository.FilmRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideJsonConverter(gson: Gson): JsonConverter {
        return JsonConverterImpl(gson)
    }

    @Provides
    @Singleton
    fun provideRoomTypeConverter(jsonConverter: JsonConverter): DatabaseTypeConverter {
        return DatabaseTypeConverter(jsonConverter)
    }

    @Provides
    @Singleton
    fun provideFilmRepo(api: KinopoiskApi, dao: FilmsDao): FilmRepository {
        return FilmRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideDao(db: FilmsDb): FilmsDao {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application, typeConverter: DatabaseTypeConverter): FilmsDb {
        return Room.databaseBuilder(app, FilmsDb::class.java, "films_database")
            .addTypeConverter(typeConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthenticator(): HeaderAuthenticator {
        return HeaderAuthenticator("41829cfe-3cbc-45d0-a240-9fa11f5e37be")
    }

    @Provides
    @Singleton
    fun provideInterceptor(authenticator: HeaderAuthenticator): Interceptor {
        return authenticator
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS).build()
    }


    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient, factory: GsonConverterFactory): KinopoiskApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(KinopoiskApi.BASE_URL)
            .addConverterFactory(factory)
            .build()
            .create(KinopoiskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventAggregator(): EventAggregator {
        return EventAggregatorImpl()
    }

}