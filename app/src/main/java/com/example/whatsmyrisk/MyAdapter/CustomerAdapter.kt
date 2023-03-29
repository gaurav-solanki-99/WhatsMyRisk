package com.example.whatsmyrisk.MyAdapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.MyModel.Customers
import com.example.whatsmyrisk.R
import com.example.whatsmyrisk.UpdateFeedBackActivity
import com.example.whatsmyrisk.ViewCustomerActivity

class CustomerAdapter(val customerlis:ArrayList<Customers>, val context:Context, val viewAll :Boolean)  :  RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.single_my_added_customer,parent,false)
        return  CustomerViewHolder(view)

    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {


        holder.title.setText(customerlis[position].name)
        holder.caharacter.setText(customerlis[position].name.toString().first().toString().toUpperCase())
        holder.subtitle.setText(customerlis[position].businessName)

        holder.btn_icon_edit.setOnClickListener(){
            var intent = Intent(context,UpdateFeedBackActivity::class.java)

            val bundle = Bundle()
            bundle.putString("sampleData", "Example1")
            bundle.putString("User", "Customer")
            bundle.putInt("INDEX",position)
            bundle.putSerializable("List", customerlis)


            intent.putExtras(bundle)

            context.startActivity(intent)
        }
        holder.btn_icon_view.setOnClickListener(){
            var intent = Intent(context,ViewCustomerActivity::class.java)

            val bundle = Bundle()
            bundle.putString("sampleData", "Example1")
            bundle.putString("User", "Customer")
            bundle.putInt("INDEX",position)
            bundle.putSerializable("List", customerlis)


            intent.putExtras(bundle)

            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        var size:Int = customerlis.size

        if(viewAll)
        {

        }
        else  if(size<=3)
        {

        }
        else
        {


                size=3


        }


        return  size
    }
    class CustomerViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {

        val title: TextView = itemView.findViewById(R.id.tv_adc1)
        val caharacter : TextView=itemView.findViewById(R.id.cus_tv1)
        val subtitle : TextView=itemView.findViewById(R.id.list_sub_titile)
        val btn_icon_edit : ImageView=itemView.findViewById(R.id.icon_edit)
        val btn_icon_view : ImageView=itemView.findViewById(R.id.cus_eye)


    }

}