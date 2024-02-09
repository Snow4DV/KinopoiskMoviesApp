package ru.snowadv.kinopoiskfeaturedmovies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Films

@Entity
data class FilmsEntity(
    @PrimaryKey val page: Int? = null,
    val films: List<Film>,
    val pagesCount: Int
) {
    fun toModel(): Films {
        return Films(films, pagesCount)
    }
}