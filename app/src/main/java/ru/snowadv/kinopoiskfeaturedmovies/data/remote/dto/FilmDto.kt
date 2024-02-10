package ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto

import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film

data class FilmDto(
    val countries: List<CountryDto>,
    val filmId: Long,
    val filmLength: String?,
    val genres: List<GenreDto>,
    val isAfisha: Int,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingVoteCount: Int,
    val year: String?
) {
    fun toModel() : Film {
        return Film(
            countries = countries.map { it.country },
            filmId = filmId,
            filmLength = filmLength,
            genres = genres.map { it.genre },
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