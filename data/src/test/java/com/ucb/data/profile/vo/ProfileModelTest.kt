package com.ucb.data.profile

import com.ucb.data.profile.vo.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileModelTest {

    @Test
    fun create_profile_ok() {
        val profile = ProfileModel(
            id = "uid-123",
            username = Username.create("wen"),
            fullName = FullName.create("wen"),
            phone = PhoneNumber.create("+59170000000"),
        )
        assertEquals("uid-123", profile.id)
        assertEquals("wen", profile.username.value)
        assertEquals("wen", profile.fullName.value)
    }

}
