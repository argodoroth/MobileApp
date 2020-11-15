package com.example.newsmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_menu.*

class KeywordsActivity : AppCompatActivity(){
    val keywords = populateList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keywords)

        //setting up the recycler view
        val recyclerView = findViewById<View>(R.id.keyword_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        val keyAdapter = KeywordAdapter(keywords)
        recyclerView.adapter = keyAdapter

        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)
    }

    //Creates the option menu by inflating the layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Adds value to the list, then recreates the recycler view
    fun addToList(view: View) {
        val keyword = KeywordModel()
        val textInput = findViewById<TextView>(R.id.keywordFieldData)
        keyword.setKeywords(textInput.text.toString())
        keywords.add(0,keyword)
        textInput.text = ""
        val recyclerView = findViewById<View>(R.id.keyword_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        val keyAdapter = KeywordAdapter(keywords)
        recyclerView.adapter = keyAdapter
    }

    //for moment used to make list of stub data, will later be used to populate with taken data
    private fun populateList(): ArrayList<KeywordModel>{
        val list = ArrayList<KeywordModel>()
        val myKeyList = arrayOf("Title1", "Title2", "Title3", "Title4", "Title5", "Title6", "Title7", "Title8","Title9", "Title10")


        for (i in 0..9) {
            val keyword = KeywordModel()
            keyword.setKeywords(myKeyList[i])
            list.add(keyword)
        }

        return list
    }
}
