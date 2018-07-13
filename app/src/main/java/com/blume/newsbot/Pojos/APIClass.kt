package com.blume.newsbot.Pojos

data class APIClass (
        val status : String,
        val totalResults : Int,
        val articles: ArrayList<articles>
)