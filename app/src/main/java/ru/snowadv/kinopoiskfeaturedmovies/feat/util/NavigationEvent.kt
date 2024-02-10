package ru.snowadv.kinopoiskfeaturedmovies.feat.util

sealed class NavigationEvent(val route: String) {
    data class ToFilmInfo(val id: Long? = null): NavigationEvent(MainScreen.FilmInfo.noArgRoute + (id?.let { "?id=$it" } ?: ""))
    data object ToHome: NavigationEvent("home")
}