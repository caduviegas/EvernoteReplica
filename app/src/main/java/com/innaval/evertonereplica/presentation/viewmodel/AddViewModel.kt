package com.innaval.evertonereplica.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.innaval.evertonereplica.data.model.Note
import com.innaval.evertonereplica.domain.repository.NoteRepository
import com.innaval.evertonereplica.domain.repository.NoteRepositoryImplementation

class AddViewModel(private val repository: NoteRepository = NoteRepositoryImplementation()) :
    ViewModel() {

        private val saveLiveData = MutableLiveData<Boolean>()

    val saved: LiveData<Boolean>
    get() = saveLiveData

    val title = ObservableField("")
    val body = ObservableField("")

    fun createNote(){
        if(title.get().isNullOrEmpty() || body.get().isNullOrEmpty()){
            saveLiveData.value = false
            return
        }

        val note = Note(title = title.get(), body = body.get())
        repository.createNote(note)
        saveLiveData.value = true
    }

    fun getNote(id:Int) = repository.getNote(id)
}