package com.example.backendnavgraph.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorScreen(
    initialTitle: String = "",
    initialContent: String = "",
    onBackClick: () -> Unit,
    onSaveClick: (title: String, content: String) -> Unit
) {

    var title by remember {
        mutableStateOf(initialTitle)
    }

    var content by remember {
        mutableStateOf(initialContent)
    }

    val isEditMode = initialTitle.isNotBlank() ||
            initialContent.isNotBlank()

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        if (isEditMode)
                            "Редактирование"
                        else
                            "Новая заметка"
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Добавить заметку"
                        )
                    }
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = {
                            Text(
                                text = "Заголовок",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        textStyle = MaterialTheme.typography.titleLarge,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    OutlinedTextField(
                        value = content,
                        onValueChange = {
                            content = it
                        },
                        label = {
                            Text(
                                text = "Текст заметки",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        textStyle = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    onSaveClick(
                        title,
                        content
                    )
                },
                enabled = title.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = if (isEditMode)
                        "Сохранить изменения"
                    else
                        "Создать заметку",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
