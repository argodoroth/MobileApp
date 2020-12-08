package com.example.newsmate

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.UrlQuerySanitizer
import com.example.newsmate.KeywordModel

class SqliteDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_KEYWORDS_TABLE =
            "CREATE TABLE $TABLE_KEYWORDS($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_KEYWORD_TITLE TEXT, $COLUMN_KEYWORD_NOTIFY INTEGER)"
        val CREATE_ARTICLES_TABLE =
            "CREATE TABLE $TABLE_ARTICLES($COLUMN_ARTICLE_ID INTEGER PRIMARY KEY, $COLUMN_ARTICLE_TITLE TEXT, $COLUMN_ARTICLE_PUBLISHER TEXT, $COLUMN_ARTICLE_SUMMARY TEXT, $COLUMN_ARTICLE_IMAGE_URL TEXT)"
        db.execSQL(CREATE_ARTICLES_TABLE)
        db.execSQL(CREATE_KEYWORDS_TABLE)//Will execute the sql to create a tasks db
    }

    //called when the schema of the database is changed
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_KEYWORDS") // this way just removes db
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ARTICLES")
        onCreate(db) // would be better to put into list, then use to populate new db
    }

    //Gets a list of the keywords out of the database
    fun listKeywords(): MutableList<KeywordModel> {    //must be a mutable list
        val sql = "SELECT * from $TABLE_KEYWORDS"
        val db = this.readableDatabase
        val storeWords = arrayListOf<KeywordModel>()

        val cursor = db.rawQuery(sql, null) //create way of accessing db rows
        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))//gets value from first column
                val word = cursor.getString(1)
                val notify = cursor.getString(2)
                storeWords.add(KeywordModel(id, word, notify == "1"))
            } while (cursor.moveToNext())
        }
        cursor.close() //Like a reader
        return storeWords
    }

    //Gets a list of the articles out of the database
    fun listArticles(): MutableList<ArticleModel> {    //must be a mutable list
        val sql = "SELECT * from $TABLE_ARTICLES"
        val db = this.readableDatabase
        val storeArticles = arrayListOf<ArticleModel>()

        val cursor = db.rawQuery(sql, null) //create way of accessing db rows
        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))//gets value from first column
                val title = cursor.getString(1)
                val pub = cursor.getString(2)
                val sum = cursor.getString(3)
                val image = cursor.getString(4)
                storeArticles.add(ArticleModel(id, title, pub, sum, image))
            } while (cursor.moveToNext())
        }
        cursor.close() //Like a reader
        return storeArticles
    }

    //add the string for the new task, will auto give ID
    fun addKeyword(word: String) {
        val values = ContentValues()
        values.put(COLUMN_KEYWORD_TITLE, word)
        values.put(COLUMN_KEYWORD_NOTIFY,1)
        val db = this.writableDatabase
        db.insert(TABLE_KEYWORDS, null, values)
    }

    //add the string for the new task, will auto give ID
    fun addArticle(title: String, publisher: String, summary: String, imageURL: String) {
        val values = ContentValues()
        values.put(COLUMN_ARTICLE_TITLE, title)
        values.put(COLUMN_ARTICLE_PUBLISHER, publisher)
        values.put(COLUMN_ARTICLE_SUMMARY, summary)
        values.put(COLUMN_ARTICLE_IMAGE_URL, imageURL)
        val db = this.writableDatabase
        db.insert(TABLE_ARTICLES, null, values)
    }

    //removes keyword from db at id
    fun deleteKeyword(id: Int) {
        val db = this.writableDatabase
        //Passes wildcard into where clause to find the row where id == id
        db.delete(TABLE_KEYWORDS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    //removes article from db at id
    fun deleteArticle(id: Int) {
        val db = this.writableDatabase
        //Passes wildcard into where clause to find the row where id == id
        db.delete(TABLE_ARTICLES, "$COLUMN_ARTICLE_ID = ?", arrayOf(id.toString()))
    }

    //Change if keyword will make notifications
    fun setKeywordNotify(id:Int, bool: Boolean): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        if (bool){
            values.put(COLUMN_KEYWORD_NOTIFY,1)
        } else {
            values.put(COLUMN_KEYWORD_NOTIFY,0)
        }
        db.update(TABLE_KEYWORDS,values,"$COLUMN_ID = ?",arrayOf(id.toString()))
        return true
    }

    //sets up constant for database names
    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "data"
        private const val TABLE_KEYWORDS = "keywords"
        private const val TABLE_ARTICLES = "articles"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_KEYWORD_TITLE = "keyword"
        private const val COLUMN_KEYWORD_NOTIFY = "notify"

        private const val COLUMN_ARTICLE_ID = "_id"
        private const val COLUMN_ARTICLE_TITLE = "title"
        private const val COLUMN_ARTICLE_SUMMARY = "summary"
        private const val COLUMN_ARTICLE_PUBLISHER = "publisher"
        private const val COLUMN_ARTICLE_IMAGE_URL = "imageURL"

    }

}
