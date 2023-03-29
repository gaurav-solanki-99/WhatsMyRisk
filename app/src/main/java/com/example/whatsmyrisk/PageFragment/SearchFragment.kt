package com.example.whatsmyrisk.PageFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.BuisnessDetailActivity
import com.example.whatsmyrisk.MyAdapter.SearchResultAdapter
import com.example.whatsmyrisk.MyAdapter.VendorsAdapter
import com.example.whatsmyrisk.MyInterfaces.ServerAddress
import com.example.whatsmyrisk.R

class SearchFragment : Fragment()
{
    private lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View  =   inflater.inflate(R.layout.search_fragment_layout,container,false)
        val listofVendor : List<String> = listOf("Schmidt-Kertzmann","Schmidt-Kertzmann", "Schmidt-Kertzmann")


//          v.findViewById<TextView>(R.id.btn_view_Details).setOnClickListener(){
//               startActivity(Intent(activity,BuisnessDetailActivity::class.java))

//          }


        v.findViewById<ImageView>(R.id.btn_back).setOnClickListener(){
            print("Back Methode Call")
            activity?.onBackPressed()
        }


        var recy2=v.findViewById<RecyclerView>(R.id.rv_search_result)
        val customerAdapter  = SearchResultAdapter(listofVendor,requireContext())
        layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recy2.layoutManager=layoutManager
        recy2.adapter=customerAdapter
        customerAdapter.notifyDataSetChanged()


          return  v;
    }


}