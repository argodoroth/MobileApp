package com.example.newsmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_menu)

    //Creates appBar at top of screen
    val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
    setSupportActionBar(appBar)
}

//Creates the option menu by inflating the layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }
}