package com.ucb.data.profile

import com.ucb.data.profile.vo.*

data class ProfileModel(
    val id: String,
    val username: Username,
    val fullName: FullName,
    val phone: PhoneNumber?
)
