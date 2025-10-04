package com.github.eutkin.bot2gis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class Bot2gisApplication : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*") // ← ваш фронтенд
            .allowedMethods("GET", "POST", "OPTIONS")
            .allowedHeaders("Content-Type", "X-Telegram-Init-Data")
            .allowCredentials(false) // не нужно, если не используете куки
    }
}

fun main(args: Array<String>) {
    runApplication<Bot2gisApplication>(*args)
}

