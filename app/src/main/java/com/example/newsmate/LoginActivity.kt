package com.example.newsmate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.emailFieldData)
        val password = findViewById<EditText>(R.id.passwordFieldData)

        //makes sign in function for login button
        //Authorizes the email and password with mAuth function
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {view ->
            mAuth.signInWithEmailAndPassword(
                email.text.toString(),
                password.text.toString()
            )
                .addOnCompleteListener(this) {task ->   //Checks if is the right credentials
                    if (task.isSuccessful){
                        val user = mAuth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("id", user?.email)
                        startActivity(intent)
                    } else {
                        val snackbar = Snackbar.make(view, "Invalid user", Snackbar.LENGTH_LONG)
                        snackbar.show()
                    }
                }
        }

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener { view ->
            mAuth.createUserWithEmailAndPassword(
                email.text.toString(),
                password.text.toString()
            )
                .addOnCompleteListener(this) {task ->   //Checks if is the right credentials
                if (task.isSuccessful){
                    val user = mAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id", user?.email)
                    startActivity(intent)
                } else {
                    val snackbar = Snackbar.make(view, "User not created", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }


    }

    fun buttonClick(view: View){
        val intent = Intent(this, MainActivity::class.java)
        val userField = findViewById<EditText>(R.id.emailFieldData);
        intent.putExtra("Username", userField.text.toString())
        startActivity(intent)
    }

    //fun signIn(email:)
}