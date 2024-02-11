package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import ru.snowadv.kinopoiskfeaturedmovies.R
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.SampleData
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common.getBlankString
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common.shimmerEffect

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmItem(
    modifier: Modifier = Modifier,
    film: Film?,
    favorite: Boolean,
    onClick: (Film) -> Unit = {},
    onLongClick: (Film) -> Unit = {}
) {
    val imageClipShape = remember { RoundedCornerShape(5.dp) }
    val cardClipShape = remember { RoundedCornerShape(15.dp) }

    val genre = if (film != null) film.genres.firstOrNull()?.replaceFirstChar { it.uppercaseChar() }
        ?: stringResource(R.string.no_genre) else " "
    Card(
        shape = cardClipShape,
        modifier = modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .combinedClickable(
                    onClick = {
                        if (film != null) {
                            onClick(film)
                        }
                    },
                    onLongClick = {
                        if (film != null) {
                            onLongClick(film)
                        }
                    }
                )
        ) {
            Box(
                modifier = Modifier.padding(5.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    if (film != null) {
                        SubcomposeAsyncImage(
                            model = film.posterUrlPreview,
                            contentScale = ContentScale.Crop,
                            contentDescription = film.nameRu ?: film.nameEn
                            ?: stringResource(R.string.no_name),
                            modifier = Modifier
                                .width(73.dp)
                                .fillMaxHeight()
                                .padding(11.dp)
                                .clip(shape = imageClipShape),
                        ) {
                            if (painter.state is AsyncImagePainter.State.Success) {
                                Image(
                                    contentScale = contentScale,
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painter,
                                    contentDescription = contentDescription
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .shimmerEffect(round = false)
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .width(73.dp)
                                .fillMaxHeight()
                                .padding(11.dp)
                                .clip(shape = imageClipShape)
                                .shimmerEffect(round = false)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1.0f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .let {
                                        if (film == null) it
                                            .shimmerEffect()
                                            .fillMaxWidth(0.6f) else it.weight(1.0f)
                                    },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                text = if (film != null) film.nameRu ?: film.nameEn
                                ?: stringResource(R.string.no_name) else " ",
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            if (favorite) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon),
                                    contentDescription = stringResource(R.string.feature_film),
                                    tint = colorResource(id = R.color.primary),
                                    modifier = Modifier
                                        .height(with(LocalDensity.current) { 18.sp.toDp() })
                                        .padding(end = 10.dp, start = 5.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            modifier = Modifier
                                .let {
                                    if (film == null) it
                                        .shimmerEffect()
                                        .fillMaxWidth(0.3f) else it
                                },
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.SemiBold,
                            text = if (film != null) "$genre (${film.year ?: stringResource(R.string.no_year)})" else " ",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun FilmItemPreview() {
    Surface(
        modifier = Modifier.width(420.dp),
    ) {
        FilmItem(
            film = SampleData.hungryGamesFilm,
            modifier = Modifier.fillMaxWidth(),
            favorite = true
        )
    }

}