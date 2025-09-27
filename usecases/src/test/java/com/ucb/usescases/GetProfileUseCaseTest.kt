package com.ucb.usecases

import com.ucb.data.profile.*
import com.ucb.data.profile.vo.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProfileUseCaseTest {

    private class FakeProfileDS : IProfileDataSource {
        override suspend fun getProfile(): ProfileModel = ProfileModel(
            id = "uid-xyz",
            username = Username.create("wendy"),
            fullName = FullName.create("Wendy Flores"),
            phone = PhoneNumber.create("+59171234567"),
        )
    }

    @Test
    fun get_profile_returns_model() = runBlocking {
        val repo = ProfileRepository(FakeProfileDS())
        val useCase = GetProfileUseCase(repo)

        val result = useCase()

        assertEquals("uid-xyz", result.id)
        assertEquals("wendy", result.username.value)
        assertEquals("Wendy Flores", result.fullName.value)
    }
}
