package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.snowadv.kinopoiskfeaturedmovies.R
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.SampleData

@Composable
fun FilmsList(
    modifier: Modifier = Modifier,
    films: List<Film?>,
    favoriteIds: Set<Long>,
    onClick: (Film) -> Unit,
    onLongClick: (Film) -> Unit,
    hasMore: Boolean,
    loadMore: () -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 5.dp)
    ) {
        itemsIndexed(films) { index, film ->
            if(index == 0) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(13.dp))
            }
            FilmItem(
                modifier = Modifier
                    .fillMaxWidth(),
                film = film,
                favorite = film != null && film.filmId in favoriteIds,
                onClick = onClick,
                onLongClick = onLongClick
            )
        }

        if(films.isNotEmpty() && hasMore) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = loadMore, modifier = Modifier.align(Alignment.Center)) {
                        Text(stringResource(R.string.load_more))
                    }
                }
            }
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
            onLongClick = {},
            hasMore = true,
            loadMore = {}
        )
    }

}