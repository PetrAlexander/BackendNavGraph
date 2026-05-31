package com.example.backendnavgraph.presentation.ui.edit

import androidx.compose.runtime.Composable
import com.example.backendnavgraph.data.network.ScreenDto
import com.example.backendnavgraph.domain.Note
import com.example.backendnavgraph.presentation.ui.edit.base.NoteEditorScreen
import com.example.backendnavgraph.presentation.ui.edit.base.NoteEditorScreenV2

@Composable
fun FullScreenEditorVariant(
    screen: ScreenDto,
    note: Note?,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {

    when (screen.variant) {

        "v1" -> {
            NoteEditorScreen(
                initialTitle = note?.title ?: "",
                initialContent = note?.content ?: "",
                onBackClick = onCancel,
                onSaveClick = onSave
            )
        }

        "v2" -> {
            NoteEditorScreenV2(
                initialTitle = note?.title ?: "",
                initialContent = note?.content ?: "",
                onBackClick = onCancel,
                onSaveClick = onSave
            )
        }

        else -> {
            NoteEditorScreen(
                initialTitle = note?.title ?: "",
                initialContent = note?.content ?: "",
                onBackClick = onCancel,
                onSaveClick = onSave
            )
        }
    }
}
