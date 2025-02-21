package com.example.newsmate.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmate.KeywordModel
import com.example.newsmate.R
import com.example.newsmate.SqliteDatabase

class KeywordAdapter (private val keywordArray: MutableList<KeywordModel>, private val mDatabase: SqliteDatabase) : RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {
    //Inflate views using layout defined in article_layout.xml

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.keyword_layout, parent, false)

        return ViewHolder(v)
    }

    //binds values to each view holder as is generated
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = keywordArray[position]

        holder.keywordView.text = info.getKeywords()
        holder.checkView.isChecked = info.getNotifies()
    }

    //max size of views to be generated
    override fun getItemCount(): Int {
        return keywordArray.size
    }

    //Adds keyword to the database, then to array then notifies the view
    fun addKeyword(word: String) {
        mDatabase.addKeyword(word)
        keywordArray.clear()
        keywordArray.addAll(mDatabase.listKeywords())
        notifyDataSetChanged()
    }

    //Removes from array, then checks for the models id and removes from db
    fun removeKeyword(position: Int) {
        if (position < keywordArray.size){
            val keyword = keywordArray[position]
            keywordArray.removeAt(position)
            mDatabase.deleteKeyword(keyword.id)
            notifyItemRemoved(position)
        }
    }


    /*
    Parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        //assigns values to the views so can be modified
        var keywordView = itemView.findViewById<View>(R.id.keyword_title) as TextView
        var imgView = itemView.findViewById<View>(R.id.cross) as ImageView
        var checkView = itemView.findViewById<CheckBox>(R.id.checkbox)

        init {
            imgView.setOnClickListener(this)
            checkView.setOnClickListener { v: View ->
                val id = keywordArray[adapterPosition].id
                mDatabase.setKeywordNotify(id, checkView.isChecked)
            }
        }

        //makes on click function for holder to react to
        override fun onClick(v: View) {
            val word = keywordView.text
            var position = adapterPosition
            removeKeyword(position)
        }
    }
}