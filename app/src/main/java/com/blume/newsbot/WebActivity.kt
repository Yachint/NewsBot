package com.blume.newsbot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_web.*
import android.webkit.WebViewClient



class WebActivity : AppCompatActivity() {
    lateinit var webUrl : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        intent?.let {
            webUrl = it.getStringExtra("urlWeb")
        }


        mywebView.settings.javaScriptEnabled
        mywebView.setWebViewClient(WebViewClient())
        mywebView.loadUrl(webUrl)
        mywebView.setHorizontalScrollBarEnabled(false)
    }


}
