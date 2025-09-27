package com.ucb.usecases

import com.ucb.data.MovieLikeRepository

class SetMovieLike(private val repo: MovieLikeRepository) {
    suspend operator fun invoke(title: String, liked: Boolean) =
        repo.setLike(title, liked)
}
