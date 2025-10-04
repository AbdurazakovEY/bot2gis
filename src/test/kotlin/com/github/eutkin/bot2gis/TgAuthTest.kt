package com.github.eutkin.bot2gis

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class TgAuthTest {

    private val tgAuth = TgAuth(
       "123456789:ABCdefGhIJKlmNoPQRsTUVwxyZ1234567890", ObjectMapper()
    )

    @Test
    @Disabled("Not implemented yet")
    fun testTgAuth() {
       val result =  tgAuth
            .extractTgUser("user=%7B%22id%22%3A123456789%2C%22first_name%22%3A%22Test%22%7D&auth_date=1712345678&hash=fe8a7b9e3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f")

    }
}