package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
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
        filmSearchViewModel.loadNextPage()
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
            if(it.filmId in state.value.favoriteFilms) {
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
        onRefresh = {
            filmSearchViewModel.refresh()
        }
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
    loadMore: () -> Unit
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(48.dp),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }

                } else {
                    ErrorMessageBox(
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = errorMessage,
                        onRefresh = onRefresh
                    )
                }
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
                loadMore = {}
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
                loadMore = {}
            )
        }
    }

}