package com.example.whatsmyrisk.PageFragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.whatsmyrisk.MyConstants.Constants
import com.example.whatsmyrisk.MyModel.CountryList
import com.example.whatsmyrisk.MyModel.CountryModel
import com.example.whatsmyrisk.MyModel.UserDetaisl
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.PhoneNumberActivity
import com.example.whatsmyrisk.R
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    var country_name_list: java.util.ArrayList<String> = ArrayList()
    var country_id_list: java.util.ArrayList<String> = ArrayList()

    var etUserName: TextView? = null
    var etBuisnessName: TextView? = null
    var etBuisnessCategory: TextView? = null
    var etGstNumnber: TextView? = null
    var etMobileNo: TextView? = null
    var etMobileNo2: TextView? = null
    var etMobileNo3: TextView? = null
    var etEmailId: TextView? = null
    var etWebsite: TextView? = null
    var etBusinessName: TextView? = null
    var etBusinessType: TextView? = null
    var etCountry: TextView? = null
    var etState: TextView? = null
    var etCity: TextView? = null



    lateinit var btn_welcome_process: TextView
    lateinit var userDetails: UserDetaisl


    var select_country_id: String = ""
    var select_state_id: String = ""
    var select_city_id: String = ""
    var select_category_id: String = ""
    var select_buisnessType_id: String = ""

    var token = ""
    var tokentype = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.view_fragment_layout, container, false)
//        var userDetails = arguments?.getSerializable("userDetails") as UserDetaisl
//        Log.e("userDetails","ReportFragment >>  $userDetails")


        etUserName = view.findViewById<TextView>(R.id.tv_name)
        etGstNumnber = view.findViewById<TextView>(R.id.tv_gst_number)
        etMobileNo = view.findViewById<TextView>(R.id.tv_mobile)
        etMobileNo2 = view.findViewById<TextView>(R.id.tv_mobile_second)
        etEmailId = view.findViewById<TextView>(R.id.tv_email)
        etWebsite = view.findViewById<TextView>(R.id.tv_website)
        etBuisnessName = view.findViewById<TextView>(R.id.tv_buisness_category)
        etBusinessType = view.findViewById<TextView>(R.id.tv_buisness_type_name)
        etCountry = view.findViewById<TextView>(R.id.tv_country)
        etState = view.findViewById<TextView>(R.id.tv_state)
        etCity = view.findViewById<TextView>(R.id.tv_city)
        etBuisnessCategory = view.findViewById<TextView>(R.id.tv_category_name)




        Log.e("GET  Category  Name ",""+PrefManager.getString(view.context, PrefManager.category_name).toString())
        Log.e("GET  Category  Name ",""+PrefManager.getString(view.context, PrefManager.business_category_id).toString())



        etUserName!!.setText(PrefManager.getString(view.context, PrefManager.first_name).toString())
        etBuisnessName!!.setText(PrefManager.getString(view.context, PrefManager.business_name).toString())
        etGstNumnber!!.setText(PrefManager.getString(view.context, PrefManager.gst_number).toString())
        etMobileNo!!.setText(PrefManager.getString(view.context, PrefManager.mobile_number).toString())
        etMobileNo2!!.setText(PrefManager.getString(view.context, PrefManager.mobile_number2).toString())
        etEmailId!!.setText(PrefManager.getString(view.context, PrefManager.email).toString())
        etWebsite!!.setText(PrefManager.getString(view.context, PrefManager.website).toString())
        etBuisnessCategory!!.setText(PrefManager.getString(view.context, PrefManager.category_name).toString())
        etBusinessType!!.setText(PrefManager.getString(view.context, PrefManager.businesstype_name).toString())
        etCountry!!.setText(PrefManager.getString(view.context, PrefManager.country_name).toString())
        etState!!.setText(PrefManager.getString(view.context, PrefManager.state_name).toString())
        etCity!!.setText(PrefManager.getString(view.context, PrefManager.city_name).toString())


        if(!context?.let { Constants.getConnectionStatus(it) }!!)
        {
            Toast.makeText(context,"Please Check Internet Connection ",Toast.LENGTH_SHORT).show()
        }

        view.findViewById<TextView>(R.id.btn_edit_profile).setOnClickListener(){
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, ReportFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

        view.findViewById<TextView>(R.id.btn_log_out).setOnClickListener(){
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle("Log Out")
            builder.setMessage("Are you sure you want to logout ?")
            builder.setPositiveButton("Yes",DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                PrefManager.setString(view.context, PrefManager.first_name,"")
                PrefManager.setString(view.context, PrefManager.business_name,"")
                PrefManager.setString(view.context, PrefManager.gst_number,"")
                PrefManager.setString(view.context, PrefManager.mobile_number,"")
                PrefManager.setString(view.context, PrefManager.email,"")
                PrefManager.setString(view.context, PrefManager.website,"")
                PrefManager.setString(view.context, PrefManager.MOBILE_NUMBER, "")
                PrefManager.setString(view.context, PrefManager.OTP, "")
                PrefManager.setString(view.context, PrefManager.ACCESS_TOKEN, "")
                PrefManager.setString(view.context, PrefManager.TOKEN_TYPE, "")

                var intent : Intent = Intent(view.context,PhoneNumberActivity::class.java)
                view.context.startActivity(intent)
                startActivity(intent)
                activity?.finish()

            })
            builder.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->

                return@OnClickListener
            })
            builder.show()

        }


















        return view

    }


}





