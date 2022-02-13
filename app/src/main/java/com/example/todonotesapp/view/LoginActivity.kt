package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.AppConstant
import com.example.todonotesapp.utils.PreferenceConstant
import com.example.todonotesapp.R

class LoginActivity : AppCompatActivity(){
    lateinit var editTextfullname : EditText
    lateinit var editTextusername : EditText
    lateinit var buttonLogin : Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setBindView()
        setupSharedPreferences()
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
    }

    private fun setBindView() {
        editTextfullname = findViewById(R.id.editTextfullname)
        editTextusername = findViewById(R.id.editTextusername)
        buttonLogin = findViewById(R.id.buttonLogin)
        val clickAction = object : View.OnClickListener{
            override fun onClick(v: View?) {
                var fullName = editTextfullname.text.toString()
                var username = editTextusername.text.toString()

                if(fullName.isNotEmpty() && username.isNotEmpty()){
                    var intent = Intent(this@LoginActivity, myNotesActivity::class.java)
                    intent.putExtra(AppConstant.fullName, fullName);

                    startActivity(intent)

                    saveLoginStatus()
                    saveFullName(fullName)
                }else{
                    Toast.makeText(this@LoginActivity, "FullName and UserName cant be empty!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonLogin.setOnClickListener(clickAction)
    }

    private fun saveFullName(fullName : String) {
        editor = sharedPreferences.edit()
        editor.putString(PreferenceConstant.SHARED_PREFERENCE_FULL_NAME, fullName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PreferenceConstant.IS_LOGGED_IN, true)
        editor.apply()
    }
}