package ru.snowadv.kinopoiskfeaturedmovies.domain.model

import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmInfoEntity

data class FilmInfo(
    val kinopoiskId: Long,
    val countries: List<String>,
    val coverUrl: String?,
    val description: String,
    val filmLength: Int?,
    val genres: List<String>,
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
    fun toEntity(): FilmInfoEntity {
        return FilmInfoEntity(
            kinopoiskId = kinopoiskId,
            countries = countries,
            coverUrl = coverUrl,
            description = description,
            filmLength = filmLength,
            genres = genres,
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