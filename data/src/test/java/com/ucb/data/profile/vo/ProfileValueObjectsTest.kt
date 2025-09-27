package com.ucb.data.profile.vo

import org.junit.Test

class ProfileValueObjectsTest {

    // FullName
    @Test fun fullName_ok() { FullName.create("Ariel Fernández") }
    @Test(expected = IllegalArgumentException::class)
    fun fullName_bad() { FullName.create("A1") }

    // Phone
    @Test fun phone_ok() { PhoneNumber.create("+59171234567") }
    @Test(expected = IllegalArgumentException::class)
    fun phone_bad() { PhoneNumber.create("abc-123") }

    // Username
    @Test fun username_ok() { Username.create("ariel.u_cb") }
    @Test(expected = IllegalArgumentException::class)
    fun username_bad() { Username.create("**") }
}
