package ru.snowadv.kinopoiskfeaturedmovies.presentation.event

import kotlinx.coroutines.channels.Channel
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.NavigationEvent
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.UiEvent
import ru.snowadv.kinopoiskfeaturedmovies.presentation.event.EventAggregator

class EventAggregatorImpl : EventAggregator {
    override val eventChannel: Channel<UiEvent> = Channel()
    override val navigationChannel: Channel<NavigationEvent> = Channel()
}