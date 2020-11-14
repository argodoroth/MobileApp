package com.example.newsmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.DEBUG
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmate.BuildConfig.DEBUG


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val articles = populateList()

        //setting up the recycler view
        val recyclerView = findViewById<View>(R.id.article_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        val artAdapter = ArticleAdapter(articles)
        recyclerView.adapter = artAdapter

        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)

        //get values from the previous login screen
        val extras = intent.extras

        if (extras == null){

        } else {
            val username = extras.getString("Username")
        }


    }

    //Creates the option menu by inflating the layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_search -> {
                val intent = Intent(this, KeywordsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //for moment used to make list of stub data, will later be used to populate with taken data
    private fun populateList(): ArrayList<ArticleModel>{
        val list = ArrayList<ArticleModel>()
        val myImageList = arrayOf(R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy)
        val myPublisherList = arrayOf("Pub1", "Pub2", "Pub3", "pub4", "pub5", "pub6", "pub7", "pub8", "pub9", "pub10")
        val myTitleList = arrayOf("Title1", "Title2", "Title3", "Title4", "Title5", "Title6", "Title7", "Title8","Title9", "Title10")
        val exampleSum = "This is some summary text about an article that doesn't exist and you're reading some meaningless words..."
        val mySummaryList = arrayOf(exampleSum)


        for (i in 0..9) {
            val article = ArticleModel()
            article.setImages(myImageList[i])
            article.setPublishers(myPublisherList[i])
            article.setTitles(myTitleList[i])
            article.setSummaries(exampleSum)
            list.add(article)
        }

        return list
    }
}