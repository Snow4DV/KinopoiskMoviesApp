package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.info

import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo

data class FilmInfoScreenState(
    val loading: Boolean = true,
    val error: String? = null,
    val film: FilmInfo? = null
)