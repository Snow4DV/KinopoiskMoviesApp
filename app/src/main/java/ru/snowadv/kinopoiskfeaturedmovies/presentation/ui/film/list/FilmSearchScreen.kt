package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.snowadv.kinopoiskfeaturedmovies.R
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.SampleData
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common.ErrorMessageBox
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common.SearchTopBar
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.theme.KinopoiskFeaturedMoviesTheme
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.view_model.FilmSearchViewModel
import java.util.Locale

@Composable
fun SearchFilmScreen(
    modifier: Modifier = Modifier,
    filmSearchViewModel: FilmSearchViewModel = hiltViewModel(),
    featuredMode: Boolean
) {

    val state = filmSearchViewModel.state
    LaunchedEffect(true) {
        if (featuredMode) {
            filmSearchViewModel.collectFavoriteIfDidntCollectPreviously()
        } else {
            filmSearchViewModel.loadIfNothingIsLoaded()
        }
    }

    val ctx = LocalContext.current

    SearchFilmScreenContent(
        modifier = modifier,
        films = state.value.films,
        favoriteIds = state.value.favoriteFilms,
        onClick = {
            filmSearchViewModel.openFilm(it.filmId)
        },
        onLongClick = {
            if (it.filmId in state.value.favoriteFilms) {
                filmSearchViewModel.removeFilmFromFeatured(it.filmId)
            } else {
                filmSearchViewModel.addFeatureFilm(it, ctx)
            }
        },
        title = stringResource(id = if (featuredMode) R.string.featured else R.string.popular),
        errorMessage = state.value.errorMessage,
        loading = state.value.loading,
        hasMore = state.value.hasNextPage,
        loadMore = {
            filmSearchViewModel.loadNextPage()
        },
        onRefresh = if (!featuredMode) {
            {
                filmSearchViewModel.refresh()
            }
        } else null,
        featuredMode = featuredMode
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchFilmScreenContent(
    modifier: Modifier = Modifier,
    films: List<Film>,
    favoriteIds: Set<Long>,
    onClick: (Film) -> Unit,
    onLongClick: (Film) -> Unit,
    title: String,
    errorMessage: String?,
    loading: Boolean,
    onRefresh: (() -> Unit)?,
    hasMore: Boolean,
    loadMore: () -> Unit,
    featuredMode: Boolean
) {
    val searchMode = remember { mutableStateOf(false) }
    val textFieldState = remember { mutableStateOf(TextFieldValue()) }

    val filteredFilms = remember(textFieldState.value.text, films) {
        films.filter {
            textFieldState.value.text.isBlank() || (it.nameRu ?: it.nameEn ?: "")
                .lowercase(Locale.ROOT).contains(textFieldState.value.text.lowercase(Locale.ROOT))
        }
    }

    val pullRefreshState = onRefresh?.let {
        rememberPullRefreshState(
            refreshing = (loading && films.isNotEmpty()),
            onRefresh = it
        )
    }

    Scaffold(
        modifier = modifier
            .let { mod -> pullRefreshState?.let { mod.pullRefresh(it) } ?: mod },
        topBar = {
            SearchTopBar(
                searchMode = searchMode.value,
                title = title,
                onSwitchMode = {
                    searchMode.value = it
                },
                textFieldValue = textFieldState.value,
                onValueChange = {
                    textFieldState.value = it
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (filteredFilms.isNotEmpty()) {
                    FilmsList(
                        modifier = Modifier.fillMaxSize(),
                        films = filteredFilms,
                        favoriteIds = favoriteIds,
                        onClick = onClick,
                        onLongClick = onLongClick,
                        hasMore = hasMore,
                        loadMore = loadMore
                    )
                } else {
                    if (loading) {
                        FilmsList(
                            modifier = Modifier.fillMaxSize(),
                            films = List(20) { null },
                            favoriteIds = emptySet(),
                            onClick = {},
                            onLongClick = {},
                            hasMore = false,
                            loadMore = {}
                        )

                    } else {
                        ErrorMessageBox(
                            modifier = Modifier.fillMaxSize(),
                            errorMessage = errorMessage,
                            onRefresh = onRefresh,
                            defaultStringResId = if (filteredFilms.isEmpty() && films.isNotEmpty()) R.string.not_found else R.string.empty_over_there
                        )
                    }
                }
            }
            if (pullRefreshState != null && !featuredMode) {
                PullRefreshIndicator(
                    refreshing = loading && films.isNotEmpty(),
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = MaterialTheme.colorScheme.primary,
                    backgroundColor = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchFilmScreenPreview() {
    KinopoiskFeaturedMoviesTheme {
        Surface(
            modifier = Modifier
                .width(420.dp)
                .height(900.dp),
        ) {
            SearchFilmScreenContent(
                films = SampleData.films,
                modifier = Modifier.fillMaxSize(),
                favoriteIds = emptySet(),
                onClick = {},
                onLongClick = {},
                title = "Популярные",
                errorMessage = stringResource(R.string.loading_error),
                onRefresh = {},
                loading = false,
                hasMore = true,
                loadMore = {},
                featuredMode = false
            )
        }
    }

}

@Preview(apiLevel = 33)
@Composable
fun SearchFilmScreenPreviewEmptyLoading() {
    KinopoiskFeaturedMoviesTheme {
        Surface(
            modifier = Modifier
                .width(420.dp)
                .height(900.dp),
        ) {
            SearchFilmScreenContent(
                films = emptyList(),
                modifier = Modifier.fillMaxSize(),
                favoriteIds = emptySet(),
                onClick = {},
                onLongClick = {},
                title = "Популярные",
                errorMessage = stringResource(R.string.loading_error),
                onRefresh = {},
                loading = true,
                hasMore = true,
                loadMore = {},
                featuredMode = false
            )
        }
    }

}