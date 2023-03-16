package com.innaval.evertonereplica.data.model

import javax.inject.Inject

class User @Inject constructor(private val note:Note){

    fun showNoteTitle(){
        println("Nota $note")
    }
}
