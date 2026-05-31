package com.example.backendnavgraph.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.backendnavgraph.data.network.NavigationGraphDto
import com.example.backendnavgraph.data.retrofit.RetrofitFactory
import com.example.backendnavgraph.domain.Note
import com.example.backendnavgraph.presentation.navigation.GraphNavigator
import com.example.backendnavgraph.presentation.navigation.ScreenHost
import com.example.backendnavgraph.presentation.ui.splash.SplashScreen

class MainActivity : ComponentActivity() {

    private val notes = mutableStateListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        var graph: NavigationGraphDto? = null
//
//        lifecycleScope.launch {
//
//            try {
//
//                graph = RetrofitFactory.api.getGraph()
//
//                Log.d(
//                    "SHOOOO",
//                    graph.toString()
//                )
//
//            } catch (e: Exception) {
//
//                Log.d(
//                    "SHOOOO",
//                    e.toString()
//                )
//            }
//        }

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
                )
            } else {
                SplashScreen()
                return@setContent
            }
//            else
//            {
//                var selectedNote by remember { mutableStateOf<Note?>(null) }
//                var showEditor by remember { mutableStateOf(false) }
//
//                val isBottomSheet = false
//                val isNewEditorScreenEnable = true
//
//                NotesListScreen(
//                    notes = notes,
//
//                    onAddClick = {
//                        selectedNote = null
//                        showEditor = true
//                    },
//
//                    onNoteClick = { noteId ->
//                        selectedNote = notes.firstOrNull { it.id == noteId }
//                        showEditor = true
//                    }
//                )
//
//                if (showEditor && isBottomSheet) {
//
//                    NoteEditorBottomSheet(
//                        note = selectedNote,
//
//                        onDismiss = {
//                            showEditor = false
//                            selectedNote = null
//                        },
//
//                        onSave = { title, content ->
//
//                            if (selectedNote == null) {
//
//                                notes += Note(
//                                    id = System.currentTimeMillis(),
//                                    title = title,
//                                    content = content
//                                )
//
//                            } else {
//
//                                val index = notes.indexOfFirst {
//                                    it.id == selectedNote!!.id
//                                }
//
//                                if (index != -1) {
//                                    notes[index] = selectedNote!!.copy(
//                                        title = title,
//                                        content = content
//                                    )
//                                }
//                            }
//
//                            showEditor = false
//                            selectedNote = null
//                        }
//                    )
//                }
//
//                if (showEditor && !isBottomSheet) {
//
//                    if (isNewEditorScreenEnable) {
//                        NoteEditorScreenV2(
//                            initialTitle = selectedNote?.title ?: "",
//                            initialContent = selectedNote?.content ?: "",
//                            onBackClick = {
//                                showEditor = false
//                                selectedNote = null
//                            },
//                            onSaveClick = { title, content ->
//
//                                if (selectedNote == null) {
//
//                                    notes += Note(
//                                        id = System.currentTimeMillis(),
//                                        title = title,
//                                        content = content
//                                    )
//
//                                } else {
//
//                                    val index = notes.indexOfFirst {
//                                        it.id == selectedNote!!.id
//                                    }
//
//                                    if (index != -1) {
//                                        notes[index] = selectedNote!!.copy(
//                                            title = title,
//                                            content = content
//                                        )
//                                    }
//                                }
//
//                                showEditor = false
//                                selectedNote = null
//                            }
//                        )
//                    } else {
//                        NoteEditorScreen(
//                            initialTitle = selectedNote?.title ?: "",
//                            initialContent = selectedNote?.content ?: "",
//
//                            onBackClick = {
//                                showEditor = false
//                                selectedNote = null
//                            },
//
//                            onSaveClick = { title, content ->
//
//                                if (selectedNote == null) {
//
//                                    notes += Note(
//                                        id = System.currentTimeMillis(),
//                                        title = title,
//                                        content = content
//                                    )
//
//                                } else {
//
//                                    val index = notes.indexOfFirst {
//                                        it.id == selectedNote!!.id
//                                    }
//
//                                    if (index != -1) {
//                                        notes[index] = selectedNote!!.copy(
//                                            title = title,
//                                            content = content
//                                        )
//                                    }
//                                }
//
//                                showEditor = false
//                                selectedNote = null
//                            }
//                        )
//                    }
//                }
//            }
        }
    }
}
