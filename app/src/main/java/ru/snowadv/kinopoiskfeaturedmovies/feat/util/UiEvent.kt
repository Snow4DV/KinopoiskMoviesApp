package ru.snowadv.kinopoiskfeaturedmovies.feat.util

sealed class UiEvent {
    class ShowSnackbar(val message: String): UiEvent()

}
