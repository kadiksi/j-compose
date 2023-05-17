@file:OptIn(ExperimentalMaterial3Api::class)

package kz.jcourier.ui.component

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.TopAppBar

@Composable
fun ToolbarWidget(name: String,content: @Composable () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = name,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(context, "Work in progress", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Filled.Menu, "MenuIcon")
                    }
                },
            )
        }, content = { content() },
        bottomBar = {

        })
}

