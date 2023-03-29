package com.example.whatsmyrisk.MyAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.R

class CommentsAdapter(val list_comments :List<String>, val context:Context) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.single_comments_layout,parent,false)

        return CommentsViewHolder(view)

    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {

        holder.comments_text.setText(list_comments[position])



    }

    override fun getItemCount(): Int {

        return  list_comments.size

    }

    class CommentsViewHolder(val ItemView:View) : RecyclerView.ViewHolder(ItemView)
    {
          val comments_text : TextView = itemView.findViewById(R.id.tv_comments)
    }
}