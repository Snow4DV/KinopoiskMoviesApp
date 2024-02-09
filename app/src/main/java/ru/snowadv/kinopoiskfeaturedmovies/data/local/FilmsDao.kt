package ru.snowadv.kinopoiskfeaturedmovies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FavoriteFilmEntity
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmInfoEntity
import ru.snowadv.kinopoiskfeaturedmovies.data.local.entity.FilmsEntity
import ru.snowadv.kinopoiskfeaturedmovies.domain.model.FilmInfo

@Dao
interface FilmsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(filmsEntity: FilmsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmInfo(filmInfoEntity: FilmInfoEntity)
    @Transaction
    @Query("SELECT * FROM FilmsEntity WHERE page = :page")
    suspend fun getFilmsPage(page: Int): FilmsEntity?
    @Transaction
    @Query("SELECT * FROM FilmInfoEntity WHERE kinopoiskId = :id")
    suspend fun getFilmInfo(id: Long): FilmInfoEntity?

    @Transaction
    @Query("SELECT * FROM FavoriteFilmEntity")
    fun getFavoriteFilms(): Flow<List<FavoriteFilmEntity>>

    @Transaction
    @Query("SELECT filmId FROM FavoriteFilmEntity")
    fun getFavoriteIds(): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteFilm(favoriteFilmEntity: FavoriteFilmEntity)

    @Query("DELETE FROM FavoriteFilmEntity WHERE filmId = :filmId")
    suspend fun removeFavoriteFilm(filmId: Long)

}