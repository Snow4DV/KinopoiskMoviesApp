package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.SampleData

@Composable
fun FilmsList(
    modifier: Modifier = Modifier,
    films: List<Film>,
    favoriteIds: Set<Long>,
    onClick: (Film) -> Unit,
    onLongClick: (Film) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 5.dp)
    ) {
        itemsIndexed(films) { index, film ->
            if(index == 0) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp))
            }
            FilmItem(
                modifier = Modifier
                    .fillMaxWidth(),
                film = film,
                favorite = film.filmId in favoriteIds,
                onClick = onClick,
                onLongClick = onLongClick
            )
        }
    }
}


@Preview
@Composable
fun FilmsListPreview() {
    Surface(
        modifier = Modifier
            .width(420.dp)
            .height(900.dp),
    ) {
        FilmsList(
            films = SampleData.films,
            modifier = Modifier.fillMaxSize(),
            favoriteIds = emptySet(),
            onClick = {},
            onLongClick = {}
        )
    }

}