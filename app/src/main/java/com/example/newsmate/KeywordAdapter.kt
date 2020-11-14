package com.example.newsmate

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class KeywordAdapter (private val keywordArray: MutableList<KeywordModel>) : RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {
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
    }

    //max size of views to be generated
    override fun getItemCount(): Int {
        return keywordArray.size
    }

    /*
    Parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        //assigns values to the views so can be modified
        var keywordView = itemView.findViewById<View>(R.id.keyword_title) as TextView
        var imgView = itemView.findViewById<View>(R.id.cross) as ImageView


        init {
            imgView.setOnClickListener(this)
        }

        //makes on click function for holder to react to
        override fun onClick(v: View) {
            val msg = keywordView.text
            val snackbar = Snackbar.make(v, "$msg are the best!", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}