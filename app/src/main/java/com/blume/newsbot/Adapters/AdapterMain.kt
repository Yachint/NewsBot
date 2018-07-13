package com.blume.newsbot.Adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blume.newsbot.Pojos.articles
import com.blume.newsbot.R
import com.blume.newsbot.ScrollingActivity
import com.blume.newsbot.WebActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_main_card_view.view.*
import kotlinx.android.synthetic.main.web_view.view.*

class AdapterMain(val articlesLists : ArrayList<articles>,
                  val context: Context) : RecyclerView.Adapter<AdapterMainViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMainViewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.adapter_main_card_view,parent,false)
        return AdapterMainViewHolder(itemView)
    }

    override fun getItemCount(): Int = articlesLists.size

    override fun onBindViewHolder(holder: AdapterMainViewHolder, position: Int) {

        holder.ivImage.visibility = View.VISIBLE
        val str : String? = articlesLists[position].author
        holder.tvTitle.text = articlesLists[position].title
        if(str.isNullOrBlank() || str.isNullOrEmpty())
        {
            Log.d("STR","value :"+str)
            //holder.tvAuthor.text = articlesLists[position].source.name
        }
        //holder.tvAuthor.text = articlesLists[position].author
        holder.tvDesc.text = articlesLists[position].description
        holder.tvDate.text = articlesLists[position].publishedAt
        holder.tvSourceName.text = articlesLists[position].source.name
        val k : String? = articlesLists[position].urlToImage
        Log.d("HOLDER","value of Position :"+position+"  k :"+k)
        if(k.isNullOrEmpty() || k.isNullOrBlank())
        {
            holder.ivImage.visibility = View.GONE
        }
        else
        {
            Picasso.get()
                    .load(articlesLists[position].urlToImage)
                    .placeholder(R.drawable.ic_image_grey)
                    .error(R.drawable.ic_broken_grey)
                    .into(holder.ivImage)
        }


        holder.btnFullarticle.setOnClickListener{
            val intent = Intent(context, ScrollingActivity::class.java)
            intent.putExtra("urlWeb",articlesLists[position].url)
            val ki : String? = articlesLists[position].urlToImage
            if(ki.isNullOrEmpty() || ki.isNullOrBlank())
            {
                intent.putExtra("urlImage","Not Available")
            }
            else
            {
                intent.putExtra("urlImage",ki)
            }

//            k.let {
//                intent.putExtra("urlImage",k)
//            }
//            intent.putExtra("urlImage","Not Available")
            intent.putExtra("title",articlesLists[position].title)
            context.startActivity(intent)
        }

        holder.btnWebButton.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra("urlWeb",articlesLists[position].url)
            context.startActivity(intent)
        }
    }

}

class AdapterMainViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val tvTitle = itemView.tvTitle!!
    val tvSourceName = itemView.tvSourceName!!
    //val tvAuthor = itemView.tvAuthor!!
    val ivImage = itemView.ivImage!!
    val tvDesc = itemView.tvDesc!!
    val btnFullarticle = itemView.btnFullArticle!!
    val tvDate = itemView.tvDate!!
    val btnWebButton = itemView.btnWebArticle!!
}