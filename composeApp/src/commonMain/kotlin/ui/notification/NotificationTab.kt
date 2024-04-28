package ui.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.account.auth.verification.VerificationView
import ui.components.CustomBackgroundView

object NotificationTab : Tab {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackgroundView()
            Column (verticalArrangement = Arrangement.Bottom){

//                BasicAlertDialog(onDismissRequest = {}) {
//                    Column(modifier = Modifier.background(Color.White)) {
//                    Text("Error Fehler Aufgetreten")
//                        Button(onClick = {}) {
//                            Text("Ok")
//                        }
//                    }
//                }

                Spacer(modifier = Modifier.height(200.dp))
                VerificationView() {}
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 2u,
            title = "Alert",
            icon = rememberVectorPainter(Icons.Default.Notifications)
        )

}