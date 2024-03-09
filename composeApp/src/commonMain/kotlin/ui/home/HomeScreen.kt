package ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.DealModel2
import domain.model.DealModel3
import domain.model.exampleDeals
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import ui.components.CustomBackgroundView
import ui.components.CustomTopAppBar
import ui.components.ProductRow

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold(topBar = {
            CustomTopAppBar(title = "Home")
        }, bottomBar = {
            Spacer(modifier = Modifier.height(70.dp))
        }) {
            val viewModel = HomeViewModel()
            Box(modifier = Modifier.fillMaxSize()) {
                CustomBackgroundView()
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    verticalItemSpacing = 5.dp,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(it)
                        .padding(all = 5.dp)
                ) {
                    items(exampleDeals) { deal ->
                        ProductRow(deal)
                    }
                }
            }
        }
    }
}

class HomeViewModel : ViewModel() {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }
    var listDeals = MutableStateFlow<List<DealModel3>>(listOf())

    init {
        viewModelScope.launch {
//            listDeals.value = getProduct().deals
            println("list:" + getProduct().deals.map { it.title })
        }
    }

    suspend fun getProduct(): DealModel2 {
        val response = httpClient.get("http://192.168.178.22:8000/api/deals")
        return response.body()
    }
}