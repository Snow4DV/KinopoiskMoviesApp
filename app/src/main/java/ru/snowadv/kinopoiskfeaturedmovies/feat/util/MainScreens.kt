package ru.snowadv.kinopoiskfeaturedmovies.feat.util

import ru.snowadv.kinopoiskfeaturedmovies.R

sealed class MainScreen(
    val titleResId: Int,
    val route: String
) {
    data object Popular: MainScreen(R.string.popular, "popular")
    data object Featured: MainScreen(R.string.featured, "featured")
    data object FilmInfo: MainScreen(R.string.film_info, "film")
}