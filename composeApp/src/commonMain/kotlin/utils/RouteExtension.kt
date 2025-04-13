package utils

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class NavRouteExtension<T : Any>(val name: StringResource?, val route: T, val icon: DrawableResource? = null)