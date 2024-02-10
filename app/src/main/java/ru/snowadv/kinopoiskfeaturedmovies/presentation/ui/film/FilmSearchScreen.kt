package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.snowadv.kinopoiskfeaturedmovies.R
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.SampleData
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common.SearchTopBar
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.theme.KinopoiskFeaturedMoviesTheme
import java.util.Locale

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
    title: String,
    errorMessage: String?,
    onRefresh: (() -> Unit)? = null
) {
    val searchMode = remember { mutableStateOf(false) }
    val textFieldState = remember { mutableStateOf(TextFieldValue()) }

    val filteredFilms = remember(textFieldState.value.text) {
        films.filter {
            textFieldState.value.text.isBlank() || (it.nameRu ?: it.nameEn ?: "")
                .lowercase(Locale.ROOT).contains(textFieldState.value.text.lowercase(Locale.ROOT))
        }
    }
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
        if(filteredFilms.isNotEmpty()) {
            FilmsList(
                modifier = Modifier.fillMaxSize(),
                films = filteredFilms,
                favoriteIds = favoriteIds,
                onClick = onClick,
                onLongClick = onLongClick
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(errorMessage != null) {
                    Icon(
                        modifier = Modifier.size(110.dp),
                        painter = painterResource(id = R.drawable.no_connection),
                        contentDescription = stringResource(R.string.no_connection),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp, bottom = 20.dp),
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = Color.White,
                        contentColor = Color.White
                    ),
                    onClick = onRefresh ?: {},
                    enabled = onRefresh != null
                ) {
                    Text(stringResource(if(errorMessage?.isNotEmpty() == true) R.string.retry else R.string.not_found))
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
                onRefresh = {}
            )
        }
    }

}