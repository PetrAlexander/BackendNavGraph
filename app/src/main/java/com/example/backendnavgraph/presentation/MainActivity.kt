package com.example.backendnavgraph.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.backendnavgraph.data.retrofit.RetrofitFactory
import com.example.backendnavgraph.domain.Note
import com.example.backendnavgraph.presentation.theme.BackendNavGraphTheme
import com.example.backendnavgraph.presentation.ui.NoteEditorBottomSheet
import com.example.backendnavgraph.presentation.ui.NoteEditorScreen
import com.example.backendnavgraph.presentation.ui.NoteEditorScreenV2
import com.example.backendnavgraph.presentation.ui.NotesListScreen
import kotlinx.coroutines.launch
import kotlin.text.set

class MainActivity : ComponentActivity() {

    private val notes = mutableStateListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {

            try {

                val graph = RetrofitFactory.api.getGraph()

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

            var selectedNote by remember { mutableStateOf<Note?>(null) }
            var showEditor by remember { mutableStateOf(false) }

            val isBottomSheet = false
            val isNewEditorScreenEnable = true

            NotesListScreen(
                notes = notes,

                onAddClick = {
                    selectedNote = null
                    showEditor = true
                },

                onNoteClick = { noteId ->
                    selectedNote = notes.firstOrNull { it.id == noteId }
                    showEditor = true
                }
            )

            if (showEditor && isBottomSheet) {

                NoteEditorBottomSheet(
                    note = selectedNote,

                    onDismiss = {
                        showEditor = false
                        selectedNote = null
                    },

                    onSave = { title, content ->

                        if (selectedNote == null) {

                            notes += Note(
                                id = System.currentTimeMillis(),
                                title = title,
                                content = content
                            )

                        } else {

                            val index = notes.indexOfFirst {
                                it.id == selectedNote!!.id
                            }

                            if (index != -1) {
                                notes[index] = selectedNote!!.copy(
                                    title = title,
                                    content = content
                                )
                            }
                        }

                        showEditor = false
                        selectedNote = null
                    }
                )
            }

            if (showEditor && !isBottomSheet) {

                if (isNewEditorScreenEnable) {
                    NoteEditorScreenV2(
                        initialTitle = selectedNote?.title ?: "",
                        initialContent = selectedNote?.content ?: "",
                        onBackClick = {
                            showEditor = false
                            selectedNote = null
                        },
                        onSaveClick = { title, content ->

                            if (selectedNote == null) {

                                notes += Note(
                                    id = System.currentTimeMillis(),
                                    title = title,
                                    content = content
                                )

                            } else {

                                val index = notes.indexOfFirst {
                                    it.id == selectedNote!!.id
                                }

                                if (index != -1) {
                                    notes[index] = selectedNote!!.copy(
                                        title = title,
                                        content = content
                                    )
                                }
                            }

                            showEditor = false
                            selectedNote = null
                        }
                    )
                } else {
                    NoteEditorScreen(
                        initialTitle = selectedNote?.title ?: "",
                        initialContent = selectedNote?.content ?: "",

                        onBackClick = {
                            showEditor = false
                            selectedNote = null
                        },

                        onSaveClick = { title, content ->

                            if (selectedNote == null) {

                                notes += Note(
                                    id = System.currentTimeMillis(),
                                    title = title,
                                    content = content
                                )

                            } else {

                                val index = notes.indexOfFirst {
                                    it.id == selectedNote!!.id
                                }

                                if (index != -1) {
                                    notes[index] = selectedNote!!.copy(
                                        title = title,
                                        content = content
                                    )
                                }
                            }

                            showEditor = false
                            selectedNote = null
                        }
                    )
                }
            }
        }
    }
}
