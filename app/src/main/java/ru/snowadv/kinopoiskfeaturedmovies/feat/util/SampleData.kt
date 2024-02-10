package ru.snowadv.kinopoiskfeaturedmovies.feat.util

import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo

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

    val filmInfo = FilmInfo(
        kinopoiskId = 111,
        countries = listOf("USA"),
        coverUrl = "https://avatars.mds.yandex.net/get-ott/1648503/2a00000170a5418408119bc802b53a03007b/orig",
        description = "Жизнь Томаса Андерсона разделена на две части: днём он — самый обычный офисный работник, получающий нагоняи от начальства, а ночью превращается в хакера по имени Нео, и нет места в сети, куда он бы не смог проникнуть. Но однажды всё меняется. Томас узнаёт ужасающую правду о реальности.",
        filmLength = 136,
        genres = listOf("фантастика", "боевик"),
        logoUrl = "https://avatars.mds.yandex.net/get-ott/1648503/2a00000170a5418408119bc802b53a03007b/orig",
        nameEn = "The Matrix",
        nameOriginal = "The Matrix",
        nameRu = "Матрица",
        posterUrl = "https://kinopoiskapiunofficial.tech/images/posters/kp/301.jpg",
        posterUrlPreview = "https://kinopoiskapiunofficial.tech/images/posters/kp_small/301.jpg",
        ratingKinopoisk = 8.7,
        ratingKinopoiskVoteCount = 100000,
        reviewsCount = 500,
        shortDescription = "Хакер Нео узнает, что его мир — виртуальный. Выдающийся экшен, доказавший, что зрелищное кино может быть умным",
        slogan = "Добро пожаловать в реальный мир",
        year = 1999
    )
}