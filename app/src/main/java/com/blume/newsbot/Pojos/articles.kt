package com.blume.newsbot.Pojos

data class articles (
    val source: source,
    val author : String,
    val title : String,
    val description : String,
    val url : String,
    val urlToImage : String,
    val publishedAt :String
)