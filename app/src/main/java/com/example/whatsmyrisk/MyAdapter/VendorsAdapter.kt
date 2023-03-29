package com.example.whatsmyrisk.MyAdapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.MyModel.Customers
import com.example.whatsmyrisk.R
import com.example.whatsmyrisk.UpdateFeedBackActivity
import com.example.whatsmyrisk.Update_Vendor_FeedBack_Activity
import com.example.whatsmyrisk.ViewVendorActivity

class VendorsAdapter(val vendorlist:ArrayList<Customers>, val context : Context, val viewAll :Boolean) : RecyclerView.Adapter<VendorsAdapter.VendorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.single_my_added_customer,parent,false)
        return  VendorViewHolder(view)

    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {


        holder.title.setText(vendorlist[position].name)
        holder.caharacter.setText(vendorlist[position].name.toString().first().toString().toUpperCase())
        holder.subtitle.setText(vendorlist[position].businessName)
        holder.btn_icon_edit.setOnClickListener(){
            var intent = Intent(context,Update_Vendor_FeedBack_Activity::class.java)

            val bundle = Bundle()
            bundle.putString("sampleData", "Example1")
            bundle.putString("User", "Vendor")
            bundle.putInt("INDEX",position)
            bundle.putSerializable("List", vendorlist)


            intent.putExtras(bundle)

            context.startActivity(intent)
        }
        holder.btn_view.setOnClickListener(){
            var intent = Intent(context,ViewVendorActivity::class.java)

            val bundle = Bundle()
            bundle.putString("sampleData", "Example1")
            bundle.putString("User", "Vendor")
            bundle.putInt("INDEX",position)
            bundle.putSerializable("List", vendorlist)


            intent.putExtras(bundle)

            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
       var size:Int = vendorlist.size

        if(viewAll)
        {

        }
        else if(size<=3)
        {

        }
        else
        {
            size=3
        }
        return  size
    }
    class VendorViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView)
    {

        val title: TextView = itemView.findViewById(R.id.tv_adc1)
        val caharacter : TextView=itemView.findViewById(R.id.cus_tv1)
        val subtitle : TextView=itemView.findViewById(R.id.list_sub_titile)
        val btn_icon_edit : ImageView=itemView.findViewById(R.id.icon_edit)
        val btn_view : ImageView=itemView.findViewById(R.id.cus_eye)


    }

}