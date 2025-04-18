package utils


import kotlinx.datetime.Clock
import org.kotlincrypto.macs.hmac.Hmac
import org.kotlincrypto.macs.hmac.sha2.HmacSHA256
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object ApiKeyService {
    fun generateApiKey(deviceId: String = "Android", secretKey: String = "ccc45058-d90a-439d-997a-1c8d8cfeb42c"): String {
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