package com.example.newsmate

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.koushikdutta.ion.Ion
import org.json.JSONObject

class NewsService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Broadcast","Service started")

        val mDatabase = SqliteDatabase(this)
        val keywords = mDatabase.listKeywords()
        val keysString = makeSearchString(keywords)

        //Would usually wait a couple of hours, lowered to demonstrate notifications
        Thread(Runnable{
            try {
                Thread.sleep(7000)
            } catch (e: InterruptedException){
                e.printStackTrace()
            }
            val broadcastIntent = Intent()
            broadcastIntent.action = MainActivity.mBroadcastNotifyAction
            getArticleForNotify(broadcastIntent, keysString)
        }).start()


        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Broadcast","Service Destroyed")
    }

    //gets an article using Ion and sends through broadcast with intent
    fun getArticleForNotify(intent: Intent, keyString: String){
        var url = ""
        if (keyString == "") {
            url = "https://newsapi.org/v2/top-headlines?country=gb&apiKey=0f08db7fe14342799c6f6ea2be6623fe"
        } else {
            url =
                "https://newsapi.org/v2/everything?q=$keyString&apiKey=0f08db7fe14342799c6f6ea2be6623fe"

            //Populates list of articles using an ion pull
            Ion.with(this)
                .load("GET", url)
                .setHeader("user-agent", "insomnia/2020.4.1")
                .asString()
                //will throw an exception if does not work
                .setCallback { ex, result ->
                    val json = JSONObject(result)
                    val jsonArr = json.getJSONArray("articles")
                    val obj = jsonArr.getJSONObject(0)
                    intent.putExtra("Title", obj.getString("title"))
                    sendBroadcast(intent)
                }
        }
    }

    //URL encodes keywords with notification as true
    fun makeSearchString(keywords: MutableList<KeywordModel>): String{
        var searchStr = ""
        if (keywords.size>=1){
            for (key in keywords){
                if (key.getNotifies()) {
                    val keyword = key.getKeywords()
                    searchStr += "$keyword%20OR%20"
                }
            }
            searchStr = searchStr.substring(0,searchStr.length-8)
        }
        return searchStr
    }
}