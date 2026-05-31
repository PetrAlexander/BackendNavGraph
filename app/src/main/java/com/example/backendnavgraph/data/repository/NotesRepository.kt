package com.example.backendnavgraph.data.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.backendnavgraph.domain.Note

object NotesRepository {

    private val _notes = mutableStateListOf<Note>()

    val notes: SnapshotStateList<Note> = _notes

    fun add(title: String, content: String) {
        _notes += Note(
            id = System.currentTimeMillis(),
            title = title,
            content = content
        )
    }

    fun update(id: Long, title: String, content: String) {

        val index = _notes.indexOfFirst { it.id == id }

        if (index != -1) {
            _notes[index] = _notes[index].copy(
                title = title,
                content = content
            )
        }
    }

    fun delete(id: Long) {
        _notes.removeAll { it.id == id }
    }

    fun getById(id: Long?): Note? {
        return _notes.firstOrNull { it.id == id }
    }
}

