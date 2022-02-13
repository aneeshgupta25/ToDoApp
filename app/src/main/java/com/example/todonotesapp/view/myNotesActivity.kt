package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonotesapp.NotesApp
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.utils.PreferenceConstant
import com.example.todonotesapp.R
import com.example.todonotesapp.adapter.NotesAdapter
import com.example.todonotesapp.clickListeners.SetClickListener
import com.example.todonotesapp.db.Notes
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class myNotesActivity : AppCompatActivity(){
    lateinit var fullName : String
    lateinit var fabutton : FloatingActionButton
    lateinit var RecyclerViewNotes : RecyclerView
    lateinit var list : ArrayList<Notes>
    lateinit var sharedPreferences : SharedPreferences
    val TAG = "MyNotesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        list = ArrayList<Notes>()
        viewBind()
        setupSharedPreferences()
        getDataFromDataBase()
        passIntent()
        setRecyclerView()

        supportActionBar?.title = fullName

        fabutton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                openDialogBox()
            }
        })
    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        list.addAll(notesDao.getAll())
    }

    private fun passIntent() {
        val intent = intent
        if(intent.hasExtra(AppConstant.fullName)) {
            fullName = intent.getStringExtra(AppConstant.fullName).toString()
        }
        if(fullName.isEmpty()){
            fullName = sharedPreferences.getString(PreferenceConstant.SHARED_PREFERENCE_FULL_NAME, "").toString()
            Log.d("myNotesActivity", "if condition works")
        }
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_FULL_NAME, MODE_PRIVATE)
    }

    private fun viewBind() {
        fabutton = findViewById(R.id.fabButton)
        RecyclerViewNotes = findViewById(R.id.recylcerViewNotes)
    }

    private fun openDialogBox(){
        val view = LayoutInflater.from(this@myNotesActivity).inflate(R.layout.add_note_dialog_layout, null)
        val edit_description : EditText = view.findViewById(R.id.edit_description)
        val edit_title : EditText = view.findViewById(R.id.edit_title)
        val submit_button : MaterialButton = view.findViewById(R.id.submit_buton)

        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        dialog.show()

        submit_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val title = edit_title.text.toString()
                val description = edit_description.text.toString()
                if(title.isNotEmpty() && description.isNotEmpty()){
                    val note = Notes(title = title, description = description)
                    list.add(note)
                    addNotesToDb(note)
                }else{
                    Toast.makeText(this@myNotesActivity, "Title or Description can't be empty", Toast.LENGTH_SHORT).show()
                }
                Log.d("myNotesActivity", "RecyclerView Calling")

                dialog.hide()
            }

        })
    }

    private fun addNotesToDb(note: Notes) {
        //insert notes in dp
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(note)
    }

    private fun setRecyclerView() {
        val setClickListener = object : SetClickListener{
            override fun onClick(note: Notes) {

                val intent = Intent(this@myNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.title, note.title)
                intent.putExtra(AppConstant.description, note.description)
                startActivity(intent)
            }

            override fun onUpdate(note: Notes) {

            }
        }
        val notesAdapter = NotesAdapter(list, setClickListener)
        val linearLayoutManager = LinearLayoutManager(this@myNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        RecyclerViewNotes.layoutManager  = linearLayoutManager
        RecyclerViewNotes.adapter = notesAdapter
    }
}