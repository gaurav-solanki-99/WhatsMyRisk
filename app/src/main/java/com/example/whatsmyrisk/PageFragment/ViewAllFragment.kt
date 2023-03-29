package com.example.whatsmyrisk.PageFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.MyAdapter.CustomerAdapter
import com.example.whatsmyrisk.MyAdapter.VendorsAdapter
import com.example.whatsmyrisk.MyModel.Customers
import com.example.whatsmyrisk.R

class ViewAllFragment : Fragment() {

    private lateinit var layoutManager: LinearLayoutManager
    lateinit var tokentype: String
    lateinit var token: String
    lateinit var customerAllList:ArrayList<Customers>
    lateinit var vendorAllList:ArrayList<Customers>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        var view:View
        view= inflater.inflate(R.layout.view_all_cust_vend,container,false)
        view.findViewById<ImageView>(R.id.btn_back_bar).setOnClickListener(){

            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, HomeFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }


        var data = arguments?.getString("sampleData")
        var user = arguments?.getString("User")

        Log.e("Data On Fragment","Data = $data")
        Log.e("Data On Fragment","user = $user")
        view.findViewById<TextView>(R.id.aap_heading).setText("My All $user"+"s")

        if(user=="Customer")
        {
            customerAllList= arguments?.getSerializable("List") as ArrayList<Customers>
            Log.e("Data On Fragment","customerAllList = $customerAllList")

            var recy2=view.findViewById<RecyclerView>(R.id.list_all)
            val customerAdapter  = CustomerAdapter(customerAllList,requireContext(),true)
            layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            recy2.layoutManager=layoutManager
            recy2.adapter=customerAdapter
            customerAdapter.notifyDataSetChanged()

        }

        if(user=="Vendor")
        {
            vendorAllList= arguments?.getSerializable("List") as ArrayList<Customers>
            Log.e("Data On Fragment","vendorAllList = $vendorAllList")

            var recy=view.findViewById<RecyclerView>(R.id.list_all)
            val vendoreAdapter  = VendorsAdapter(vendorAllList,requireContext(),true)
            layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            recy.layoutManager=layoutManager
            recy.adapter=vendoreAdapter
            vendoreAdapter.notifyDataSetChanged()

        }





        return view
    }
}