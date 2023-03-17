package com.innaval.evertonereplica.presentation.view.activities

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.innaval.evertonereplica.R
import com.innaval.evertonereplica.data.model.Note
import com.innaval.evertonereplica.databinding.ActivityFormBinding
import com.innaval.evertonereplica.presentation.viewmodel.AddViewModel
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.content_form.*

class FormActivity: AppCompatActivity(), TextWatcher {

    private var toSave: Boolean = false
    private var noteId: Int? = null

    private lateinit var viewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val binding =
            DataBindingUtil.setContentView<ActivityFormBinding>(this, R.layout.activity_form)
        viewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)
        binding.viewModel = viewModel
        noteId = intent.extras?.getInt("noteId")

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        setupLiveDataObserver()
    }

    private fun setupLiveDataObserver() {
        viewModel.saved.observe(this, Observer { saved ->
            if (saved) {
                finish()
            } else {
                displayError("Título e Nota devem ser informados")
            }
        })

        noteId?.let {
            viewModel.getNote(it).observe(this, Observer { note ->
                if(note == null){
                    displayError("Erro ao buscar nota")
                }else{
                    displayNote(note)
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        toggleToolbar(R.drawable.ic_arrow_back_black_24dp)

        note_title.addTextChangedListener(this)
        note_editor.addTextChangedListener(this)
    }

    private fun toggleToolbar(@DrawableRes icon: Int) {
        supportActionBar?.let {
            it.title = null
            val upArrow = ContextCompat.getDrawable(this, icon)
            val colorFilter =
                PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_ATOP
                )
            upArrow?.colorFilter = colorFilter
            it.setHomeAsUpIndicator(upArrow)
            it.setDisplayHomeAsUpEnabled(true)
        }
    }


    fun displayError(message: String) {
        showToast(message)
    }

    private fun displayNote(note: Note) {
        note_title.setText(note.title)
        note_editor.setText(note.body)
    }

    fun returnToHome() {
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun saveNoteClicked() {
        viewModel.createNote()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            return if (toSave && noteId == null) {
                saveNoteClicked()
                true
            } else {
                returnToHome()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        toSave =
            if (note_editor.text.toString().isEmpty() && note_title.text.toString().isEmpty()) {
                toggleToolbar(R.drawable.ic_arrow_back_black_24dp)
                false
            } else {
                toggleToolbar(R.drawable.ic_done_black_24dp)
                true
            }
    }

    override fun afterTextChanged(editable: Editable) {
    }

}