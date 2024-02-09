package ru.snowadv.kinopoiskfeaturedmovies.domain.model

import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FavoriteFilmEntity

data class Film(
    val countries: List<String>,
    val filmId: Long,
    val filmLength: String?,
    val genres: List<String>,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val rating: String,
    val ratingVoteCount: Int,
    val year: String
) {
    fun toFavEntity(): FavoriteFilmEntity {
        return FavoriteFilmEntity(
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