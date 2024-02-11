package ru.snowadv.kinopoiskfeaturedmovies.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.snowadv.kinopoiskfeaturedmovies.data.MockedFailingKinopoiskApi
import ru.snowadv.kinopoiskfeaturedmovies.data.MockedKinopoiskApi
import ru.snowadv.kinopoiskfeaturedmovies.presentation.event.EventAggregator
import ru.snowadv.kinopoiskfeaturedmovies.presentation.event.EventAggregatorImpl
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.DatabaseTypeConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverterImpl
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDao
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDb
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FavoriteFilmEntity
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmInfoEntity
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.HeaderAuthenticator
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.KinopoiskApi
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.CountryDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmInfoDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmsDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.GenreDto
import ru.snowadv.kinopoiskfeaturedmovies.data.repository.FilmRepositoryImpl
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.domain.repository.FilmRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

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
    fun provideDao(db: FilmsDb): FilmsDao {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application, typeConverter: DatabaseTypeConverter): FilmsDb {
        return Room.inMemoryDatabaseBuilder(app, FilmsDb::class.java)
            .addTypeConverter(typeConverter)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthenticator(): HeaderAuthenticator {
        return HeaderAuthenticator("41829cfe-3cbc-45d0-a240-9fa11f5e37be") // TODO: replace with mine! I already killed two with >500 requests per day xd
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

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext appContext: Context): ImageLoader {
        return ImageLoader(appContext).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(appContext)
                    .maxSizePercent(0.2)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.2)
                    .directory(appContext.cacheDir)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }


    @Provides
    fun provideFavFilm() : FavoriteFilmEntity {
        return FavoriteFilmEntity(
            countries = listOf("Канада", "США"),
            filmId = 1,
            filmLength = "01:23",
            genres = listOf("комедия", "триллер"),
            nameEn = "Film 1",
            nameRu = "Фильм 1",
            posterUrl = "http://example.com/img.jps",
            posterUrlPreview = "http://example.com/smol.jpg",
            rating = "8.5",
            ratingVoteCount = 1000,
            year = "2021"
        )
    }

    @Provides
    fun provideFilmInfoEntity(): FilmInfoEntity {
        return FilmInfoEntity(kinopoiskId = 1234, countries = listOf("Country"), coverUrl =
        "coverUrl", description = "description", filmLength = 120, genres = listOf("Genre"), logoUrl = "logoUrl",
            nameEn = "NameEn", nameOriginal = "NameOriginal", nameRu = "NameRu", posterUrl = "posterUrl",
            posterUrlPreview = "posterUrlPreview", ratingKinopoisk = 7.5, ratingKinopoiskVoteCount = 100,
            reviewsCount = 10, shortDescription = "shortDescription", slogan = "slogan", year = 2021)

    }

    @Provides
    fun provideFilm(): Film {
        return Film(
            countries = listOf("USA"),
            filmId = 1,
            filmLength = "136",
            genres = listOf("Action", "Sci-Fi"),
            nameEn = "The Matrix",
            nameRu = "Матрица",
            posterUrl = "https://example.com/poster.jpg",
            posterUrlPreview = "https://example.com/poster_preview.jpg",
            rating = "8.7",
            ratingVoteCount = 1500,
            year = "1999"
        )
    }

    @Provides
    fun provideFilmInfoDto(): FilmInfoDto {
        return FilmInfoDto(
            kinopoiskId = 1234,
            countries = listOf(
                CountryDto("USA"),
                CountryDto("Australia")
            ),
            coverUrl = "https://example.com/cover.jpg",
            description = "A computer hacker learns about the true nature of reality",
            filmLength = 136,
            genres = listOf(
                GenreDto("Action"),
                GenreDto("Sci-Fi")
            ),
            logoUrl = "https://example.com/logo.jpg",
            nameEn = "The Matrix",
            nameOriginal = "The Matrix",
            nameRu = "Матрица",
            posterUrl = "https://example.com/poster.jpg",
            posterUrlPreview = "https://example.com/poster_preview.jpg",
            ratingKinopoisk = 8.7,
            ratingKinopoiskVoteCount = 1500,
            reviewsCount = 100,
            shortDescription = "A hacker Neo fights against machines",
            slogan = "Free your mind",
            year = 1999
        )
    }

    @Provides
    fun provideListOfFilms(): List<FilmDto> {
        return listOf(
            FilmDto(
                countries = listOf(CountryDto("USA")),
                filmId = 1,
                filmLength = "1:40",
                genres = listOf(GenreDto("Action"), GenreDto("Sci-Fi")),
                nameEn = "The Matrix",
                nameRu = "Матрица",
                posterUrl = "https://example.com/poster.jpg",
                posterUrlPreview = "https://example.com/poster_preview.jpg",
                rating = "8.7",
                ratingVoteCount = 1500,
                year = "1999",
                isAfisha = 0
            ),
            FilmDto(
                countries = listOf(CountryDto("Canada")),
                filmId = 2,
                filmLength = "02:11",
                genres = listOf(GenreDto("Action"), GenreDto("Sci-Fi")),
                nameEn = "Interstellar",
                nameRu = "Интерстеллар",
                posterUrl = "https://example.com/poster.jpg",
                posterUrlPreview = "https://example.com/poster_preview.jpg",
                rating = "8.7",
                ratingVoteCount = 1000,
                year = "1999",
                isAfisha = 0
            )
        )
    }

    @Provides
    fun provideFilmsDto(films: List<FilmDto>): FilmsDto {
        return FilmsDto(
            films = films,
            pagesCount = 1
        )
    }

    @Provides
    fun provideMockedKinopoiskApiDto(filmsDto: FilmsDto, filmInfoDto: FilmInfoDto): MockedKinopoiskApi {
        return MockedKinopoiskApi(filmsDto, filmInfoDto)
    }

    @Provides
    fun provideMockedFailingKinopoiskApi(): MockedFailingKinopoiskApi {
        return MockedFailingKinopoiskApi()
    }

    @Provides
    fun provideFilmsRepository(api: MockedKinopoiskApi, dao: FilmsDao): FilmRepository {
        return FilmRepositoryImpl(api, dao)
    }

}