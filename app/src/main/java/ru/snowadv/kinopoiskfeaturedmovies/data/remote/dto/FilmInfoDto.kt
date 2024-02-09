package ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto

import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo

data class FilmInfoDto(
    val kinopoiskId: Long,
    val countries: List<CountryDto>,
    val coverUrl: String?,
    val description: String,
    val filmLength: Int?,
    val genres: List<GenreDto>,
    val logoUrl: String?,
    val nameEn: String?,
    val nameOriginal: String,
    val nameRu: String,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingKinopoisk: Double,
    val ratingKinopoiskVoteCount: Int,
    val reviewsCount: Int,
    val shortDescription: String?,
    val slogan: String?,
    val year: Int
) {
    fun toModel(): FilmInfo {
        return FilmInfo(
            kinopoiskId = kinopoiskId,
            countries = countries.map { it.country },
            coverUrl = coverUrl,
            description = description,
            filmLength = filmLength,
            genres = genres.map { it.genre },
            logoUrl = logoUrl,
            nameEn = nameEn,
            nameOriginal = nameOriginal,
            nameRu = nameRu,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            ratingKinopoisk = ratingKinopoisk,
            ratingKinopoiskVoteCount = ratingKinopoiskVoteCount,
            reviewsCount = reviewsCount,
            shortDescription = shortDescription,
            slogan = slogan,
            year = year
        )
    }
}