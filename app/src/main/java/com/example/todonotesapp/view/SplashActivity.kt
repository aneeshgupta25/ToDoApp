package com.example.todonotesapp.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todonotesapp.utils.PreferenceConstant
import com.example.todonotesapp.R

class SplashActivity : AppCompatActivity(){

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSharedPreferences();
        checkLoginStatus();
    }
    private fun setupSharedPreferences(){
        sharedPreferences = getSharedPreferences(PreferenceConstant.SHARED_PREFERENCE_FULL_NAME, MODE_PRIVATE)
    }

    private fun checkLoginStatus(){
        var isLoggedIn = sharedPreferences.getBoolean(PreferenceConstant.IS_LOGGED_IN, false)
        if(isLoggedIn){
            val intent = Intent(this@SplashActivity, myNotesActivity::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}