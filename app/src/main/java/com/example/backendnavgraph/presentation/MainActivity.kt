package com.example.backendnavgraph.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.backendnavgraph.data.network.NavigationGraphDto
import com.example.backendnavgraph.data.retrofit.RetrofitFactory
import com.example.backendnavgraph.domain.Note
import com.example.backendnavgraph.presentation.navigation.GraphNavigator
import com.example.backendnavgraph.presentation.navigation.ScreenHost
import com.example.backendnavgraph.presentation.ui.edit.base.NoteEditorBottomSheet
import com.example.backendnavgraph.presentation.ui.edit.base.NoteEditorScreen
import com.example.backendnavgraph.presentation.ui.edit.base.NoteEditorScreenV2
import com.example.backendnavgraph.presentation.ui.notes.NotesListScreen
import com.example.backendnavgraph.presentation.ui.splash.SplashScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val notes = mutableStateListOf<Note>()

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var graph: NavigationGraphDto? = null

        lifecycleScope.launch {

            try {

                graph = RetrofitFactory.api.getGraph()

                Log.d(
                    "SHOOOO",
                    graph.toString()
                )

            } catch (e: Exception) {

                Log.d(
                    "SHOOOO",
                    e.toString()
                )
            }
        }

        setContent {
            val graph = remember {
                mutableStateOf<NavigationGraphDto?>(null)
            }

            LaunchedEffect(Unit) {

                try {

                    graph.value = RetrofitFactory.api.getGraph()

                    Log.d("SHOOOO", graph.toString())

                } catch (e: Exception) {

                    Log.d("SHOOOO", e.toString())
                }
            }

            Log.d("MainActivity!", "graph ${graph.value}")

            val graphValue = graph.value

            if (graphValue != null) {

                ScreenHost(
                    graph = graphValue,
                    onFinish = { finish() }
                )
            } else {
                SplashScreen()
                return@setContent
            }
        }
    }
}
