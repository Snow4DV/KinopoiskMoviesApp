package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.snowadv.kinopoiskfeaturedmovies.presentation.event.EventAggregator
import ru.snowadv.kinopoiskfeaturedmovies.domain.repository.FilmRepository
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.Resource
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.UiEvent
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.info.FilmInfoScreenState
import javax.inject.Inject

@HiltViewModel
class FilmInfoViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
    private val eventAggregator: EventAggregator
): ViewModel() {

    private val _state = mutableStateOf(FilmInfoScreenState())
    val state: State<FilmInfoScreenState> = _state

    fun loadData(filmId: Long?) {
        if(filmId == null) return
        _state.value = FilmInfoScreenState()
        viewModelScope.launch(Dispatchers.IO) {
            filmRepository.getFilmInfo(filmId).onEach {  resource ->
                when(resource) {
                    is Resource.Loading, is Resource.Success -> {
                        _state.value = state.value.copy(
                            loading = resource is Resource.Loading,
                            error = null,
                            film = resource.data ?: state.value.film
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            loading = false,
                            error = if(resource.data == null) resource.message else null,
                            film = resource.data ?: state.value.film
                        )
                        if(resource.data != null) {
                            eventAggregator.eventChannel.send(UiEvent.ShowSnackbar("Сервер недоступен. Данные были загружены из кэша."))
                        }
                    }
                }
            }.launchIn(this)
        }
    }
}