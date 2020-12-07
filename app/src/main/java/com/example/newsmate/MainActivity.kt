package com.example.newsmate

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.newsmate.adapters.ArticleAdapter
import com.example.newsmate.adapters.TabAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.koushikdutta.ion.Ion
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private var notificationHelper: NotificationHelper? = null
    private var mIntentFilter: IntentFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)

        //Setup tab layout with pagers to display in activity
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.pager)
        val tabTitles = resources.getStringArray(R.array.tabs)

        //get keywords to be used in keyword search
        val mDatabase = SqliteDatabase(this)
        val keywords: MutableList<KeywordModel> = mDatabase.listKeywords()
        val search = makeSearchString(keywords)


        //Creates recycler view with new articles
        getNewsArticle(search)

        //Uses intent filter
        this.mIntentFilter = IntentFilter()
        mIntentFilter!!.addAction(mBroadcastNotifyAction)

        //Makes an object to manage notifications
        notificationHelper = NotificationHelper(this)

        //starts news service
        val serviceIntent = Intent(this, NewsService::class.java)
        startService(serviceIntent)
        //stopService(serviceIntent)
        Log.d("Broadcast actions","Done")

        //attaches adapter to tabs and gives titles
        viewPager.adapter = TabAdapter(this)    //
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy{ tab, position ->
                when (position) {
                    0 -> tab.text = tabTitles[0]
                    1 -> tab.text = tabTitles[1]
                }
            }).attach()
    }


    public override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver, mIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }

    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when (intent.action) {
                mBroadcastNotifyAction -> {
                    val artTitle = intent.getStringExtra("Title")
                    Log.d("Broadcast2","Recieved")
                    postNotification(not1,"New story available", artTitle.toString())
                }
            }
        }
    }
    //Creates the option menu by inflating the layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Sets up the buttons in the app bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                postNotification(not1, "Notify!","data")
                return true
            }
            R.id.action_search -> {
                val intent = Intent(this, KeywordsActivity::class.java)
                startActivity(intent)
                return true
            }
            //Reloads recycler view with new search
            R.id.action_refresh -> {
                val mDatabase = SqliteDatabase(this)
                val keywords: MutableList<KeywordModel> = mDatabase.listKeywords()
                val search = makeSearchString(keywords)
                getNewsArticle(search)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    //Gets a json object of list of news articles from newsAPI.org
    private fun getNewsArticle(keyString: String){
        var url = ""

        //will do topheadlines search if no keywords
        if (keyString == "") {
            url = "https://newsapi.org/v2/top-headlines?country=gb&apiKey=0f08db7fe14342799c6f6ea2be6623fe"
        } else {
            url = "https://newsapi.org/v2/everything?q=$keyString&apiKey=0f08db7fe14342799c6f6ea2be6623fe"
        }
        //Populates list of articles using an ion pull
        Ion.with(this)
            .load("GET", url)
            .setHeader("user-agent", "insomnia/2020.4.1")
            .asString()
            //will throw an exception if does not work
            .setCallback { ex, result ->
                val json = JSONObject(result)
                Log.d("full object", json.toString())
                populateList(json)
            }
    }

    //Gathers data from a json object containing a list of articles
    private fun populateList(json: JSONObject){
        val list = mutableListOf<ArticleModel>()
        val jsonArr = json.getJSONArray("articles")

        //Will get items from json and add them to article object
        for (i in 0..9) {
            val obj = jsonArr.getJSONObject(i)
            val source = obj.getJSONObject("source")
            val imageView = findViewById<ImageView>(R.id.icon)

            val title = obj.getString("title")
            val pub = source.getString("name")
            val sum = obj.getString("description")
            val imgURL = obj.getString("urlToImage")

            val article = ArticleModel(0, title, pub, sum, imgURL)
            list.add(article)
        }

        displayRecycler(list)

    }

    //Displays a list of articles in a recycler view
    private fun displayRecycler(list: MutableList<ArticleModel>){
        val recyclerView = findViewById<View>(R.id.article_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        val artAdapter = ArticleAdapter(list, this)
        recyclerView.adapter = artAdapter
    }



    //URL encodes list of keywords so can be used as a search string
    fun makeSearchString(keywords: MutableList<KeywordModel>): String{
        var searchStr = ""
        if (keywords.size>=1){
            for (key in keywords){
                val keyword = key.getKeywords()
                searchStr += "$keyword%20OR%20"
            }
            searchStr = searchStr.substring(0,searchStr.length-8)
        }
        return searchStr
    }


    //Will build notification and then send through notification manager
    fun postNotification(id:Int, title: String, body: String) {
        var notificationBuilder: NotificationCompat.Builder? = null
        when (id) {
            not1 -> notificationBuilder = notificationHelper!!.getNotification1(title,body)
        }
        if (notificationBuilder != null) {
            notificationHelper!!.notify(id,notificationBuilder)
        }
    }

    companion object {
        private const val not1 = 100
        const val mBroadcastNotifyAction = "com.example.broadcast.notify"
    }
}