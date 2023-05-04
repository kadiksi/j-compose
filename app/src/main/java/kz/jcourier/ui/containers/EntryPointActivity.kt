package kz.jcourier.ui.containers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kz.jcourier.data.model.PhotosModel
import kz.jcourier.ui.component.BasicCard
import kz.jcourier.app.theme.JTheme
import kz.jcourier.ui.screens.HomeScreen
import kz.jcourier.ui.screens.LoginScreen
import kz.jcourier.viewmodel.HomeViewModel
import kz.jcourier.viewmodel.LoginViewModel

@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {
    private lateinit var  homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setContent {
            JTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginPage(homeViewModel = homeViewModel)
                }
            }
        }
    }
}

@Composable
fun LoginPage(viewModel: LoginViewModel = viewModel(), homeViewModel: HomeViewModel) {
    val isAuthorised by viewModel.uiState.isAuthorised

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LoginScreen(!isAuthorised)
        HomeScreen(isAuthorised, homeViewModel = homeViewModel)
    }
}

@Composable
fun MainContent(photoList: SnapshotStateList<PhotosModel>) {
    Column {
        LazyColumn(
            Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        ) {
            items(
                items = photoList,
                itemContent = {
                    BasicCard(it)
                })
        }
    }

}