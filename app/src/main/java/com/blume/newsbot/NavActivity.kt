package com.blume.newsbot

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.blume.newsbot.Adapters.AdapterMain
import com.blume.newsbot.Pojos.APIClass
import com.blume.newsbot.Pojos.articles
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.app_bar_nav.*
import kotlinx.android.synthetic.main.content_nav.*
import okhttp3.*
import java.io.IOException

class NavActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var currentChoice : String
    val articlesLists = ArrayList<articles>()
    val worldTop = "https://newsapi.org/v2/top-headlines?language=en&apiKey=9b51f47a947e4afda04fdddb3c503109"
    val indiaTop = "https://newsapi.org/v2/top-headlines?country=in&apiKey=9b51f47a947e4afda04fdddb3c503109"
    val buissInTop = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=9b51f47a947e4afda04fdddb3c503109"
    val sportsInTop= "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=9b51f47a947e4afda04fdddb3c503109"
    val gamingTop = "https://newsapi.org/v2/everything?q=gaming&sortBy=popularity&apiKey=9b51f47a947e4afda04fdddb3c503109"
    val techTop = "https://newsapi.org/v2/top-headlines?category=technology&country=us&apiKey=9b51f47a947e4afda04fdddb3c503109"
    val scienceTop = "https://newsapi.org/v2/top-headlines?category=science&country=us&apiKey=9b51f47a947e4afda04fdddb3c503109"

    val everything = "https://newsapi.org/v2/top-headlines?"
    val category = "category="
    val sortby = "&sortBy="
    val apiKey = "&apiKey=9b51f47a947e4afda04fdddb3c503109"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        setSupportActionBar(toolbar)

        rvNav.setLayoutManager(GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL,false))
        rvNav.adapter = AdapterMain(articlesLists,this)
        APINetworkCall(indiaTop)

        /*fab.setOnClickListener { view ->

            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    //.setAction("Action", null).show()
        }*/



        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        menu_item_pop.setOnClickListener {
            currentChoice += "&sortby=popularity"
            APINetworkCall(currentChoice)

        }

        menu_item_new.setOnClickListener {
            currentChoice += "&sortby=publishedAt"
            APINetworkCall(currentChoice)
        }

        menu_item_rel.setOnClickListener {
            currentChoice += "&sortby=relevancy"
            APINetworkCall(currentChoice)
        }


    }

    fun APINetworkCall(url : String)
    {
        currentChoice = url
        articlesLists.clear()
        rvNav.adapter.notifyDataSetChanged()
        var currentUrl = url
        progressBarNav.visibility = View.VISIBLE
        progressBarNav.setProgress(0)
        progressBarNav.max = 2000

        val client = OkHttpClient()
        val request = Request.Builder()
                .url(currentUrl)
                .build()

        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response!!.body()!!.string()
                Log.d("TAG","onResponse: " + result)
                val gson = GsonBuilder().serializeNulls().create()
                //val collectionType = object : TypeToken<ArrayList<PlaceHolderClass>>() {}.type
                val postsResult= gson.fromJson<APIClass>(result, APIClass::class.java)
                articlesLists.clear()
                articlesLists.addAll(postsResult.articles)
                this@NavActivity.runOnUiThread{
                    rvNav.adapter.notifyDataSetChanged()
                    progressBarNav.visibility = View.GONE
                }
            }
        })
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav, menu)
        val btnSearch = menu.findItem(R.id.action_search)
        val mSearchView = btnSearch.actionView as SearchView
        mSearchView.queryHint = "Search"

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val customSearchParam  = "https://newsapi.org/v2/everything?q="+query+"&sortBy=popularity&apiKey=9b51f47a947e4afda04fdddb3c503109"
                APINetworkCall(customSearchParam)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.action_refresh -> {
                APINetworkCall(currentChoice)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.Personal -> {
                Toast.makeText(this,"Coming Soon!",Toast.LENGTH_SHORT).show()
            }
            R.id.World -> {
                APINetworkCall(worldTop)
            }
            R.id.India -> {
                APINetworkCall(indiaTop)
            }
            R.id.Sports -> {
                APINetworkCall(sportsInTop)
            }
            R.id.Buisness -> {
                APINetworkCall(buissInTop)
            }
            R.id.Technology-> {
                APINetworkCall(techTop)
            }
            R.id.Science-> {
                APINetworkCall(scienceTop)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
