package ru.snowadv.kinopoiskfeaturedmovies.data

import ru.snowadv.kinopoiskfeaturedmovies.data.remote.KinopoiskApi
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmInfoDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmsDto

class MockedKinopoiskApi(private val filmsDto: FilmsDto, private val filmInfoDto: FilmInfoDto): KinopoiskApi {
    override suspend fun getFilms(page: Int, type: String): FilmsDto {
        return filmsDto
    }

    override suspend fun getFilmInfo(id: Long): FilmInfoDto {
        return filmInfoDto
    }

}