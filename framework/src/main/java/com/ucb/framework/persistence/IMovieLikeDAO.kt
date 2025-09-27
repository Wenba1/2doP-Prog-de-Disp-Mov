package com.ucb.framework.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IMovieLikeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(like: MovieLike)

    @Query("DELETE FROM movie_like WHERE title = :title")
    suspend fun deleteByTitle(title: String)

    @Query("SELECT title FROM movie_like WHERE liked = 1")
    suspend fun getLikedTitles(): List<String>

    @Query("SELECT liked FROM movie_like WHERE title = :title LIMIT 1")
    suspend fun isLiked(title: String): Boolean?
}
