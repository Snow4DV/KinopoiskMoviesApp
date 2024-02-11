package ru.snowadv.kinopoiskfeaturedmovies

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Module
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.DatabaseTypeConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDao
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDb
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FavoriteFilmEntity
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmInfoEntity
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmsEntity
import ru.snowadv.kinopoiskfeaturedmovies.di.AppModule
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
class FilmsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)



    @Inject
    lateinit var db: FilmsDb
    @Inject
    lateinit var dao: FilmsDao


    private lateinit var favoriteFilmEntity: FavoriteFilmEntity
    private lateinit var filmInfoEntity: FilmInfoEntity
    private lateinit var film: Film

    @Inject
    lateinit var typeConverter: DatabaseTypeConverter

    @Before
    fun setup() {
        hiltRule.inject()
        favoriteFilmEntity = FavoriteFilmEntity(
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
        filmInfoEntity = FilmInfoEntity(kinopoiskId = 1234, countries = listOf("Country"), coverUrl =
            "coverUrl", description = "description", filmLength = 120, genres = listOf("Genre"), logoUrl = "logoUrl",
                    nameEn = "NameEn", nameOriginal = "NameOriginal", nameRu = "NameRu", posterUrl = "posterUrl",
            posterUrlPreview = "posterUrlPreview", ratingKinopoisk = 7.5, ratingKinopoiskVoteCount = 100,
            reviewsCount = 10, shortDescription = "shortDescription", slogan = "slogan", year = 2021)


        film = Film(
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

    @After
    fun cleanup() {
        db.close()
    }

    @Test
    fun insertFilms() = runBlocking {
        dao.insertFilmInfo(filmInfoEntity)
        val filmsEntity = FilmsEntity(page = 1, films = listOf(film), pagesCount = 1)
        dao.insertFilms(filmsEntity)
        val result = dao.getFilmsPage(1)
        assertEquals(result, filmsEntity)
    }

    @Test
    fun insertFilmInfo() = runBlocking {
        dao.insertFilmInfo(filmInfoEntity)

        val result = dao.getFilmInfo(1234)
        assertEquals(result, filmInfoEntity)
    }

    @Test
    fun getFavoriteFilms() = runBlocking {
        dao.addFavoriteFilm(favoriteFilmEntity)

        val result = dao.getFavoriteFilms().first()
        assert(favoriteFilmEntity in result)
    }

    @Test
    fun getFavoriteIds() = runBlocking {
        dao.addFavoriteFilm(favoriteFilmEntity)
        val result = dao.getFavoriteIds().first()
        assert(favoriteFilmEntity.filmId in result)
    }

    @Test
    fun addFavoriteFilmTwice() = runBlocking {
        dao.addFavoriteFilm(favoriteFilmEntity)
        dao.addFavoriteFilm(favoriteFilmEntity)

        val result = dao.getFavoriteFilms().first()
        assert(result.count { it == favoriteFilmEntity } == 1)
    }

    @Test
    fun removeFavoriteFilm() = runBlocking {
        dao.addFavoriteFilm(favoriteFilmEntity)

        dao.removeFavoriteFilm(favoriteFilmEntity.filmId)

        val result = dao.getFavoriteFilms().first()
        assert(favoriteFilmEntity !in result)
    }
}