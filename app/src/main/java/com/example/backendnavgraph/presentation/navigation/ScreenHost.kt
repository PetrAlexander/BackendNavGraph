package com.example.backendnavgraph.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.backendnavgraph.data.network.NavigationGraphDto
import com.example.backendnavgraph.data.network.screen
import com.example.backendnavgraph.data.repository.NotesRepository
import com.example.backendnavgraph.presentation.ui.notes.NotesListScreen
import com.example.backendnavgraph.presentation.ui.edit.EditorRenderer

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ScreenHost(
    graph: NavigationGraphDto,
    onFinish: () -> Unit
) {
    val navigator = remember {
        mutableStateOf(
            GraphNavigator(graph)
        )
    }.value

    BackHandler {

        val handled = navigator.onBack()

        if (!handled) {
            onFinish()
        }
    }

    val repo = remember { mutableStateOf(NotesRepository) }.value

    val screen = remember(navigator.getCurrentRoute()) {
        mutableStateOf(
            graph.screen(
                navigator.getCurrentRoute()
            )
        )
    }

    screen.value?.let { screenValue ->
        when (screenValue.route) {

            "notes" -> {

                NotesListScreen(
                    onAddClick = {
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