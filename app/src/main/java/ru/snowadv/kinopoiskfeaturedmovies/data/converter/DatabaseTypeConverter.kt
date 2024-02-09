package ru.snowadv.kinopoiskfeaturedmovies.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.Film

@ProvidedTypeConverter
class DatabaseTypeConverter(
    private val jsonConverter: JsonConverter
) {
    @TypeConverter
    fun stringListToJson(list: List<String>): String {
        return jsonConverter.toJson(list) ?: "[]"
    }

    @TypeConverter
    fun stringListFromJson(json: String): List<String> {
        return jsonConverter.fromJson(json, (object : TypeToken<List<String>>() {}).type)
            ?: emptyList()
    }


    @TypeConverter
    fun filmsToJson(list: List<Film>): String {
        return jsonConverter.toJson(list) ?: "[]"
    }

    @TypeConverter
    fun filmsListFromJson(json: String): List<Film> {
        return jsonConverter.fromJson(json, (object : TypeToken<List<Film>>() {}).type)
            ?: emptyList()
    }

}