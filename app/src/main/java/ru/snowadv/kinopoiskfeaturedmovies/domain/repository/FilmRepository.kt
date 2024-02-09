package ru.snowadv.kinopoiskfeaturedmovies.domain.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FavoriteFilmEntity
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Films
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.Resource

interface FilmRepository {
    fun getPopularFilms(page: Int = 1): Flow<Resource<Films>>
    fun getFilmInfo(id: Long): Flow<Resource<FilmInfo>>
    fun getFavoriteFilms(): Flow<List<Film>>
    fun getFavoriteFilmsIds(): Flow<Set<Long>>
    suspend fun addFavoriteFilm(film: Film)
    suspend fun removeFavoriteFilm(filmId: Long)
}