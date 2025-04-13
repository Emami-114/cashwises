package utils

import data.repository.UserRepository
import domain.model.UserModel
import domain.model.UserRole
import io.ktor.utils.io.charsets.Charsets
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.longOrNull
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class, FormatStringsInDatetimeFormats::class)
fun decodeJWT(jwt: String): Long {
    val parts = jwt.split(".")
    if (parts.size != 3) {
        println("Invalid JWT format")
        return 0
    }
    val payloadBase64 = parts[1]
    val decoderBytes = base64UrlDecode(payloadBase64)
    val payloadJson = decoderBytes.decodeToString()

//    val formatter = LocalDateTime.Format {
//        byUnicodePattern("dd.MM.yyyy HH:mm:ss")
//    }

    val jsonElement = Json.parseToJsonElement(payloadJson)
    val jsonObject = jsonElement.jsonObject
    println("Payload: $jsonObject")
    val key = "exp"
        val value = jsonObject[key]
        if (value is JsonPrimitive && value.isString.not()) {
            val timestamp = value.longOrNull ?: 0
            println("$key: $timestamp")
            UserRepository.INSTANCE.userIsLogged = true
            UserRepository.INSTANCE.user = UserModel(
                name = jsonObject["unique_name"].toString(),
                role = UserRole.ADMIN,
                email = "",
                verified = true,
                photo = ""
            )
            UserRepository.INSTANCE.user?.role = UserRole.ADMIN
            UserRepository.INSTANCE.user?.name = jsonObject["unique_name"].toString()
            println("Role: ${jsonObject["role"].toString()}")
            println("Name: ${jsonObject["unique_name"].toString()}")
            return timestamp
        } else {
            println("$key: nicht vorhanden oder kein Zahlentyp")
            return 0
        }
}

@OptIn(ExperimentalEncodingApi::class)
private fun base64UrlDecode(input: String): ByteArray {
    val normalized = input
        .replace('-', '+')
        .replace('_', '/')
        .let {
            val padding = (4 - it.length % 4) % 4
            it + "=".repeat(padding)
        }
    return Base64.decode(normalized)
}