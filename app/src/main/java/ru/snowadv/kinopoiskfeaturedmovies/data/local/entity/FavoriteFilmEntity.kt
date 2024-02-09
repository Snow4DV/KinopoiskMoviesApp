package ru.snowadv.kinopoiskfeaturedmovies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film

@Entity
data class FavoriteFilmEntity(
    val countries: List<String>,
    @PrimaryKey val filmId: Long,
    val filmLength: String?,
    val genres: List<String>,
    val nameEn: String?,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val year: String
) {
    fun toModel(): Film {
        return Film(
            countries = countries,
            filmId = filmId,
            filmLength = filmLength,
            genres = genres,
            nameEn = nameEn,
            nameRu = nameRu,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            rating = rating,
            ratingVoteCount = ratingVoteCount,
            year = year
        )
    }
}