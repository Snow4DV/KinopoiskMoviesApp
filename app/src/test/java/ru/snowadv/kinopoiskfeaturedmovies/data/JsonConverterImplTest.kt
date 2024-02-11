package ru.snowadv.kinopoiskfeaturedmovies.data

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.JsonConverterImpl
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmsEntity
import javax.inject.Inject



class JsonConverterImplTest {


    @Inject
    lateinit var jsonConverter: JsonConverter

    @Before
    fun setup() {
        jsonConverter = JsonConverterImpl(Gson())
    }

    @Test
    fun fromJson_validJson_returnDeserializedObject() {
        val json = """
             {
                 "name": "John",
                 "age": 25
             }
         """.trimIndent()

        val expectedObject = User("John", 25)
        val type = object : TypeToken<User>() {}.type

        val result = jsonConverter.fromJson<User>(json, type)

        assert(result == expectedObject)
    }

    @Test
    fun fromJson_invalidJson_throwException() {
        val json = """
             {
                 "name": "John",
                 "age": "adbs" 
             }
         """.trimIndent()

        val type = object : TypeToken<User>() {}.type

        assertThrows(JsonSyntaxException::class.java) {
            val result = jsonConverter.fromJson<User>(json, type)
        }
    }

    @Test
    fun toJson_validObject_returnSerializedJson() {
        val user = User("John", 25)
        val expectedJson = """{"name":"John","age":25}"""

        val result = jsonConverter.toJson(user)

        assert(result == expectedJson)
    }

    @Test
    fun toJson_nullObject_returnNull() {
        val user: User? = null

        val result = jsonConverter.toJson(user)

        assert(result == "null")
    }

    data class User(val name: String, val age: Int)

}