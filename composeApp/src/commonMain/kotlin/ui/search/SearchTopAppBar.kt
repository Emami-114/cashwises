package ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.company.app.theme.cw_dark_background
import ui.components.CustomDivider
import ui.components.CustomSearchView

@Composable
fun CustomSearchTopAppBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    backButtonAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(cw_dark_background)
                .padding(top = 30.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomSearchView(
                modifier = Modifier,
                value = searchText,
                onTextChange = { onSearchTextChange(it) },
                onSearchClick = { onSearchTextChange(it) },
                onFocused = {}
            )
        }
        CustomDivider()
    }
}