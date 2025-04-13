package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import data.repository.ApiConfig
import data.repository.UserRepository
import kotlinx.datetime.Clock
import ui.components.ToastStatusEnum
import ui.settings


object Utils {
    var showNotification: @Composable (
        status: ToastStatusEnum,
        message: String,
        isAvailableBottomBar: Boolean
    ) -> Unit = { _, _, _ -> }

    fun isJwtTokenValid(): Boolean {
        if (Clock.System.now().epochSeconds > decodeJWT(ApiConfig.userToken)) {
            println("JWT Date Time:  ${decodeJWT(ApiConfig.userToken)}")
            ApiConfig.userToken = ""
            settings.putString("TOKEN","")
            return false
        }
        return true
    }
}