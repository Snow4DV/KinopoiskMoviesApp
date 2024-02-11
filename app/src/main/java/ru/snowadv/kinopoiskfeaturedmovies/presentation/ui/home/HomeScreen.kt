package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.snowadv.kinopoiskfeaturedmovies.R
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.MainScreen
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.NavigationEvent
import ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.film.list.SearchFilmScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState {2}
    Scaffold(
        modifier = modifier,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MainActivityTab.entries.forEachIndexed{ index, it ->
                    val selected = index == pagerState.currentPage
                    Button(
                        modifier = Modifier
                            .height(50.dp)
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                            contentColor = if (selected) Color.White else (if(isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary)
                        ),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Text(stringResource(it.titleResId))
                    }
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = pagerState
        ) {
            when(it) {
                0 -> {
                    SearchFilmScreen(
                        modifier = Modifier.fillMaxWidth(),
                        featuredMode = false,
                        filmSearchViewModel = hiltViewModel(key = it.toString())
                    )
                }
                1 -> {
                    SearchFilmScreen(
                        modifier = Modifier.fillMaxWidth(),
                        featuredMode = true,
                        filmSearchViewModel = hiltViewModel(key = it.toString())
                    )
                }
            }
        }
    }
}


enum class MainActivityTab(val titleResId: Int) {
    Popular(R.string.popular),
    Featured(R.string.featured);
}