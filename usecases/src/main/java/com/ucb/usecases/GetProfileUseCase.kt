package com.ucb.usecases

import com.ucb.data.profile.ProfileModel
import com.ucb.data.profile.ProfileRepository

class GetProfileUseCase(private val repo: ProfileRepository) {
    suspend operator fun invoke(): ProfileModel = repo.getProfile()
}
