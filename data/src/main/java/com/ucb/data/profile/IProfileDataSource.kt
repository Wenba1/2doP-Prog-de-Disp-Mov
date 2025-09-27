package com.ucb.data.profile

interface IProfileDataSource {
    suspend fun getProfile(): ProfileModel
}
