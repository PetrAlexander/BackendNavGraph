package com.example.backendnavgraph.presentation.ui.edit

import androidx.compose.runtime.Composable
import com.example.backendnavgraph.data.network.ScreenDto
import com.example.backendnavgraph.domain.Note
import com.example.backendnavgraph.presentation.ui.edit.base.NoteEditorBottomSheet

@Composable
fun EditorRenderer(
    screen: ScreenDto,
    note: Note?,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {

    when (screen.presentation) {

        "bottom_sheet" -> {
            NoteEditorBottomSheet(
                note = note,
                onSave = onSave,
                onDismiss = onCancel
            )
        }

        "screen" -> {
            FullScreenEditorVariant(
                screen = screen,
                note = note,
                onSave = onSave,
                onCancel = onCancel
            )
        }

        else -> {
            FullScreenEditorVariant(
                screen = screen,
                note = note,
                onSave = onSave,
                onCancel = onCancel
            )
        }
    }
}
