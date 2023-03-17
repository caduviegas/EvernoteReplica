package com.innaval.evertonereplica.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.innaval.evertonereplica.R
import com.innaval.evertonereplica.data.model.Note
import com.innaval.evertonereplica.presentation.view.adapters.NoteAdapter
import com.innaval.evertonereplica.presentation.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

class HomeActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setupView()
    }

    private fun setupView() {
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        home_recycler_view.addItemDecoration(divider)
        home_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        observeAllNotes()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun observeAllNotes() {
        viewModel.getAllNotes().observe(this, Observer { notes ->
            if (notes == null) {
                displayError("Erro ao carregar notas")
            } else {
                displayNotes(notes)
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            return true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_all_notes) {

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun displayError(message: String) {
        showToast(message)
    }

    fun displayNotes(notes: List<Note>) {
        home_recycler_view.adapter = NoteAdapter(notes) { note ->
            val intent = Intent(baseContext, FormActivity::class.java)
            intent.putExtra("noteId", note.id)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun fabClicked(view: View) {
        val intent = Intent(baseContext, FormActivity::class.java)
        startActivity(intent)
    }
}