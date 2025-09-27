package com.ucb.framework.movie

import android.content.Context
import com.ucb.data.movie.IMovieLikeLocalDataSource
import com.ucb.framework.persistence.AppRoomDatabase
import com.ucb.framework.persistence.MovieLike

class MovieLikeLocalDataSource(context: Context): IMovieLikeLocalDataSource {
    private val dao = AppRoomDatabase.getDatabase(context).movieLikeDao()

    override suspend fun setLike(title: String, liked: Boolean) {
        if (liked) dao.upsert(MovieLike(title = title, liked = true))
        else dao.deleteByTitle(title)
    }

    override suspend fun getLikedTitles(): List<String> = dao.getLikedTitles()

    override suspend fun isLiked(title: String): Boolean = dao.isLiked(title) ?: false
}
