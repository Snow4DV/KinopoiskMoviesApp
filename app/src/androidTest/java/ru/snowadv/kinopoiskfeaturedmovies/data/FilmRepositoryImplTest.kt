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
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDao
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDb
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmInfoDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmsDto
import ru.snowadv.kinopoiskfeaturedmovies.data.repository.FilmRepositoryImpl
import ru.snowadv.kinopoiskfeaturedmovies.di.AppModule
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.domain.repository.FilmRepository
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.Resource
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class)
class FilmRepositoryImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)



    @Inject
    lateinit var db: FilmsDb
    @Inject
    lateinit var dao: FilmsDao

    @Inject
    lateinit var filmsDtoList: List<FilmDto>
    @Inject
    lateinit var filmsDto: FilmsDto // that will contain films from filmsDtoList, it is returned by api method to get films page
    @Inject
    lateinit var filmInfoDto: FilmInfoDto // that will be returned by api method to get info about film
    @Inject
    lateinit var film: Film

    @Inject
    lateinit var badKinopoiskApi: MockedFailingKinopoiskApi

    @Inject
    lateinit var goodFilmRepository: FilmRepository

    lateinit var badFilmRepository: FilmRepository

    @Before
    fun setup() {
        hiltRule.inject()
        badFilmRepository = FilmRepositoryImpl(badKinopoiskApi, dao)
    }

    @After
    fun cleanup() {
        db.close()
    }

    @Test
    fun getPopularFilms_withoutCache() = runBlocking {
        var loadingEmitted = false
        var successEmitted = false
        goodFilmRepository.getPopularFilms(1).collect { resource ->
            if (resource is Resource.Loading && !loadingEmitted && !successEmitted) {
                loadingEmitted = true
                assert(resource.data == null)
            } else if (resource is Resource.Success && loadingEmitted && !successEmitted) {
                successEmitted = true
                assert(resource.data == filmsDto.toModel())
            }
        }
        assert(loadingEmitted)
        assert(successEmitted)
    }

    @Test
    fun getPopularFilms_noInternet_withoutCache() = runBlocking {
        var loadingEmitted = false
        var errorEmitted = false
        badFilmRepository.getPopularFilms(1).collect { resource ->
            if (resource is Resource.Loading && !loadingEmitted && !errorEmitted) {
                loadingEmitted = true
                assert(resource.data == null)
            } else if (resource is Resource.Error && loadingEmitted && !errorEmitted) {
                errorEmitted = true
                assert(resource.data == null)
            }
        }
        assert(loadingEmitted)
        assert(errorEmitted)
    }


    @Test
    fun getPopularFilms_withCache() = runBlocking {
        dao.insertFilms(filmsDto.toModel().toEntity(1))
        var loadingEmitted = false
        var loadingWithDataEmitted = false
        var successEmitted = false
        goodFilmRepository.getPopularFilms(1).collect { resource ->
            if (resource is Resource.Loading && !successEmitted) {
                loadingEmitted = true
                if(resource.data != null) {
                    loadingWithDataEmitted = true
                }
            } else if (resource is Resource.Success && loadingEmitted && !successEmitted) {
                successEmitted = true
                assert(resource.data == filmsDto.toModel())
            }
        }
        assert(loadingEmitted)
        assert(successEmitted)
        assert(loadingWithDataEmitted)
    }

    @Test
    fun getPopularFilms_withCache_noInternet() = runBlocking {
        dao.insertFilms(filmsDto.toModel().toEntity(1))
        var loadingEmitted = false
        var loadingWithDataEmitted = false
        var errorEmitted = false
        badFilmRepository.getPopularFilms(1).collect { resource ->
            if (resource is Resource.Loading && !errorEmitted) {
                loadingEmitted = true
                if(resource.data != null) {
                    loadingWithDataEmitted = true
                }
            } else if (resource is Resource.Error && loadingEmitted && !errorEmitted) {
                errorEmitted = true
                assert(resource.data == filmsDto.toModel())
                assert(resource.message != null)
            }
        }
        assert(loadingEmitted)
        assert(errorEmitted)
        assert(loadingWithDataEmitted)
    }




    @Test
    fun getFilmInfo_withoutCache() = runBlocking {
        var loadingEmitted = false
        var successEmitted = false
        goodFilmRepository.getFilmInfo(filmInfoDto.kinopoiskId).collect { resource ->
            if (resource is Resource.Loading && !loadingEmitted && !successEmitted) {
                loadingEmitted = true
                assert(resource.data == null)
            } else if (resource is Resource.Success && loadingEmitted && !successEmitted) {
                successEmitted = true
                assert(resource.data == filmInfoDto.toModel())
            }
        }
        assert(loadingEmitted)
        assert(successEmitted)
    }

    @Test
    fun getFilmInfo_noInternet_withoutCache() = runBlocking {
        var loadingEmitted = false
        var errorEmitted = false
        badFilmRepository.getFilmInfo(filmInfoDto.kinopoiskId).collect { resource ->
            if (resource is Resource.Loading && !loadingEmitted && !errorEmitted) {
                loadingEmitted = true
                assert(resource.data == null)
            } else if (resource is Resource.Error && loadingEmitted && !errorEmitted) {
                errorEmitted = true
                assert(resource.data == null)
            }
        }
        assert(loadingEmitted)
        assert(errorEmitted)
    }




    @Test
    fun getFilmInfo_withCache() = runBlocking {
        dao.insertFilmInfo(filmInfoDto.toModel().toEntity())
        var loadingEmitted = false
        var loadingWithDataEmitted = false
        var successEmitted = false
        goodFilmRepository.getFilmInfo(filmInfoDto.kinopoiskId).collect { resource ->
            if (resource is Resource.Loading && !successEmitted) {
                loadingEmitted = true
                if(resource.data != null) {
                    loadingWithDataEmitted = true
                }
            } else if (resource is Resource.Success && loadingEmitted && !successEmitted) {
                successEmitted = true
                assert(resource.data == filmInfoDto.toModel())
            }
        }
        assert(loadingEmitted)
        assert(successEmitted)
        assert(loadingWithDataEmitted)
    }

    @Test
    fun getFilmInfo_withCache_noInternet() = runBlocking {
        dao.insertFilmInfo(filmInfoDto.toModel().toEntity())
        var loadingEmitted = false
        var loadingWithDataEmitted = false
        var errorEmitted = false
        badFilmRepository.getFilmInfo(filmInfoDto.kinopoiskId).collect { resource ->
            if (resource is Resource.Loading && !errorEmitted) {
                loadingEmitted = true
                if(resource.data != null) {
                    loadingWithDataEmitted = true
                }
            } else if (resource is Resource.Error && loadingEmitted && !errorEmitted) {
                errorEmitted = true
                assert(resource.data == filmInfoDto.toModel())
                assert(resource.message != null)
            }
        }
        assert(loadingEmitted)
        assert(errorEmitted)
        assert(loadingWithDataEmitted)
    }

    @Test
    fun addFavoriteFilmsFromRoomDb() = runBlocking {
        goodFilmRepository.addFavoriteFilm(film)
        assert(dao.getFavoriteFilms().first() .first().toModel() == film)
    }


    @Test
    fun getFavoriteFilmFromRoomDb() = runBlocking {
        dao.addFavoriteFilm(film.toFavEntity())
        assert(goodFilmRepository.getFavoriteFilms().first() .first() == film)
    }


    @Test
    fun removeFavoriteFilmFromRoomDb() = runBlocking {
        assert(dao.getFavoriteFilms().first().isEmpty())
        dao.addFavoriteFilm(film.toFavEntity())
        assert(dao.getFavoriteFilms().first().isNotEmpty())
        goodFilmRepository.removeFavoriteFilm(film.filmId)
        assert(dao.getFavoriteFilms().first().isEmpty())
    }

    @Test
    fun addFilmsAndGetIdSet() = runBlocking {
        val films = filmsDtoList.map { it.toModel() }
        films.forEach {
            dao.addFavoriteFilm(it.toFavEntity())
        }

        val idSet = goodFilmRepository.getFavoriteFilmsIds().first()
        assert(idSet.size == films.size)
        films.forEach {
            assert(it.filmId in idSet)
        }
    }

    @Test
    fun removeFilmsAndCheckIfIdsAreAbsent() = runBlocking {
        val films = filmsDtoList.map { it.toModel() }
        films.forEach {
            dao.addFavoriteFilm(it.toFavEntity())
        }

        films.forEach {
            goodFilmRepository.removeFavoriteFilm(it.filmId)
        }

        assert(goodFilmRepository.getFavoriteFilmsIds().first().isEmpty())
    }





}