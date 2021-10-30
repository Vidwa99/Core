package com.example.core3

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import java.io.InputStreamReader
import java.nio.file.Paths






class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var countryList: ArrayList<Country>
    private val propertyRegex = ",".toRegex()
    lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializieUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.last ->{
                preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                val lastSelection = preferences.getString("Selected","")
                Snackbar.make(this.findViewById(R.id.last), "Last Selection was "+lastSelection.toString(), Snackbar.LENGTH_LONG).show();

            }
        }

        return super.onOptionsItemSelected(item)
    }
    fun initializieUI() {


        val fullText = getTextFromResources(
            getResources().getIdentifier(
                "medal_list",
                "raw", getPackageName()
            )
        );
        parseFullTextToLines(fullText)
        setRecyclerView()

    }

    fun parseFullTextToLines(text: String) {
        countryList = ArrayList<Country>()                       // declare new country array list
        val lines: List<String> =
            text.split(System.getProperty("line.separator"))        // parse and save lines into a list of String
        lines.forEach {
            parseLineToLocation(it) //pass each line to function
        }
    }

    fun parseLineToLocation(lineText: String) {
        val strs = lineText.split(",").toTypedArray()

       // (name, IOC,Times, Gold, Silver, Bronze)//split object by comma "," to find name, lat, long, timezone in order
    val country = Country(strs[0], strs[1],strs[2] as Int, strs[3] as Int, strs[4] as Int, strs[5] as Int)//create a new location object
    countryList.add(country)                                                 //add location to lists of locations
    //Log.i("FILES", "$location ${locationList.indexOf(location)}")
}
    fun getTextFromResources( resourceId: Int): String {
        return applicationContext.resources.openRawResource(resourceId).use {
            it.bufferedReader().use {
                it.readText()
            }
        }
    }
    fun setRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.rvCountryList) //call recycler view layout widget


        // specify an adapter
        val mAdapter = com.example.core3.RecyclerViewAdapter(
            CountryList = countryList
        )
        recyclerView.setAdapter(mAdapter)
    }




}





