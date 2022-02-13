package com.example.todonotesapp.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.R

class DetailActivity : AppCompatActivity(){
    val TAG = "DetailActivity"
    lateinit var textViewTitle : TextView
    lateinit var textviewDescription : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        bindView();
        setupIntent();
    }

    private fun setupIntent() {
        val intent = intent
        val title = intent.getStringExtra(AppConstant.title)
        val description = intent.getStringExtra(AppConstant.description)
        textViewTitle.text = title
        textviewDescription.text = description
    }

    private fun bindView() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textviewDescription = findViewById(R.id.textviewDescription)
    }
}