package com.example.newsmate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmate.adapters.ArticleAdapter

//fragment to display saved articles
class SavedFragment: Fragment() {
    //Inflates tab and inflates inner recycler view using db data
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_saved,container,false)!!
        val mDatabase = SqliteDatabase(super.getContext()!!)
        val recyclerView = view.findViewById<View>(R.id.saved_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this.getContext()) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        val artAdapter = ArticleAdapter(mDatabase.listArticles(), super.getContext()!!)
        recyclerView.adapter = artAdapter

        return view
    }

    //Will refresh screen whenever an article has been added or deleted
    override fun onResume() {
        super.onResume()
        val mDatabase = SqliteDatabase(super.getContext()!!)
        val view = view!!
        val recyclerView = view.findViewById<View>(R.id.saved_recycler_view) as RecyclerView //bind to layout
        val layoutManager = LinearLayoutManager(this.getContext()) //Allows parent to manipulate views
        recyclerView.layoutManager = layoutManager //binds layout manager to recycler
        val artAdapter = ArticleAdapter(mDatabase.listArticles(), super.getContext()!!)
        recyclerView.adapter = artAdapter
    }
}