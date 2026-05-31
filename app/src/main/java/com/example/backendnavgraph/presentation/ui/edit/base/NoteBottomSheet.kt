package com.example.backendnavgraph.presentation.ui.edit.base

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.backendnavgraph.domain.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorBottomSheet(
    note: Note?,
    onDismiss: () -> Unit,
    onSave: (title: String, content: String) -> Unit
) {

    val isEditMode = note != null

    var title by remember(note?.id) {
        mutableStateOf(note?.title ?: "")
    }

    var content by remember(note?.id) {
        mutableStateOf(note?.content ?: "")
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = if (isEditMode)
                    "Редактирование"
                else
                    "Новая заметка",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Заголовок") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Текст") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    onSave(title, content)
                    onDismiss()
                },
                enabled = title.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditMode) "Сохранить" else "Создать")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
