package ui.notification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ui.components.CustomBackgroundView
import ui.deals.AddDealView

object NotificationTab : Tab {
    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomBackgroundView()
            AddDealView()
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