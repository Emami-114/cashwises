package utils


import cashwises.composeApp.BuildConfig
import getPlatform
import kotlinx.datetime.Clock
import org.kotlincrypto.macs.hmac.Hmac
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256
import ui.settings
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object ApiKeyService {
    fun checkApiKey() {
        val apiKey = settings.getString("api_key", "")
        if (apiKey.isEmpty()) {
            settings.putString("api_key", generateApiKey())
        }
    }
    fun generateApiKey(): String {
        val deviceId = getPlatform().name
        val secretKey = BuildConfig.API_SECRET_KEY
        val timestamp = Clock.System.now().toEpochMilliseconds()
        val rawData = "$deviceId:$timestamp"
        val signature = generateSignature(rawData, secretKey)
        return "$deviceId:$timestamp:$signature"
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun generateSignature(rawData: String, secretKey: String): String {
        val mac = HmacSHA256(secretKey.encodeToByteArray())
        val hash = mac.doFinal(rawData.encodeToByteArray())
        val base64 = Base64.encode(hash)

        return base64
            .replace("+", "-")
            .replace("/", "_")
            .replace("=", "")
    }
}