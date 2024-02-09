package ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto

import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Films

data class FilmsDto(
    val films: List<FilmDto>,
    val pagesCount: Int
) {
    fun toModel(): Films {
        return Films(films.map { it.toModel() }, pagesCount)
    }
}