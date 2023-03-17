package com.innaval.evertonereplica.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.innaval.evertonereplica.data.model.Note
import com.innaval.evertonereplica.domain.repository.NoteRepository
import com.innaval.evertonereplica.domain.repository.NoteRepositoryImplementation

class HomeViewModel(private val repository: NoteRepository = NoteRepositoryImplementation()): ViewModel() {

    fun getAllNotes(): LiveData<List<Note>?>{
        return repository.getAllNote()
    }
}