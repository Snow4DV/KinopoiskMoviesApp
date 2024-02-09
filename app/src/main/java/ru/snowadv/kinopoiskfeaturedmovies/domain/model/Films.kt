package ru.snowadv.kinopoiskfeaturedmovies.domain.model

import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmsEntity

data class Films(
    val films: List<Film>,
    val pagesCount: Int
) {
    fun toEntity(page: Int): FilmsEntity {
        return FilmsEntity(
            page = page,
            films = films,
            pagesCount = pagesCount
        )
    }
}