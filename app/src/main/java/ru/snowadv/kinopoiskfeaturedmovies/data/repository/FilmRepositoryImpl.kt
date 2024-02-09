package ru.snowadv.kinopoiskfeaturedmovies.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import ru.snowadv.kinopoiskfeaturedmovies.data.local.FilmsDao
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.KinopoiskApi
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Films
import ru.snowadv.kinopoiskfeaturedmovies.domain.repository.FilmRepository
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.Resource
import java.io.IOException

class FilmRepositoryImpl(
    private val api: KinopoiskApi,
    private val dao: FilmsDao
): FilmRepository {
    override fun getFilms(page: Int): Flow<Resource<Films>> = flow {
        emit(Resource.Loading())
        val cachedFilms = dao.getFilmsPage(page)?.toModel()
        emit(Resource.Loading(cachedFilms))
        try {
            val remoteFilms = api.getFilms(page).toModel()
            dao.insertFilms(remoteFilms.toEntity(page))
            emit(Resource.Success(remoteFilms))
        } catch(e: IOException) {
            emit(Resource.Error("Произошла ошибка при загрузке данных, проверьте подключение к сети", cachedFilms))
        } catch(e: HttpException) {
            emit(Resource.Error("Данные не могут быть получены", cachedFilms))
        }
    }

    override fun getFilmInfo(id: Long): Flow<Resource<FilmInfo>> = flow {
        emit(Resource.Loading())
        val cachedFilm = dao.getFilmInfo(id)?.toModel()
        emit(Resource.Loading(cachedFilm))
        try {
            val remoteFilm = api.getFilmInfo(id).toModel()
            dao.insertFilmInfo(remoteFilm.toEntity())
            emit(Resource.Success(remoteFilm))
        } catch(e: IOException) {
            emit(Resource.Error("Произошла ошибка при загрузке данных, проверьте подключение к сети", cachedFilm))
        } catch(e: HttpException) {
            val error = if(e.code() == 404) "Такой фильм не найден" else "Произошла очень странная ошибка, наши инженеры уже трудятся, не покладая рук"
            emit(Resource.Error(error, cachedFilm))
        }
    }
}