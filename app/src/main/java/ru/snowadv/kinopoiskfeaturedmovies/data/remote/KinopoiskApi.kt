package ru.snowadv.kinopoiskfeaturedmovies.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmsDto

interface KinopoiskApi {
    @GET("/api/v2.2/films/top/?type=TOP_100_POPULAR_FILMS")
    suspend fun getFilms(@Query("page") page: Int = 1): FilmsDto

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech"
    }
}