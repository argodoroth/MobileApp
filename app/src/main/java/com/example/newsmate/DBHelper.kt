package com.example.newsmate

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.newsmate.KeywordModel

class SqliteDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_KEYWORDS_TABLE =
            "CREATE TABLE $TABLE_KEYWORDS($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_KEYWORD_TITLE TEXT)"
        db.execSQL(CREATE_KEYWORDS_TABLE)//Will execute the sql to create a tasks db
    }

    //called when the schema of the database is changed
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_KEYWORDS") // this way just removes db
        onCreate(db) // would be better to put into list, then use to populate new db
    }

    fun listKeywords(): MutableList<KeywordModel> {    //must be a mutable list
        val sql = "SELECT * from $TABLE_KEYWORDS"
        val db = this.readableDatabase
        val storeWords = arrayListOf<KeywordModel>()

        val cursor = db.rawQuery(sql, null) //create way of accessing db rows
        if (cursor.moveToFirst()) {
            do {
                val id = Integer.parseInt(cursor.getString(0))//gets value from first column
                val word = cursor.getString(1)
                storeWords.add(KeywordModel(id, word))
            } while (cursor.moveToNext())
        }
        cursor.close() //Like a reader
        return storeWords
    }

    //add the string for the new task, will auto give ID
    fun addKeyword(word: String) {
        val values = ContentValues()
        values.put(COLUMN_KEYWORD_TITLE, word)
        val db = this.writableDatabase
        db.insert(TABLE_KEYWORDS, null, values)
    }

    fun deleteKeyword(id: Int) {
        val db = this.writableDatabase
        //Passes wildcard into where clause to find the row where id == id
        db.delete(TABLE_KEYWORDS, "$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    companion object {
        private const val DATABASE_VERSION = 5
        private const val DATABASE_NAME = "data"
        private const val TABLE_KEYWORDS = "keywords"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_KEYWORD_TITLE = "keyword"
    }

}
