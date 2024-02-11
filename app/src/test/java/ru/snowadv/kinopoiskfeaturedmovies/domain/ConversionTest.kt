package ru.snowadv.kinopoiskfeaturedmovies.domain

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.*
import ru.snowadv.kinopoiskfeaturedmovies.data.remote.dto.*
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.*

class ConversionTest {
    @Test
    fun testFilmDtoToModel() {
        val filmDto = FilmDto(
            countries = listOf(CountryDto(country = "USA")),
            filmId = 1,
            filmLength = "120 min",
            genres = listOf(GenreDto(genre = "Action")),
            isAfisha = 1,
            nameEn = "Movie",
            nameRu = "Фильм",
            posterUrl = "example.com/poster",
            posterUrlPreview = "example.com/poster-preview",
            rating = "8.5",
            ratingVoteCount = 100,
            year = "2021"
        )
        val filmModel = filmDto.toModel()
        assertEquals(listOf("USA"), filmModel.countries)
        assertEquals(1, filmModel.filmId)
        assertEquals("120 min", filmModel.filmLength)
        assertEquals(listOf("Action"), filmModel.genres)
        assertEquals("Movie", filmModel.nameEn)
        assertEquals("Фильм", filmModel.nameRu)
        assertEquals("example.com/poster", filmModel.posterUrl)
        assertEquals("example.com/poster-preview", filmModel.posterUrlPreview)
        assertEquals("8.5", filmModel.rating)
        assertEquals(100, filmModel.ratingVoteCount)
        assertEquals("2021", filmModel.year)
    }

    // FilmInfoDto to FilmInfo
    @Test
    fun testFilmInfoDtoToModel() {
        
        val filmInfoDto = FilmInfoDto(
            kinopoiskId = 1,
            countries = listOf(CountryDto(country = "USA")),
            coverUrl = "example.com/cover",
            description = "Movie description",
            filmLength = 120,
            genres = listOf(GenreDto(genre = "Action")),
            logoUrl = "example.com/logo",
            nameEn = "Movie",
            nameOriginal = "Original Movie",
            nameRu = "Фильм",
            posterUrl = "example.com/poster",
            posterUrlPreview = "example.com/poster-preview",
            ratingKinopoisk = 8.5,
            ratingKinopoiskVoteCount = 100,
            reviewsCount = 50,
            shortDescription = "Short description",
            slogan = "Movie slogan",
            year = 2021
        )

        
        val filmInfoModel = filmInfoDto.toModel()

        
        assertEquals(1, filmInfoModel.kinopoiskId)
        assertEquals(listOf("USA"), filmInfoModel.countries)
        assertEquals("example.com/cover", filmInfoModel.coverUrl)
        assertEquals("Movie description", filmInfoModel.description)
        assertEquals(120, filmInfoModel.filmLength)
        assertEquals(listOf("Action"), filmInfoModel.genres)
        assertEquals("example.com/logo", filmInfoModel.logoUrl)
        assertEquals("Movie", filmInfoModel.nameEn)
        assertEquals("Original Movie", filmInfoModel.nameOriginal)
        assertEquals("Фильм", filmInfoModel.nameRu)
        assertEquals("example.com/poster", filmInfoModel.posterUrl)
        assertEquals("example.com/poster-preview", filmInfoModel.posterUrlPreview)
        assertEquals(8.5, filmInfoModel.ratingKinopoisk!!, 0.01)
        assertEquals(100, filmInfoModel.ratingKinopoiskVoteCount)
        assertEquals(50, filmInfoModel.reviewsCount)
        assertEquals("Short description", filmInfoModel.shortDescription)
        assertEquals("Movie slogan", filmInfoModel.slogan)
        assertEquals(2021, filmInfoModel.year)
    }

    // FilmsDto to Films
    @Test
    fun testFilmsDtoToModel() {
        
        val filmDto1 = FilmDto(
            countries = listOf(CountryDto(country = "USA")),
            filmId = 1,
            filmLength = "120 min",
            genres = listOf(GenreDto(genre = "Action")),
            isAfisha = 1,
            nameEn = "Movie 1",
            nameRu = "Фильм 1",
            posterUrl = "example.com/poster1",
            posterUrlPreview = "example.com/poster1-preview",
            rating = "8.5",
            ratingVoteCount = 100,
            year = "2021"
        )
        val filmDto2 = FilmDto(
            countries = listOf(CountryDto(country = "UK")),
            filmId = 2,
            filmLength = "90 min",
            genres = listOf(GenreDto(genre = "Comedy")),
            isAfisha = 0,
            nameEn = "Movie 2",
            nameRu = "Фильм 2",
            posterUrl = "example.com/poster2",
            posterUrlPreview = "example.com/poster2-preview",
            rating = "7.5",
            ratingVoteCount = 80,
            year = "2022"
        )
        val filmsDto = FilmsDto(
            films = listOf(filmDto1, filmDto2),
            pagesCount = 2
        )

        
        val filmsModel = filmsDto.toModel()

        
        assertEquals(2, filmsModel.pagesCount)
        assertEquals(2, filmsModel.films.size)
        assertEquals("Movie 1", filmsModel.films[0].nameEn)
        assertEquals("Фильм 1", filmsModel.films[0].nameRu)
        assertEquals("example.com/poster1", filmsModel.films[0].posterUrl)
        assertEquals("example.com/poster1-preview", filmsModel.films[0].posterUrlPreview)
        assertEquals("8.5", filmsModel.films[0].rating)
        assertEquals(100, filmsModel.films[0].ratingVoteCount)
        assertEquals("2021", filmsModel.films[0].year)
        assertEquals("Movie 2", filmsModel.films[1].nameEn)
        assertEquals("Фильм 2", filmsModel.films[1].nameRu)
        assertEquals("example.com/poster2", filmsModel.films[1].posterUrl)
        assertEquals("example.com/poster2-preview", filmsModel.films[1].posterUrlPreview)
        assertEquals("7.5", filmsModel.films[1].rating)
        assertEquals(80, filmsModel.films[1].ratingVoteCount)
        assertEquals("2022", filmsModel.films[1].year)
    }

    // FilmsEntity to Films
    @Test
    fun testFilmsEntityToModel() {
        
        val film1 = Film(
            countries = listOf("USA"),
            filmId = 1,
            filmLength = "120 min",
            genres = listOf("Action"),
            nameEn = "Movie 1",
            nameRu = "Фильм 1",
            posterUrl = "example.com/poster1",
            posterUrlPreview = "example.com/poster1-preview",
            rating = "8.5",
            ratingVoteCount = 100,
            year = "2021"
        )
        val film2 = Film(
            countries = listOf("UK"),
            filmId = 2,
            filmLength = "90 min",
            genres = listOf("Comedy"),
            nameEn = "Movie 2",
            nameRu = "Фильм 2",
            posterUrl = "example.com/poster2",
            posterUrlPreview = "example.com/poster2-preview",
            rating = "7.5",
            ratingVoteCount = 80,
            year = "2022"
        )
        val filmsEntity = FilmsEntity(
            page = 1,
            films = listOf(film1, film2),
            pagesCount = 2
        )

        
        val filmsModel = filmsEntity.toModel()

        
        assertEquals(2, filmsModel.pagesCount)
        assertEquals(2, filmsModel.films.size)
        assertEquals("Movie 1", filmsModel.films[0].nameEn)
        assertEquals("Фильм 1", filmsModel.films[0].nameRu)
        assertEquals("example.com/poster1", filmsModel.films[0].posterUrl)
        assertEquals("example.com/poster1-preview", filmsModel.films[0].posterUrlPreview)
        assertEquals("8.5", filmsModel.films[0].rating)
        assertEquals(100, filmsModel.films[0].ratingVoteCount)
        assertEquals("2021", filmsModel.films[0].year)
        assertEquals("Movie 2", filmsModel.films[1].nameEn)
        assertEquals("Фильм 2", filmsModel.films[1].nameRu)
        assertEquals("example.com/poster2", filmsModel.films[1].posterUrl)
        assertEquals("example.com/poster2-preview", filmsModel.films[1].posterUrlPreview)
        assertEquals("7.5", filmsModel.films[1].rating)
        assertEquals(80, filmsModel.films[1].ratingVoteCount)
        assertEquals("2022", filmsModel.films[1].year)
    }

    // FilmInfoEntity to FilmInfo
    @Test
    fun testFilmInfoEntityToModel() {
        
        val filmInfoEntity = FilmInfoEntity(
            kinopoiskId = 1,
            countries = listOf("USA"),
            coverUrl = "example.com/cover",
            description = "Movie description",
            filmLength = 120,
            genres = listOf("Action"),
            logoUrl = "example.com/logo",
            nameEn = "Movie",
            nameOriginal = "Original Movie",
            nameRu = "Фильм",
            posterUrl = "example.com/poster",
            posterUrlPreview = "example.com/poster-preview",
            ratingKinopoisk = 8.5,
            ratingKinopoiskVoteCount = 100,
            reviewsCount = 50,
            shortDescription = "Short description",
            slogan = "Movie slogan",
            year = 2021
        )

        
        val filmInfoModel = filmInfoEntity.toModel()

        
        assertEquals(1, filmInfoModel.kinopoiskId)
        assertEquals(listOf("USA"), filmInfoModel.countries)
        assertEquals("example.com/cover", filmInfoModel.coverUrl)
        assertEquals("Movie description", filmInfoModel.description)
        assertEquals(120, filmInfoModel.filmLength)
        assertEquals(listOf("Action"), filmInfoModel.genres)
        assertEquals("example.com/logo", filmInfoModel.logoUrl)
        assertEquals("Movie", filmInfoModel.nameEn)
        assertEquals("Original Movie", filmInfoModel.nameOriginal)
        assertEquals("Фильм", filmInfoModel.nameRu)
        assertEquals("example.com/poster", filmInfoModel.posterUrl)
        assertEquals("example.com/poster-preview", filmInfoModel.posterUrlPreview)
        assertEquals(8.5, filmInfoModel.ratingKinopoisk!!, 0.01)
        assertEquals(100, filmInfoModel.ratingKinopoiskVoteCount)
        assertEquals(50, filmInfoModel.reviewsCount)
        assertEquals("Short description", filmInfoModel.shortDescription)
        assertEquals("Movie slogan", filmInfoModel.slogan)
        assertEquals(2021, filmInfoModel.year)
    }

    @Test
    fun testFavoriteFilmEntityToModel() {
        
        val favoriteFilmEntity = FavoriteFilmEntity(
            countries = listOf("USA"),
            filmId = 1,
            filmLength = "120 min",
            genres = listOf("Action"),
            nameEn = "Movie",
            nameRu = "Фильм",
            posterUrl = "example.com/poster",
            posterUrlPreview = "example.com/poster-preview",
            rating = "8.5",
            ratingVoteCount = 100,
            year = "2021"
        )
        val filmModel = favoriteFilmEntity.toModel()
        assertEquals(listOf("USA"), filmModel.countries)
        assertEquals(1, filmModel.filmId)
        assertEquals("120 min", filmModel.filmLength)
        assertEquals(listOf("Action"), filmModel.genres)
        assertEquals("Movie", filmModel.nameEn)
        assertEquals("Фильм", filmModel.nameRu)
        assertEquals("example.com/poster", filmModel.posterUrl)
        assertEquals("example.com/poster-preview", filmModel.posterUrlPreview)
        assertEquals("8.5", filmModel.rating)
        assertEquals(100, filmModel.ratingVoteCount)
        assertEquals("2021", filmModel.year)
    }







    @Test
    fun testFilmInfoToEntity() {


        val expected = FilmInfo(
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

        val actual = expected.toEntity()

        assert(actual.kinopoiskId == expected.kinopoiskId)
        assert(actual.countries == expected.countries)
        assert(actual.coverUrl == expected.coverUrl)
        assert(actual.description == expected.description)
        assert(actual.filmLength == expected.filmLength)
        assert(actual.genres == expected.genres)
        assert(actual.logoUrl == expected.logoUrl)
        assert(actual.nameEn == expected.nameEn)
        assert(actual.nameOriginal == expected.nameOriginal)
        assert(actual.nameRu == expected.nameRu)
        assert(actual.posterUrl == expected.posterUrl)
        assert(actual.posterUrlPreview == expected.posterUrlPreview)
        assert(actual.ratingKinopoisk == expected.ratingKinopoisk)
        assert(actual.ratingKinopoiskVoteCount == expected.ratingKinopoiskVoteCount)
        assert(actual.reviewsCount == expected.reviewsCount)
        assert(actual.shortDescription == expected.shortDescription)
        assert(actual.slogan == expected.slogan)
        assert(actual.year == expected.year)
    }

    @Test
    fun testFilmsToEntity() {
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

        val expected = Films(
            films = films,
            pagesCount = 1
        )

        val actual = expected.toEntity(1)

        assert(actual.page == 1)
        assert(actual.films == expected.films)
        assert(actual.pagesCount == expected.pagesCount)
    }

    @Test
    fun testFilmToFavEntity() {
        // Assign values to the properties of film

        val film = Film(
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

        val expected = FavoriteFilmEntity(
            countries = film.countries,
            filmId = film.filmId,
            filmLength = film.filmLength,
            genres = film.genres,
            nameEn = film.nameEn,
            nameRu = film.nameRu,
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview,
            rating = film.rating,
            ratingVoteCount = film.ratingVoteCount,
            year = film.year
        )

        val actual = film.toFavEntity()

        assert(actual.countries == expected.countries)
        assert(actual.filmId == expected.filmId)
        assert(actual.filmLength == expected.filmLength)
        assert(actual.genres == expected.genres)
        assert(actual.nameEn == expected.nameEn)
        assert(actual.nameRu == expected.nameRu)
        assert(actual.posterUrl == expected.posterUrl)
        assert(actual.posterUrlPreview == expected.posterUrlPreview)
        assert(actual.rating == expected.rating)
        assert(actual.ratingVoteCount == expected.ratingVoteCount)
        assert(actual.year == expected.year)
    }
}