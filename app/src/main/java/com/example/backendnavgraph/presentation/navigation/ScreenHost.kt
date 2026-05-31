package com.example.backendnavgraph.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.backendnavgraph.data.network.NavigationGraphDto
import com.example.backendnavgraph.data.network.screen
import com.example.backendnavgraph.data.repository.NotesRepository
import com.example.backendnavgraph.presentation.ui.notes.NotesListScreen
import com.example.backendnavgraph.presentation.ui.edit.EditorRenderer

@Composable
fun ScreenHost(
    graph: NavigationGraphDto
) {
    val navigator = remember {
        mutableStateOf(
            GraphNavigator(graph)
        )
    }.value

    val repo = remember { mutableStateOf(NotesRepository) }.value

    val screen = remember(navigator.currentRoute) {
        mutableStateOf(
            graph.screen(
                navigator.currentRoute
            )
        )
    }

    Log.d("SOME1WASBORN!", "screen ${screen.value}")
    Log.d("SOME1WASBORN!", "currentRoute ${navigator.currentRoute}")

    screen.value?.let { screenValue ->
        Log.d("SOME1WASBORN!", "screenValue ${screenValue}")
        when (screenValue.route) {

            "notes" -> {

                Log.d("SOME1WASBORN!", "notes")
                NotesListScreen(
                    onAddClick = {
                        Log.d("SOME1WASBORN!", "onAddClick")
                        navigator.sendEvent(
                            graph = graph,
                            event = "add_note"
                        )
                    },
                    notes = repo.notes,
                    onNoteClick = { noteId ->
                        navigator.sendEvent(
                            graph = graph,
                            event = "edit_note",
                            payload = mapOf("noteId" to noteId)
                        )
                    }
                )
            }

            "editor" -> {
                Log.d("SOME1WASBORN!", "editor")

                val noteId = navigator.currentPayload["noteId"] as? Long

                val note = repo.getById(noteId)

                EditorRenderer(
                    screen = screenValue,
                    note = note,
                    onSave = { title, content ->

                        if (note == null) {
                            repo.add(title, content)
                        } else {
                            repo.update(note.id, title, content)
                        }


                        navigator.sendEvent(graph, "save")
                    },
                    onCancel = {
                        navigator.sendEvent(graph, "cancel")
                    }
                )
            }
        }
    }
}