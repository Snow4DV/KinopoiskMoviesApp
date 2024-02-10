package ru.snowadv.kinopoiskfeaturedmovies.feat.util

sealed class NavigationEvent(val route: String) {
    data object ToPopular: NavigationEvent(MainScreen.Popular.route)
    data object ToFeatured: NavigationEvent(MainScreen.Featured.route)
    data class ToFilmInfo(val id: Long? = null): NavigationEvent(MainScreen.FilmInfo.route + (id?.let { "?id=$it" } ?: ""))
}