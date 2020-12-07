package com.example.newsmate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_article.*

//Displays a single article on a separate screen
class SingleArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_article)

        displayArticle()
        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)

        val mDatabase = SqliteDatabase(this)

        val saveButton = findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener(){
            saveArticle(mDatabase)
        }

        val deleteButton = findViewById<Button>(R.id.delete_button)
        deleteButton.setOnClickListener(){
            deleteArticle(mDatabase)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.second_appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun saveArticle(mDatabase: SqliteDatabase){
        val pub = intent.getStringExtra("publisher").toString()
        val title = intent.getStringExtra("title").toString()
        val sum = intent.getStringExtra("summary").toString()
        val imgURL = intent.getStringExtra("imageURL").toString()
        mDatabase.addArticle(title,pub,sum,imgURL)
    }

    private fun deleteArticle(mDatabase: SqliteDatabase){
        val id = intent.getIntExtra("id",-1)
        mDatabase.deleteArticle(id)
        val newIntent = Intent(this,MainActivity::class.java)
        startActivity(newIntent)
    }

    private fun displayArticle(){
        val pub = intent.getStringExtra("publisher")
        val pubView = findViewById<TextView>(R.id.fullPublisherText)
        pubView.text = pub

        val title = intent.getStringExtra("title")
        val titView = findViewById<TextView>(R.id.full_title)
        titView.text = title

        val sum = intent.getStringExtra("summary")
        val sumView = findViewById<TextView>(R.id.full_summary_text)
        sumView.text = sum

        val imgURL = intent.getStringExtra("imageURL")
        val imgView = findViewById<View>(R.id.news_image) as ImageView
        Picasso.get().load(imgURL).into(imgView)
    }

    //Sets up the buttons in the app bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                FirebaseAuth.getInstance().signOut()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}