package com.example.whatsmyrisk

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.MyAdapter.CommentsAdapter
import com.example.whatsmyrisk.MyAdapter.VendorsAdapter

class BuisnessDetailActivity : AppCompatActivity()
{
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buisness_details_layout)
        supportActionBar?.hide()
        val message : String = "Sed ut perspiciatis unde omnis iste natus sit voluptatem accusantium doloremque laudantium, totam rem aperiam,";
        val listofVendor : List<String> = listOf("$message","$message", "$message")

        findViewById<TextView>(R.id.aap_heading).setText("Buisness Detail")
        findViewById<ImageView>(R.id.ic_risk2).visibility=View.GONE

        findViewById<ImageView>(R.id.btn_back_bar).setOnClickListener(){
            print("Back Methode Call")
            onBackPressed()
        }









        //Customer Adapter
        var recy2=findViewById<RecyclerView>(R.id.rv_comments)
        val customerAdapter  = CommentsAdapter(listofVendor,this)
        layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recy2.layoutManager=layoutManager
        recy2.adapter=customerAdapter
        customerAdapter.notifyDataSetChanged()
    }
}