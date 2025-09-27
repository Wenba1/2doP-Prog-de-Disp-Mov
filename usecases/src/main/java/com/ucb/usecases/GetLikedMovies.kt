package com.ucb.usecases

import com.ucb.data.MovieLikeRepository

class GetLikedMovies(private val repo: MovieLikeRepository) {
    suspend operator fun invoke(): List<String> = repo.getLikedTitles()
}
