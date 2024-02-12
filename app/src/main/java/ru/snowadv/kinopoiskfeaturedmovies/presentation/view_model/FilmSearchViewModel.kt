package ru.snowadv.kinopoiskfeaturedmovies.presentation.view_model

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.imageLoader
import coil.request.ImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.snowadv.kinopoiskfeaturedmovies.presentation.event.EventAggregator
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.domain.repository.FilmRepository
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.NavigationEvent
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.Resource
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.UiEvent
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.list.FilmSearchScreenState
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@HiltViewModel
class FilmSearchViewModel @Inject constructor(
    private val filmRepository: FilmRepository,
    private val eventAggregator: EventAggregator
) : ViewModel() {

    private val _state = mutableStateOf(FilmSearchScreenState(loading = false))
    val state: State<FilmSearchScreenState> = _state
    private val stateMutex = Mutex()

    private val currentPage = mutableIntStateOf(0)
    private val availablePages = mutableIntStateOf(1)

    private var dataLoaded: Boolean = false

    init {
        viewModelScope.launch {
            filmRepository.getFavoriteFilmsIds().onEach {
                stateMutex.withLock {
                    _state.value = state.value.copy(
                        favoriteFilms = it
                    )
                }
            }.launchIn(this)
        }
    }

    fun collectFavoriteIfDidntCollectPreviously() {
        if(!dataLoaded) {
            dataLoaded = true
            collectFavorite()
        }
    }
    private fun collectFavorite() {
        viewModelScope.launch {
            filmRepository.getFavoriteFilms().onEach {
                _state.value = state.value.copy(
                    films = it
                )
            }.launchIn(this)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            stateMutex.withLock {
                _state.value = state.value.copy(
                    films = emptyList()
                )
            }
            currentPage.intValue = 0
            availablePages.intValue = 1
            loadNextPage()
        }
    }

    fun loadIfNothingIsLoaded() {
        if(!dataLoaded) {
            dataLoaded = true
            loadNextPage()
        }
    }
    fun loadNextPage() {
        if (currentPage.intValue < availablePages.intValue) {
            val beforeLoad = state.value.films
            currentPage.intValue = currentPage.intValue + 1
            viewModelScope.launch(Dispatchers.IO) {
                stateMutex.withLock {
                    filmRepository.getPopularFilms(currentPage.intValue + 1).onEach { resource ->
                        availablePages.intValue = resource.data?.pagesCount ?: availablePages.intValue
                        when (resource) {
                            is Resource.Loading, is Resource.Success -> {
                                _state.value = state.value.copy(
                                    loading = resource is Resource.Loading,
                                    errorMessage = null,
                                    hasNextPage = currentPage.intValue < availablePages.intValue,
                                    films = resource.data?.films?.let { beforeLoad + it }
                                        ?: beforeLoad
                                )
                            }

                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    loading = false,
                                    errorMessage = resource.message,
                                    hasNextPage = currentPage.intValue < availablePages.intValue,
                                    films = resource.data?.films?.let { beforeLoad + it }
                                        ?: beforeLoad,
                                )
                                if (resource.data != null) {
                                    eventAggregator.eventChannel.send(UiEvent.ShowSnackbar("Мы не смогли подключиться к серверу, так что данные были загружены из памяти."))
                                } else {
                                    eventAggregator.eventChannel.send(UiEvent.ShowSnackbar(resource.message ?: "Неожиданная ошибка."))
                                }
                            }
                        }

                    }.launchIn(this)
                }
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

    fun addFeatureFilm(film: Film, context: Context) { // context is needed to prefetch picture
        viewModelScope.launch {
            filmRepository.addFavoriteFilm(film)
            eventAggregator.eventChannel.send(UiEvent.ShowSnackbar("Добавлено в избранные"))

            val alreadyFailed = AtomicBoolean(false)
            filmRepository.getFilmInfo(film.filmId).onEach {
                if(it is Resource.Error && it.data == null) {
                    if(!alreadyFailed.getAndSet(true)) {
                        eventAggregator.eventChannel.send(UiEvent.ShowSnackbar(it.message ?: "Непредвиденная ошибка во время предзагрузки."))
                    }
                }
            }.launchIn(this)

            film.posterUrl?.let {
                val prefetchRequest = ImageRequest.Builder(context).data(it).build()
                if(context.imageLoader.enqueue(prefetchRequest).job.await().drawable == null && !alreadyFailed.getAndSet(true)) {
                    eventAggregator.eventChannel.send(UiEvent.ShowSnackbar("Ошибка во время предварительной загрузки постера. Проверьте подключение к интернету."))
                }
            }
        }
    }


    fun removeFilmFromFeatured(id: Long) {
        viewModelScope.launch {
            filmRepository.removeFavoriteFilm(id)
            eventAggregator.eventChannel.send(UiEvent.ShowSnackbar("Удалено из избранных"))
        }
    }
}