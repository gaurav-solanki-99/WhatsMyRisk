package com.example.whatsmyrisk.PageFragment

import android.app.AlertDialog
import android.app.AlertDialog.*
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsmyrisk.AddCustomerActivity
import com.example.whatsmyrisk.AddVendorActivity
import com.example.whatsmyrisk.MyAdapter.CustomerAdapter
import com.example.whatsmyrisk.MyAdapter.VendorsAdapter
import com.example.whatsmyrisk.MyConstants.Constants
import com.example.whatsmyrisk.MyModel.CustomerList
import com.example.whatsmyrisk.MyModel.Customers
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.R
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class HomeFragment : Fragment() {
    private lateinit var layoutManager: LinearLayoutManager
    lateinit var tokentype: String
    lateinit var token: String
    lateinit var customerAllList:ArrayList<Customers>
    lateinit var vendorAllList:ArrayList<Customers>
    lateinit var progressDialog:ProgressDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View
        view = inflater.inflate(R.layout.home_fragment_layout, container, false)
        val listofVendor: List<String> = listOf("Patty O'Furniture", "Aaida Bugg", "Anand Sharma")

        if(!context?.let { Constants.getConnectionStatus(it) }!!)
        {
            Toast.makeText(context,"Please Check Internet Connection ",Toast.LENGTH_SHORT).show()
        }





        tokentype = PrefManager.getString(view.context, PrefManager.TOKEN_TYPE).toString()
        token = PrefManager.getString(view.context, PrefManager.ACCESS_TOKEN).toString()

        view.findViewById<ImageView>(R.id.btn_back_bar).visibility=View.GONE






        view.findViewById<TextView>(R.id.btn_view_all_vendors).setOnClickListener()
        {

            val bundle = Bundle()
            bundle.putString("sampleData", "Example1")
            bundle.putString("User", "Vendor")
            bundle.putSerializable("List", vendorAllList)


             val transaction = activity?.supportFragmentManager?.beginTransaction()
            val fragmentTwo = ViewAllFragment()
            fragmentTwo.arguments = bundle
            transaction?.replace(R.id.container, fragmentTwo)
            transaction?.addToBackStack(null)

            transaction?.commit()
        }
        view.findViewById<TextView>(R.id.btn_view_all_customers).setOnClickListener()
        {

            val bundle = Bundle()
            bundle.putString("sampleData", "Example1")
            bundle.putString("User", "Customer")
            bundle.putSerializable("List", customerAllList)

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val fragmentTwo = ViewAllFragment()
            fragmentTwo.arguments = bundle

            transaction?.replace(R.id.container, fragmentTwo)
            transaction?.addToBackStack(null)

            transaction?.commit()
        }


        view.findViewById<View>(R.id.add_customer_btn_1).setOnClickListener(){

            val intent:Intent= Intent(context,AddCustomerActivity::class.java)


            startActivity(intent);


        }





        view.findViewById<View>(R.id.add_vendor_btn_1).setOnClickListener(){


            val intent:Intent= Intent(context,AddVendorActivity::class.java)

            startActivity(intent);


        }

//        //Vendore Adapter
//        var recy=view.findViewById<RecyclerView>(R.id.list_vender_rv)
//        val vendoreAdapter  = VendorsAdapter(listofVendor,requireContext())
//        layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//        recy.layoutManager=layoutManager
//        recy.adapter=vendoreAdapter
//        vendoreAdapter.notifyDataSetChanged()


        //Customer Adapter
//        var recy2=view.findViewById<RecyclerView>(R.id.list_customer_rv)
//        val customerAdapter  = VendorsAdapter(listofVendor,requireContext())
//        layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//        recy2.layoutManager=layoutManager
//        recy2.adapter=customerAdapter
//        customerAdapter.notifyDataSetChanged()

         progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
       getAllCustomerLis(view)
        getAllVendorList(view)









        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


     fun getAllCustomerLis(v:View){


         val alertDialogBuilder = AlertDialog.Builder(activity).create()
         alertDialogBuilder.show()

         tokentype = PrefManager.getString(v.context, PrefManager.TOKEN_TYPE).toString()
         token = PrefManager.getString(v.context, PrefManager.ACCESS_TOKEN).toString()
         Log.e("Token","$token")

         //tokentype + " " + token
           GetRetrofitInstance.instance.cutomerListApi(tokentype + " " + token).enqueue(object :retrofit2.Callback<CustomerList>{


               override fun onResponse(call: Call<CustomerList>, response: Response<CustomerList>) {
                   alertDialogBuilder.dismiss()

                  // var lenght = response.body()!!.data.size
                   Log.e("Response",""+response.body().toString())
                   //Toast.makeText(context,"CustomerList Success ",Toast.LENGTH_SHORT).show()

                if(response.code()==200) {
                    customerAllList = response.body()!!.data
                    var recy2 = v.findViewById<RecyclerView>(R.id.list_customer_rv)
                    val customerAdapter = CustomerAdapter(customerAllList, requireContext(), false)
                    layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    recy2.layoutManager = layoutManager
                    recy2.adapter = customerAdapter
                    customerAdapter.notifyDataSetChanged()
                }
                   progressDialog.dismiss()
               }

               override fun onFailure(call: Call<CustomerList>, t: Throwable) {
                   alertDialogBuilder.dismiss()
                   progressDialog.dismiss()
                   //Toast.makeText(context,"CustomerList Fail"+t.message,Toast.LENGTH_SHORT).show()
               }

           })
     }

    fun getAllVendorList(v:View){

         val alertDialogBuilder = AlertDialog.Builder(activity).create()
         alertDialogBuilder.show()

         tokentype = PrefManager.getString(v.context, PrefManager.TOKEN_TYPE).toString()
         token = PrefManager.getString(v.context, PrefManager.ACCESS_TOKEN).toString()
         Log.e("Token","$token")

         //tokentype + " " + token
           GetRetrofitInstance.instance.vendorListApi(tokentype + " " + token).enqueue(object :retrofit2.Callback<CustomerList>{


               override fun onResponse(call: Call<CustomerList>, response: Response<CustomerList>) {
                   alertDialogBuilder.dismiss()

                  // var lenght = response.body()!!.data.size
                   Log.e("Vendor Response",""+response.body().toString())
                  // Toast.makeText(context,"Vendor Success ",Toast.LENGTH_SHORT).show()

                  if(response.code()==200)
                  {
                      vendorAllList = response.body()!!.data
                      //Vendore Adapter
                      var recy = v.findViewById<RecyclerView>(R.id.list_vender_rv)
                      val vendoreAdapter = VendorsAdapter(vendorAllList, requireContext(), false)
                      layoutManager =
                          LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                      recy.layoutManager = layoutManager
                      recy.adapter = vendoreAdapter
                      vendoreAdapter.notifyDataSetChanged()
                  }
                   progressDialog.dismiss()
               }

               override fun onFailure(call: Call<CustomerList>, t: Throwable) {
                   alertDialogBuilder.dismiss()
                   progressDialog.dismiss()
                   //Toast.makeText(context,"CustomerList Fail"+t.message,Toast.LENGTH_SHORT).show()
               }

           })
     }

}