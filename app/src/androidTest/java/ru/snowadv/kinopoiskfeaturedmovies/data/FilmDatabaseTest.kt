package ru.snowadv.kinopoiskfeaturedmovies.data

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test

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
class FilmDatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)



    @Inject
    lateinit var db: FilmsDb
    @Inject
    lateinit var dao: FilmsDao

    @Inject
    lateinit var favoriteFilmEntity: FavoriteFilmEntity
    @Inject
    lateinit var filmInfoEntity: FilmInfoEntity
    @Inject
    lateinit var film: Film

    @Inject
    lateinit var typeConverter: DatabaseTypeConverter

    @Before
    fun setup() {
        hiltRule.inject()
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