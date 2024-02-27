package ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.CustomBackgroundView
import ui.components.CustomSearchView
import ui.components.CustomTextField
import ui.components.CustomTopAppBar

class SearchScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        var search by remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                CustomTopAppBar(modifier = Modifier.height(80.dp), title = "", rightAction = {
                    CustomSearchView(
                        text = search,
                        onTextChange = { search = it }, onSearchClick = { search = it },
                        onFocused = {
                        }
                    )
                })
            },

            ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                CustomBackgroundView()
                Column(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    CustomTextField(text = search, onchangeText = { search = it })
                    CustomTextField(text = search, onchangeText = { search = it })
                    CustomTextField(text = search, onchangeText = { search = it })
                    CustomTextField(text = search, onchangeText = { search = it })
                }
            }


        }

    }

}