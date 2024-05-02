package ui.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.account.auth.verification.VerificationView
import ui.components.CustomBackgroundView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(onNavigate: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBackgroundView()
        Column(verticalArrangement = Arrangement.Bottom) {
            Spacer(modifier = Modifier.height(200.dp))
        }
    }
}
