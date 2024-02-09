package ru.snowadv.kinopoiskfeaturedmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.snowadv.kinopoiskfeaturedmovies.data.converter.DatabaseTypeConverter
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmInfoEntity
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmsEntity

@Database(
    entities = [FilmsEntity::class, FilmInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseTypeConverter::class)
abstract class FilmsDb: RoomDatabase() {
    abstract val dao: FilmsDao
}