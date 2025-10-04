package com.github.eutkin.bot2gis

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URLDecoder
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class TgAuth(
    @Value("\${BOT_TOKEN}")
    private val botToken: String,
    private val objectMapper: ObjectMapper
) {


    fun extractTgUser(initData: String): TelegramUser? {
        val params = initData.split("&").associate {
            val (k, v) = it.split("=", limit = 2)
            URLDecoder.decode(k, "UTF-8") to URLDecoder.decode(v, "UTF-8")
        }.toMutableMap()

        val hash = params["hash"] ?: return null
        params.remove("hash")

        // Сортируем по ключу и собираем строку
        val checkString = params.entries
            .sortedBy { it.key }
            .joinToString("\n") { "${it.key}=${it.value}" }

        // 1. Создаём секретный ключ: SHA256(BOT_TOKEN)
        val secretKeyBytes = MessageDigest.getInstance("SHA-256").digest(botToken.toByteArray())
        val secretKey = SecretKeySpec(secretKeyBytes, "HmacSHA256")

        // 2. Считаем HMAC-SHA256 от checkString
        val hmac = Mac.getInstance("HmacSHA256").apply { init(secretKey) }
        val computedHash = hmac.doFinal(checkString.toByteArray()).toHexString()

        if (computedHash != hash) return null

        // Парсим пользователя
        val userJson = params["user"] ?: return null
        val user = this.objectMapper.readValue(userJson, TelegramUser::class.java)

        return user
    }

    // Вспомогательная функция для hex
    fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
}

data class TelegramUser(
    val id: Long,
    val firstName: String,
    val lastName: String? = null,
    val username: String? = null,
    @JsonProperty("language_code")
    val languageCode: String? = null,
    @JsonProperty("is_premium")
    val isPremium: Boolean? = null,
    @JsonProperty("photo_url")
    val photoUrl: String? = null,
    @JsonProperty("allows_write_to_pm")
    val allowsWriteToPm: Boolean? = null
)