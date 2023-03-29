package com.example.whatsmyrisk.MyAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.BuisnessDetailActivity
import com.example.whatsmyrisk.R

class SearchResultAdapter(val reslutlist:List<String>, val context:Context) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {

        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.single_search_page_layout,parent,false)

        return SearchResultViewHolder(v)

    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {

        holder.title.setText(reslutlist[position])

        holder.view_details.setOnClickListener(){
            context.startActivity(Intent(this.context,BuisnessDetailActivity::class.java))
        }

    }

    override fun getItemCount(): Int {

        return  reslutlist.size;
    }

    class SearchResultViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView)
    {
        val title : TextView = itemView.findViewById(R.id.tv_title)
        val view_details:TextView=itemView.findViewById(R.id.btn_view_Details)
    }

}