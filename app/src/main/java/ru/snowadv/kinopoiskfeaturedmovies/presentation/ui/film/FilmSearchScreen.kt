package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.SampleData
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common.SearchTopBar

@Composable
fun SearchFilmScreen(
    modifier: Modifier = Modifier,

    ) {

}

@Composable
fun SearchFilmScreenContent(
    modifier: Modifier = Modifier,
    films: List<Film>,
    favoriteIds: Set<Long>,
    onClick: (Film) -> Unit,
    onLongClick: (Film) -> Unit,
    title: String
) {
    val searchMode = remember { mutableStateOf(false) }
    val textFieldState = remember { mutableStateOf("") }
    Column(
        modifier = modifier
    ) {
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
        FilmsList(
            modifier = Modifier.fillMaxSize(),
            films = films,
            favoriteIds = favoriteIds,
            onClick = onClick,
            onLongClick = onLongClick
        )
    }
}

@Preview
@Composable
fun SearchFilmScreenPreview() {
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
            title = "Популярные"
        )
    }
}