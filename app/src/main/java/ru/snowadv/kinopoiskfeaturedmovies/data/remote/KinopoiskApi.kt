package ru.snowadv.kinopoiskfeaturedmovies.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmInfoDto
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.FilmsDto

interface KinopoiskApi {
    @GET("/api/v2.2/films/top")
    suspend fun getFilms(@Query("page") page: Int = 1, @Query("type") type: String = "TOP_100_POPULAR_FILMS"): FilmsDto

    @GET("/api/v2.2/films/{id}")
    suspend fun getFilmInfo(@Path("id") id: Long): FilmInfoDto

    companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech"
    }
}