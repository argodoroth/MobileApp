package com.example.newsmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmate.adapters.KeywordAdapter

class KeywordsActivity : AppCompatActivity(){
    //val keywords = populateList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keywords)

        //loads up all the keywords stored in the database
        val mDatabase = SqliteDatabase(this)
        val keywords: MutableList<KeywordModel> = mDatabase.listKeywords()
        val keyAdapter = KeywordAdapter(keywords, mDatabase)

        //setting up the recycler view
        val recyclerView = findViewById<View>(R.id.keyword_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        recyclerView.adapter = keyAdapter

        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener(){
            addToKeywords(mDatabase,keyAdapter)
        }
    }

    //Creates the option menu by inflating the layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun addToKeywords(mDatabase: SqliteDatabase, mAdapter: KeywordAdapter){
        val wordInput = findViewById<TextView>(R.id.keywordFieldData)
        val word = wordInput.text.toString()
        if (!TextUtils.isEmpty(word)){
            mAdapter.addKeyword(word)
        }
    }
}