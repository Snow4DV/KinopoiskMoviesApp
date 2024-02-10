package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import ru.snowadv.comapr.presentation.EventAggregator
import ru.snowadv.kinopoiskfeaturedmovies.domain.repository.FilmRepository
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.NavigationEvent
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.Resource
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.UiEvent
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.list.FilmSearchScreenState
import javax.inject.Inject

@HiltViewModel
class FilmSearchViewModel @Inject constructor(
    private val filmRepository: FilmRepository, private val eventAggregator: EventAggregator
) : ViewModel() {

    private val _state = mutableStateOf(FilmSearchScreenState())
    val state: State<FilmSearchScreenState> = _state
    private val stateMutex = Mutex()

    private val currentPage = mutableIntStateOf(0)
    private val availablePages = mutableIntStateOf(1)

    init {
        viewModelScope.launch {
            filmRepository.getFavoriteFilmsIds().onEach {
                stateMutex.lock()
                _state.value = state.value.copy(
                    favoriteFilms = it
                )
                stateMutex.unlock()
            }.launchIn(this)
        }
    }

    fun refresh() {
        currentPage.intValue = 0
        availablePages.intValue = 1
        loadNextPage()
    }
    fun loadNextPage() {
        if (currentPage.intValue < availablePages.intValue) {
            val beforeLoad = state.value.films
            currentPage.intValue = currentPage.intValue + 1
            viewModelScope.launch(Dispatchers.IO) {
                filmRepository.getPopularFilms(currentPage.intValue + 1).onEach { resource ->
                    availablePages.intValue = resource.data?.pagesCount ?: availablePages.intValue
                    stateMutex.lock()
                    when (resource) {
                        is Resource.Loading, is Resource.Success -> {
                            _state.value = state.value.copy(
                                loading = resource is Resource.Loading,
                                errorMessage = null,
                                hasNextPage = currentPage.intValue < availablePages.intValue,
                                films = resource.data?.films?.let { beforeLoad + it } ?: beforeLoad
                            )
                        }

                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                loading = false,
                                errorMessage = resource.message,
                                hasNextPage = currentPage.intValue < availablePages.intValue,
                                films = resource.data?.films?.let { beforeLoad + it } ?: beforeLoad,
                            )
                            if (resource.data != null) {
                                eventAggregator.eventChannel.send(UiEvent.ShowSnackbar("Мы не смогли подключиться к серверу, так что данные были загружены из кэша."))
                            }
                        }
                    }
                    stateMutex.unlock()
                }.launchIn(this)
            }
        }

    }

    fun showSnackbar(text: String) {
        viewModelScope.launch {
            eventAggregator.eventChannel.send(UiEvent.ShowSnackbar(text))
        }
    }

    fun openFilm(id: Long) {
        viewModelScope.launch {
            eventAggregator.navigationChannel.send(NavigationEvent.ToFilmInfo(id))
        }
    }
}