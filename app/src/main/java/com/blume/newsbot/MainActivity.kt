package com.blume.newsbot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.blume.newsbot.Pojos.articles
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.blume.newsbot.Adapters.AdapterMain
import com.blume.newsbot.Pojos.APIClass
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.adapter_main_card_view.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    val articlesLists = ArrayList<articles>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain.setLayoutManager(GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL,false))
        rvMain.adapter = AdapterMain(articlesLists,this)
        APINetworkCall()

    }
    fun APINetworkCall()
    {
        progressBarMain.visibility = View.VISIBLE
        progressBarMain.setProgress(0)
        progressBarMain.max = 2000

        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=in&apiKey=9b51f47a947e4afda04fdddb3c503109")
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
                this@MainActivity.runOnUiThread{
                    rvMain.adapter.notifyDataSetChanged()
                    progressBarMain.visibility = View.GONE
                }
            }
        })
    }
}
