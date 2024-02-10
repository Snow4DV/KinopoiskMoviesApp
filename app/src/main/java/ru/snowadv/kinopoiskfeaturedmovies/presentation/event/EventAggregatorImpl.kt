package ru.snowadv.comapr.presentation

import kotlinx.coroutines.channels.Channel
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.NavigationEvent
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.UiEvent

class EventAggregatorImpl : EventAggregator {
    override val eventChannel: Channel<UiEvent> = Channel()
    override val navigationChannel: Channel<NavigationEvent> = Channel()
}