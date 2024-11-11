package utils

import androidx.compose.runtime.Composable
import ui.components.ToastStatusEnum


object Utils {
    var showNotification: @Composable (
        status: ToastStatusEnum,
        message: String,
        isAvailableBottomBar: Boolean
    ) -> Unit = { _, _, _ -> }

}