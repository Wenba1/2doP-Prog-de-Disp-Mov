package com.ucb.data.movie

interface IMovieLikeLocalDataSource {
    suspend fun setLike(title: String, liked: Boolean)
    suspend fun getLikedTitles(): List<String>
    suspend fun isLiked(title: String): Boolean
}
