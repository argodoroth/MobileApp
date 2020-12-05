package com.example.newsmate.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmate.ArticleModel
import com.example.newsmate.R
import com.example.newsmate.SingleArticleActivity
import com.squareup.picasso.Picasso

class ArticleAdapter (private val articleArray: MutableList<ArticleModel>, val context: Context) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    //Inflate views using layout defined in article_layout.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.article_layout, parent, false)

        return ViewHolder(v, context)
    }

    //binds values to each view holder as is generated
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = articleArray[position]
        //uses picasso loader to get image from the image url
        Picasso.get().load(info.getImages()).into(holder.imgView)
        holder.titleView.text = info.getTitles()
        holder.sumView.text = info.getSummaries()
        holder.pubView.text = info.getPublishers()
        holder.imageURL = info.getImages()
        holder.articleID = info.id
    }

    //max size of views to be generated
    override fun getItemCount(): Int {
        return articleArray.size
    }

    /*
    Parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        //assigns values to the views so can be modified
        var imgView = itemView.findViewById<View>(R.id.icon) as ImageView
        var titleView = itemView.findViewById<View>(R.id.title) as TextView
        var sumView = itemView.findViewById<View>(R.id.summaryText) as TextView
        var pubView = itemView.findViewById<View>(R.id.publisherText) as TextView
        var imageURL = ""
        var articleID = -1

        init {
            itemView.setOnClickListener(this)
        }

        //makes onClick function using context of main activity to take to new screen
        override fun onClick(v: View) {
            val title = titleView.text
            val image = imgView.drawable
            val sum = sumView.text
            val pub = pubView.text
            val intent = Intent(context, SingleArticleActivity::class.java)
            //Passes info across to article display screen
            intent.putExtra("title", title)
            intent.putExtra("publisher", pub)
            intent.putExtra("summary", sum)
            intent.putExtra("imageURL", imageURL )
            intent.putExtra("id", articleID)

            context.startActivity(intent)

            //val snackbar = Snackbar.make(v, "$title are the best!", Snackbar.LENGTH_LONG)
            //snackbar.show()
            //add an intent here!!
        }
    }
}