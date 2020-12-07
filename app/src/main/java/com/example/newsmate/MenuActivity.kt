package com.example.newsmate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        //sets up shared preferemces
        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //finds spinner and array of options, and sets default value
        val regions = resources.getStringArray(R.array.Countries)
        val spinner = findViewById<Spinner>(R.id.country_list)
        val regID = regions.indexOf(sharedPref.getString("region","gb"))
        spinner.setSelection(regID)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, regions)
            spinner.adapter = adapter
        }

        //creates a listener that will know which item was selected
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                editor.putString(getString(R.string.region),regions[position]).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinner.setSelection(regID)
            }
        }

        //checks switch
        val switch = findViewById<SwitchMaterial>(R.id.local_news_switch)
        switch.isChecked = sharedPref.getBoolean("local", false)
        switch.setOnClickListener { v: View ->
            editor.putBoolean("local", switch.isChecked).apply()
        }

        //Creates appBar at top of screen
        val appBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        setSupportActionBar(appBar)
}

//Creates the option menu by inflating the layout
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.second_appbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Sets up the buttons in the app bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                FirebaseAuth.getInstance().signOut()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}