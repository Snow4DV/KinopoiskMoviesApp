package ru.snowadv.kinopoiskfeaturedmovies.domain.model

data class Film(
    val countries: List<String>,
    val filmId: Int,
    val filmLength: String?,
    val genres: List<String>,
    val nameEn: String?,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val year: String
)