package com.blume.newsbot

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.telecom.Call
import android.util.Log
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import me.angrybyte.goose.Article
import me.angrybyte.goose.Configuration
import me.angrybyte.goose.ContentExtractor
import java.io.IOException

class ScrollingActivity : AppCompatActivity() {
    lateinit var webUrl : String
    lateinit var imageUrl : String
    lateinit var title : String
    lateinit var body :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        intent?.let {
            webUrl = it.getStringExtra("urlWeb")
            val urlCheck : String = it.getStringExtra("urlImage")
            if(urlCheck != "Not Available")
            {
                imageUrl = urlCheck
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_image_grey)
                        .error(R.drawable.ic_broken_grey)
                        .into(ivFull)
            }
            else
            {
                ivFull.visibility = View.GONE
            }
            title = it.getStringExtra("title")
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        tvFullTitle.text = title



        val v = getBody()
        v.execute(webUrl)

    }

    inner class getBody : AsyncTask<String, Int, String>()
    {

        var details: String? = ""

        override fun onPreExecute() {
            super.onPreExecute()
            progressBarText.visibility = View.VISIBLE
            progressBarText.setProgress(0)
            progressBarText.max = 2000
        }


        override fun doInBackground(vararg params: String?): String? {
            val buffer  = StringBuffer()
            try {

                //Log.d("ASYNC","Connecting to "+ webUrl)
                //val doc  = Jsoup.connect(params[0]!!).get()
                //Log.d("ASYNC","Connected to "+ webUrl)

                val config = Configuration(cacheDir.absolutePath)
                val extractor = ContentExtractor(config)

                val article = extractor.extractContent(webUrl,false)
                details = article.cleanedArticleText
                Log.d("ASYNC","Body 1:"+details)

                if (details == null) {
                    Log.w("ASYNC", "Couldn't load the article text, the page is messy. Trying with page description...");
                    details = article.getMetaDescription();
                    Log.d("ASYNC","Body 2:"+details)
                }





            }catch (e :IOException)
            {

            }

            return details
        }

        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
            Log.d("ASYNC","Substring :"+result)
            progressBarText.visibility = View.GONE
            body = result!!
            tvMainText.text = body

            }
        }

    }

