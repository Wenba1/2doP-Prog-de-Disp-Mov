package com.ucb.data

import com.ucb.data.movie.IMovieLikeLocalDataSource

class MovieLikeRepository(private val local: IMovieLikeLocalDataSource) {
    suspend fun setLike(title: String, liked: Boolean) = local.setLike(title, liked)
    suspend fun getLikedTitles(): List<String> = local.getLikedTitles()
    suspend fun isLiked(title: String): Boolean = local.isLiked(title)
}
