package com.innaval.evertonereplica.domain.repository

import androidx.lifecycle.LiveData
import com.innaval.evertonereplica.data.model.Note

interface NoteRepository {

    fun getAllNote(): LiveData<List<Note>?>

    fun getNote(noteId: Int): LiveData<Note?>

    fun createNote(note: Note): LiveData<Note>
}