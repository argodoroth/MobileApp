package com.example.newsmate

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_article.*

class SingleArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_article)

        displayArticle()
        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)

        val mDatabase = SqliteDatabase(this)

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener(){
            saveArticle(mDatabase)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun saveArticle(mDatabase: SqliteDatabase){
        val pub = intent.getStringExtra("publisher").toString()
        val title = intent.getStringExtra("title").toString()
        val sum = intent.getStringExtra("summary").toString()
        val imgURL = intent.getStringExtra("imageURL").toString()
        mDatabase.addArticle(title,pub,sum,imgURL)
    }

    private fun displayArticle(){
        val pub = intent.getStringExtra("publisher")
        val pubView = findViewById<TextView>(R.id.fullPublisherText)
        pubView.text = pub

        val title = intent.getStringExtra("title")
        val titView = findViewById<TextView>(R.id.fullTitle)
        titView.text = title

        val sum = intent.getStringExtra("summary")
        val sumView = findViewById<TextView>(R.id.fullSummaryText)
        sumView.text = sum

        val imgURL = intent.getStringExtra("imageURL")
        val imgView = findViewById<View>(R.id.newsImage) as ImageView
        Picasso.get().load(imgURL).into(imgView)
    }
}