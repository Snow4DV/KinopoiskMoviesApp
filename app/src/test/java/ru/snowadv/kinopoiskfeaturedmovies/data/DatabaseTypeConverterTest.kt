package ru.snowadv.kinopoiskfeaturedmovies.data

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import org.junit.Before
import org.junit.Test
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.DatabaseTypeConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverterImpl
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film

class DatabaseTypeConverterTest {
    class DatabaseTypeConverterTest {

        private lateinit var jsonConverter: JsonConverter
        private lateinit var databaseTypeConverter: DatabaseTypeConverter

        @Before
        fun setup() {
            jsonConverter = JsonConverterImpl(Gson())
            databaseTypeConverter = DatabaseTypeConverter(jsonConverter)
        }

        @Test
        fun filmsToJson_validList_returnSerializedJson() {
            val films = listOf(
                Film(
                    countries = listOf("USA"),
                    filmId = 1,
                    filmLength = "136",
                    genres = listOf("Action", "Sci-Fi"),
                    nameEn = "The Matrix",
                    nameRu = "Матрица",
                    posterUrl = "https://example.com/poster.jpg",
                    posterUrlPreview = "https://example.com/poster_preview.jpg",
                    rating = "8.7",
                    ratingVoteCount = 1500,
                    year = "1999"
                ),
                Film(
                    countries = listOf("USA"),
                    filmId = 2,
                    filmLength = "169",
                    genres = listOf("Action", "Adventure"),
                    nameEn = "The Dark Knight",
                    nameRu = "Тёмный рыцарь",
                    posterUrl = "https://example.com/poster.jpg",
                    posterUrlPreview = "https://example.com/poster_preview.jpg",
                    rating = "9.0",
                    ratingVoteCount = 2000,
                    year = "2008"
                )
            )
            val expectedJson =
                """[{"countries":["USA"],"filmId":1,"filmLength":"136","genres":["Action","Sci-Fi"],"nameEn":"The Matrix","nameRu":"Матрица","posterUrl":"https://example.com/poster.jpg","posterUrlPreview":"https://example.com/poster_preview.jpg","rating":"8.7","ratingVoteCount":1500,"year":"1999"},{"countries":["USA"],"filmId":2,"filmLength":"169","genres":["Action","Adventure"],"nameEn":"The Dark Knight","nameRu":"Тёмный рыцарь","posterUrl":"https://example.com/poster.jpg","posterUrlPreview":"https://example.com/poster_preview.jpg","rating":"9.0","ratingVoteCount":2000,"year":"2008"}]"""

            val result = databaseTypeConverter.filmsToJson(films)

            assert(result == expectedJson)
        }

        @Test
        fun filmsToJson_emptyList_returnEmptyJsonArray() {
            val films = emptyList<Film>()
            val expectedJson = "[]"

            val result = databaseTypeConverter.filmsToJson(films)

            assert(result == expectedJson)
        }

        @Test
        fun filmsListFromJson_validJson_returnDeserializedList() {
            val expectedFilms = listOf(
                Film(
                    countries = listOf("USA"),
                    filmId = 1,
                    filmLength = "136",
                    genres = listOf("Action", "Sci-Fi"),
                    nameEn = "The Matrix",
                    nameRu = "Матрица",
                    posterUrl = "https://example.com/poster.jpg",
                    posterUrlPreview = "https://example.com/poster_preview.jpg",
                    rating = "8.7",
                    ratingVoteCount = 1500,
                    year = "1999"
                ),
                Film(
                    countries = listOf("USA"),
                    filmId = 2,
                    filmLength = "169",
                    genres = listOf("Action", "Adventure"),
                    nameEn = "The Dark Knight",
                    nameRu = "Тёмный рыцарь",
                    posterUrl = "https://example.com/poster.jpg",
                    posterUrlPreview = "https://example.com/poster_preview.jpg",
                    rating = "9.0",
                    ratingVoteCount = 2000,
                    year = "2008"
                )
            )

            val expectedJson = """[{"countries":["USA"],"filmId":1,"filmLength":"136","genres":["Action","Sci-Fi"],"nameEn":"The Matrix","nameRu":"Матрица","posterUrl":"https://example.com/poster.jpg","posterUrlPreview":"https://example.com/poster_preview.jpg","rating":"8.7","ratingVoteCount":1500,"year":"1999"},{"countries":["USA"],"filmId":2,"filmLength":"169","genres":["Action","Adventure"],"nameEn":"The Dark Knight","nameRu":"Тёмный рыцарь","posterUrl":"https://example.com/poster.jpg","posterUrlPreview":"https://example.com/poster_preview.jpg","rating":"9.0","ratingVoteCount":2000,"year":"2008"}]"""

            val result = databaseTypeConverter.filmsListFromJson(expectedJson)

            assert(result == expectedFilms)
        }

        @Test
        fun filmsListFromJson_emptyJson_returnEmptyList() {
            val json = "[]"
            val expectedFilms = emptyList<Film>()

            val result = databaseTypeConverter.filmsListFromJson(json)

            assert(result == expectedFilms)
        }

        @Test(expected = JsonSyntaxException::class)
        fun filmsListFromJson_invalidJson_throwException() {
            val json = "abcdefgh"

            databaseTypeConverter.filmsListFromJson(json)
        }



        @Test
        fun stringListToJson_validList_returnSerializedJson() {
            val list = listOf("item1", "item2", "item3")
            val expectedJson = """["item1","item2","item3"]"""

            val result = databaseTypeConverter.stringListToJson(list)

            assert(result == expectedJson)
        }

        @Test
        fun stringListToJson_emptyList_returnEmptyJsonArray() {
            val list = emptyList<String>()
            val expectedJson = "[]"

            val result = databaseTypeConverter.stringListToJson(list)

            assert(result == expectedJson)
        }

        @Test
        fun stringListFromJson_validJson_returnDeserializedList() {
            val json = """["item1","item2","item3"]"""
            val expectedList = listOf("item1", "item2", "item3")

            val result = databaseTypeConverter.stringListFromJson(json)

            assert(result == expectedList)
        }

        @Test
        fun stringListFromJson_emptyJson_returnEmptyList() {
            val json = "[]"
            val expectedList = emptyList<String>()

            val result = databaseTypeConverter.stringListFromJson(json)

            assert(result == expectedList)
        }
    }
}