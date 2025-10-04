package com.github.eutkin.bot2gis

import com.pengrad.telegrambot.model.Update
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class EndpointRegister(
    private val jdbc : JdbcOperations
) {

    companion object {
        private val log = LoggerFactory.getLogger(EndpointRegister::class.java)
    }

    @PostMapping
    fun register(@RequestBody update : Update) {
        log.info("Registering $update")
        val id = update.message().from().id()
        val firstName = update.message().from().firstName()

        try {
            this.jdbc.update("insert into users(id, first_name) values(?, ?)", id, firstName)
        } catch (e: Exception) {
            log.error(e.message)
        }
    }
}