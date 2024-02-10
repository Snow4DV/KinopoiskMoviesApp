package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.info

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.SnapPositionInLayout
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.snowadv.kinopoiskfeaturedmovies.R
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.SampleData
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common.ErrorMessageBox
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.theme.KinopoiskFeaturedMoviesTheme
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.view_model.FilmInfoViewModel

@Composable
fun FilmInfoScreen(
    modifier: Modifier = Modifier,
    filmInfoViewModel: FilmInfoViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    LaunchedEffect(true) {
        filmInfoViewModel.loadData()
    }

    val state = filmInfoViewModel.state.value

    Box(
        modifier = modifier
    ) {
        state.film?.let {
            FilmInfoScreenContent(
                modifier = Modifier.fillMaxSize(),
                filmInfo = state.film,
                onBackClick = onBackClick
            )
        } ?: run {
            if(!state.loading) {
                ErrorMessageBox(
                    modifier = Modifier.fillMaxSize(),
                    errorMessage = state.error,
                    onRefresh = if(state.error != null) {
                        {
                            filmInfoViewModel.loadData()
                        }
                    } else null,
                    defaultStringResId = R.string.choose_film
                )
            }
        }
        if(state.loading) {
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
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmInfoScreenContent(
    modifier: Modifier = Modifier,
    filmInfo: FilmInfo,
    onBackClick: () -> Unit

) {
    val configuration = LocalConfiguration.current

    val iconPaddingTop = 15.dp
    val iconPaddingStart = 10.dp

    val minBarAndImageHeight = with(LocalDensity.current) { 29.sp.toDp() + iconPaddingTop * 2 }

    val posterWidth = configuration.screenWidthDp.dp
    val posterHeight = remember(configuration.screenHeightDp, posterWidth) {
        min(
            configuration.screenHeightDp.dp - minBarAndImageHeight,
            (posterWidth.value / 27.0f * 40.0f).toInt().dp
        )
    } // movie poster is 27x40 inches

    val scrollState = rememberLazyListState()

    LaunchedEffect(Unit) {
        scrollState.scrollToItem(1)
    }

    val snapPosition = remember {
        SnapPositionInLayout { layoutSize, itemSize, beforeContentPadding, afterContentPadding, _ ->
            beforeContentPadding
        }
    }
    val snappingLayout = remember(scrollState) {
        SnapLayoutInfoProvider(
            scrollState,
            positionInLayout = snapPosition
        )
    }
    val flingBehavior = rememberSnapFlingBehavior(snappingLayout)

    val paddingBetweenDescriptionUnits = 7.dp
    val fontSizeTitle = 20.sp
    val fontSizeSecondary = 14.sp

    val pullBarHeight = 15.dp

    val scrollThingShape = remember {RoundedCornerShape(25.dp)}
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = filmInfo.posterUrl,
            contentScale = ContentScale.Crop,
            contentDescription = filmInfo.nameRu ?: stringResource(R.string.no_name),
            modifier = Modifier
                .width(posterWidth)
                .height(posterHeight),
            placeholder = ColorPainter(Color.LightGray)
        )
        Icon( // a little bit hacky and blur is expensive. I would ask designer to reconsider this
            modifier = with(LocalDensity.current) {
                Modifier
                    .height(31.sp.toDp() + iconPaddingTop)
                    .width(31.sp.toDp() + iconPaddingStart * 2)
                    .padding(top = iconPaddingTop, start = iconPaddingStart, end = iconPaddingStart)
                    .offset(x = (0).dp, y = 0.dp)
                    .blur(4.dp)
            },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.Black
        )
        Icon(
            modifier = with(LocalDensity.current) {
                Modifier
                    .height(29.sp.toDp() + iconPaddingTop)
                    .width(29.sp.toDp() + iconPaddingStart)
                    .padding(top = iconPaddingTop, start = iconPaddingStart)
                    .clickable(onClick = onBackClick)
            },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back),
            tint = MaterialTheme.colorScheme.primary
        )
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = minBarAndImageHeight),
                state = scrollState,
                flingBehavior = flingBehavior
            ) {
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(posterHeight - minBarAndImageHeight - pullBarHeight)
                    )
                }
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .width(65.dp)
                                .height(pullBarHeight)
                                .align(alignment = Alignment.CenterHorizontally)
                                .padding(5.dp)
                                .clip(scrollThingShape)
                                .shadow(elevation = 5.dp, shape = scrollThingShape)
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))


                        )
                        Column(
                            modifier = Modifier
                                .shadow(elevation = 10.dp)
                                .background(color = MaterialTheme.colorScheme.surface)
                                .padding(vertical = 20.dp, horizontal = 35.dp)
                                .fillMaxWidth(),
                            //.height(configuration.screenHeightDp.dp - minBarAndImageHeight),
                            verticalArrangement = Arrangement.spacedBy(
                                paddingBetweenDescriptionUnits
                            )
                        ) {
                            Text(
                                text = filmInfo.nameRu ?: filmInfo.nameEn ?: filmInfo.nameOriginal
                                ?: stringResource(
                                    id = R.string.no_name
                                ),
                                fontSize = fontSizeTitle,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(bottom = 3.dp)
                            )
                            Text(
                                modifier = Modifier.padding(2.dp),
                                text = filmInfo.description
                                    ?: stringResource(R.string.no_description),
                                fontSize = fontSizeSecondary,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = (fontSizeSecondary.value + 2).sp
                            )
                            if (filmInfo.genres.isNotEmpty()) {
                                Row {
                                    Text(
                                        text = stringResource(if (filmInfo.genres.size == 1) R.string.genre else R.string.genres),
                                        fontSize = fontSizeSecondary,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = (fontSizeSecondary.value + 2).sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = filmInfo.genres.joinToString(", "),
                                        fontSize = fontSizeSecondary,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = (fontSizeSecondary.value + 2).sp,
                                    )
                                }
                            }
                            if (filmInfo.countries.isNotEmpty()) {
                                Row {
                                    Text(
                                        text = stringResource(
                                            if (filmInfo.countries.size == 1) R.string.country else R.string.countries
                                        ),
                                        fontSize = fontSizeSecondary,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = (fontSizeSecondary.value + 2).sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = filmInfo.countries.joinToString(", "),
                                        fontSize = fontSizeSecondary,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = (fontSizeSecondary.value + 2).sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun FilmInfoScreenContentPreview() {
    val configuration = LocalConfiguration.current
    KinopoiskFeaturedMoviesTheme {
        Surface(
            modifier = Modifier
                .width(configuration.screenWidthDp.dp)
                .height(configuration.screenHeightDp.dp),
        ) {
            FilmInfoScreenContent(
                modifier = Modifier.fillMaxSize(),
                filmInfo = SampleData.filmInfo,
                onBackClick = {}
            )

        }
    }
}