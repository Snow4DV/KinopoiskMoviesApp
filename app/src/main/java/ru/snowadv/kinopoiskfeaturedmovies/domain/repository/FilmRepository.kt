package ru.snowadv.kinopoiskfeaturedmovies.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Films
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.Resource

interface FilmRepository {
    fun getFilms(page: Int = 1): Flow<Resource<Films>>
    fun getFilmInfo(id: Long): Flow<Resource<FilmInfo>>
}