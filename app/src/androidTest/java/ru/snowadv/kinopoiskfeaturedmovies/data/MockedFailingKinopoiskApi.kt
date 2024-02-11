package ru.snowadv.kinopoiskfeaturedmovies.data


import retrofit2.HttpException
import retrofit2.Response
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.KinopoiskApi
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmInfoDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmsDto
import java.io.IOException

class MockedFailingKinopoiskApi(): KinopoiskApi {
    override suspend fun getFilms(page: Int, type: String): FilmsDto {
        throw IOException()
    }

    override suspend fun getFilmInfo(id: Long): FilmInfoDto {
        throw IOException()
    }

}