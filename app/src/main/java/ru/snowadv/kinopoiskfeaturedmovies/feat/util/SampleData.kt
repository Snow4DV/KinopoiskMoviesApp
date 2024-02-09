package ru.snowadv.kinopoiskfeaturedmovies.feat.util

import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film

object SampleData {
    val hungryGamesFilm = Film(
        countries = listOf("США", "Канада"),
        filmId = 1289334,
        filmLength = "02:37",
        genres = listOf("триллер", "драма", "фантастика", "приключения", "боевик", "военный"),
        nameEn = "The Hunger Games: The Ballad of Songbirds & Snakes",
        nameRu = "Голодные игры: Баллада о змеях и певчих птицах",
        posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/1289334.jpg",
        posterUrlPreview = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/1289334.jpg",
        rating = "7.2",
        ratingVoteCount = 47449,
        year = "2023"
    )


    val gentlemenFilm = Film(
        countries = listOf("США", "Великобритания"),
        filmId = 1143242,
        filmLength = "01:53",
        genres = listOf("криминал", "боевик", "комедия"),
        nameEn = "The Gentlemen",
        nameRu = "Джентльмены",
        posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/1143242.jpg",
        posterUrlPreview = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/1143242.jpg",
        rating = "8.6",
        ratingVoteCount = 1629626,
        year = "2019"
    )


    val interstellarFilm = Film(
        countries = listOf("США", "Великобритания", "Канада"),
        filmId = 258687,
        filmLength = "02:49",
        genres = listOf("драма", "фантастика", "приключения"),
        nameEn = "Interstellar",
        nameRu = "Интерстеллар",
        posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/258687.jpg",
        posterUrlPreview = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/258687.jpg",
        rating = "8.6",
        ratingVoteCount = 936917,
        year = "2014"
    )

    val films = listOf(hungryGamesFilm, gentlemenFilm, interstellarFilm)
}