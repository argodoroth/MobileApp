package com.example.newsmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun buttonClick(view: View){
        val intent = Intent(this, MainActivity::class.java)
        val userField = findViewById<EditText>(R.id.userFieldData);
        intent.putExtra("Username", userField.text.toString())
        startActivity(intent)
    }
}