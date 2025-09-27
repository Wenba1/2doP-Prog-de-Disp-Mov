package com.ucb.data.profile

class ProfileRepository(private val dataSource: IProfileDataSource) {
    suspend fun getProfile(): ProfileModel = dataSource.getProfile()
}
