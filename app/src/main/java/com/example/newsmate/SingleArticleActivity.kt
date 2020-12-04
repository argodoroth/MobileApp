package com.example.newsmate

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

class SingleArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_article)

        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }
}