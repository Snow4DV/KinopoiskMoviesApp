package ru.snowadv.kinopoiskfeaturedmovies.feat

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.MainScreen
import ru.snowadv.kinopoiskfeaturedmovies.feat.util.NavigationEvent

class NavigationEventTest {
    @Test
    fun testToFilmInfo() {
        val id = 123L
        val event = NavigationEvent.ToFilmInfo(id)

        val expectedRoute = "film?id=$id"

        assertEquals(expectedRoute, event.route)
    }

    @Test
    fun testToHome() {
        val event = NavigationEvent.ToHome

        val expectedRoute = "home"

        assertEquals(expectedRoute, event.route)
    }
}

class MainScreenTest {
    @Test
    fun testHomeScreen() {
        val mainScreen = MainScreen.Home

        val expectedRoute = "home"
        val expectedNoArgRoute = "home"

        assertEquals(expectedRoute, mainScreen.route)
        assertEquals(expectedNoArgRoute, mainScreen.noArgRoute)
    }

    @Test
    fun testFilmInfoScreen() {
        val mainScreen = MainScreen.FilmInfo

        val expectedRoute = "film?id={id}"
        val expectedNoArgRoute = "film"

        assertEquals(expectedRoute, mainScreen.route)
        assertEquals(expectedNoArgRoute, mainScreen.noArgRoute)
    }
}