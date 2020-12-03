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
import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Category
import com.dfl.newsapi.enums.Country
import com.example.newsmate.BuildConfig.DEBUG
import com.koushikdutta.ion.Ion
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNewsArticle()


        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)

        //Tries to pull news info
        //val newsApiRepository = NewsApiRepository("0f08db7fe14342799c6f6ea2be6623fe")
        //getNewsArticle(newsApiRepository)
        //Log.d("JSON", obj.toString())
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

    private fun getNewsArticle(){
        Ion.with(this)
            .load("GET", "https://newsapi.org/v2/top-headlines?country=us&apiKey=0f08db7fe14342799c6f6ea2be6623fe")
            .setHeader("user-agent", "insomnia/2020.4.1")
            .asString()
            //will throw an exception if does not work
            .setCallback { ex, result ->
                val json = JSONObject(result)
                Log.d("full object", json.toString())
                populateList(json)
            }
    }

    private fun processArticle(articleData: String){
        val myJSON = JSONObject(articleData)
        val myEx = myJSON.getString("title")
        Log.d("ArtTitle", myEx)
    }



    //Gathers data from a json object containing a list of articles
    private fun populateList(json: JSONObject){
        val list = ArrayList<ArticleModel>()
        val jsonArr = json.getJSONArray("articles")


        val myImageList = arrayOf(R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy,R.drawable.happy_person,R.drawable.laptop_guy)
        val myPublisherList = arrayOf("Pub1", "Pub2", "Pub3", "pub4", "pub5", "pub6", "pub7", "pub8", "pub9", "pub10")
        val exTit = json.getString("status")
        val myTitleList = arrayOf(exTit,exTit,exTit,exTit,exTit,exTit,exTit,exTit,exTit,exTit)
        val exampleSum = "This is some summary text about an article that doesn't exist and you're reading some meaningless words..."
        val mySummaryList = arrayOf(exampleSum)


        for (i in 0..9) {
            val obj = jsonArr.getJSONObject(i)
            val article = ArticleModel()
            article.setImages(myImageList[i])
            article.setPublishers(myPublisherList[i])
            article.setTitles(obj.getString("title"))
            article.setSummaries(exampleSum)
            list.add(article)
        }

        displayRecycler(list)
    }

    //Displays a list of articles in a recycler view
    private fun displayRecycler(list: ArrayList<ArticleModel>){
        val recyclerView = findViewById<View>(R.id.article_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        val artAdapter = ArticleAdapter(list)
        recyclerView.adapter = artAdapter
    }
}