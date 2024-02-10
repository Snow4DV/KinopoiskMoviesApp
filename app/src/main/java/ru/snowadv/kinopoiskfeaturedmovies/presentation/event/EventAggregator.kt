package ru.snowadv.comapr.presentation

import kotlinx.coroutines.channels.Channel
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.NavigationEvent
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.UiEvent

interface EventAggregator {
    val eventChannel: Channel<UiEvent>
    val navigationChannel: Channel<NavigationEvent>
}