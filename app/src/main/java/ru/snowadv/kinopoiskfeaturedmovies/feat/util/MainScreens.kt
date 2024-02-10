package ru.snowadv.kinopoiskfeaturedmovies.feat.util

import ru.snowadv.kinopoiskfeaturedmovies.R

sealed class MainScreen(
    val titleResId: Int,
    val route: String,
    val noArgRoute: String = route
) {
    data object Home: MainScreen(R.string.popular, "home")
    data object FilmInfo: MainScreen(R.string.film_info, "film?id={id}", "film")
}