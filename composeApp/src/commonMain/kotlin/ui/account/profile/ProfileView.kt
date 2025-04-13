package ui.account.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import data.repository.UserRepository
import ui.AppConstants
import ui.components.CustomTextField
import ui.components.CustomTopAppBar

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        UserRepository.INSTANCE.user?.let { user ->
            name = user.name
            email = user.email

        }
    }
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        CustomTopAppBar(title = "Profile", backButtonAction = {
            navController.popBackStack()
        })
        Column(
            modifier = Modifier.padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CustomTextField(value = name, onValueChange = { name = it })
            CustomTextField(value = email, onValueChange = { email = it })
        }
    }
}