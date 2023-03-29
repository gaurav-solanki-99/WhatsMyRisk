package com.example.whatsmyrisk

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsmyrisk.MyModel.CountryList
import com.example.whatsmyrisk.MyModel.CountryModel
import com.example.whatsmyrisk.MyModel.Customers
import com.example.whatsmyrisk.MyModel.StatusMessage
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.PageFragment.HomeFragment
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Update_Vendor_FeedBack_Activity : AppCompatActivity()  {

    lateinit  var vendorlist:ArrayList<Customers>
    var dialog: Dialog? = null


    var countryAllList : ArrayList<CountryModel> = ArrayList()


    var token=""
    var tokentype=""
    var feedBackValue=""
    lateinit var updateBtn: TextView

    lateinit var name: String
    lateinit var vendor_id:String
    lateinit  var business_name: String
    var  business_category =0
    var business_sub_category = 1
    var business_type=0
    lateinit  var gst_number: String
    var country=0
    var state=0
    var city=0
    lateinit  var mobile_number: String
    lateinit  var mobile_number2: String
    lateinit  var email: String
    lateinit  var website: String
    var rating=0
    var paymaster=0
    lateinit var rupees: String
    lateinit  var description: String
    lateinit var feedback: String


    lateinit var etname: EditText
    lateinit var etbusiness_name: EditText
    lateinit var etgst_number: EditText
    lateinit var etmobile_number: EditText
    lateinit var etmobile_number2: EditText
    lateinit var etemail: EditText
    lateinit var etwebsite: EditText
    lateinit var etrupees: EditText
    lateinit var etdescription: EditText
    lateinit var etfeedback: EditText





    lateinit var spinner_buisness_category : Spinner
//    lateinit var spinner_buisness_sub__category : Spinner
    lateinit var spinner_buisness_type : Spinner
    lateinit var spinner_countyr: Spinner
    lateinit var spinner_state : Spinner
    lateinit var spinner_city : Spinner
    lateinit var spinner_rating : Spinner
    lateinit var spinner_paynaster : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_vendor_feed_back_form)
        supportActionBar?.hide()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        updateBtn=findViewById<TextView>(R.id.btn_vendor_update)



        val languages = resources.getStringArray(R.array.Languages)
        spinner_buisness_category = findViewById<Spinner>(R.id.spinner_buisness_category)
//        spinner_buisness_sub__category = findViewById<Spinner>(R.id.spinner_buisness_subCategory)
        spinner_buisness_type = findViewById<Spinner>(R.id.spinner_buisness_type)
        spinner_countyr = findViewById<Spinner>(R.id.spinner_countyr)
        spinner_state = findViewById<Spinner>(R.id.spinner_state)
        spinner_city = findViewById<Spinner>(R.id.spinner_city)

        //Edit Text Find
        etname=findViewById<EditText>(R.id.et_feed_name)
        etbusiness_name=findViewById<EditText>(R.id.et_feed_buisness_name)
        etgst_number=findViewById<EditText>(R.id.et_feed_gst_number)
        etmobile_number=findViewById<EditText>(R.id.et_feed_mobile_number)
        etmobile_number2=findViewById<EditText>(R.id.et_feed_mobile_number2)
        etemail=findViewById<EditText>(R.id.et_feed_email_id)
        etwebsite=findViewById<EditText>(R.id.et_feed_website)
        etrupees=findViewById<EditText>(R.id.et_feed_rupee)
        etdescription=findViewById<EditText>(R.id.et_feed_experince)
        etfeedback=findViewById<EditText>(R.id.et_feed_feedback)

        findViewById<View>(R.id.btn_back_bar).setOnClickListener(){
            onBackPressed()
        }

        var bundle: Bundle? = intent.extras
        var index : Int? =  bundle?.getInt("INDEX")
        var user :String? = bundle?.getString("User")
        vendorlist = bundle?.getSerializable("List") as ArrayList<Customers>

        findViewById<TextView>(R.id.aap_heading).setText("Update "+user)
        findViewById<TextView>(R.id.page_desc).setText("Update Feedback About A$user")


        Log.e("Updated Index is ",""+index.toString())
        Log.e("User ",""+user.toString())
        Log.e("vendorlist",""+vendorlist.toString())
        Log.e("Selected_sutomer",""+vendorlist[index!!].name)

        getAllCountryList(this,vendorlist[index])
        getAllCategoryList(vendorlist[index])
        getAllBuisnessTypeList(vendorlist[index])
        getAllStateList(vendorlist[index].countryId!!.toInt(),vendorlist[index],true)
        getAllCityList(vendorlist[index].stateId!!.toInt(),vendorlist[index],true)


            val adapter6 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.Rating))
            adapter6.setDropDownViewResource(R.layout.single_spinner_items)
        findViewById<View>(R.id.search_rating2)!!.setOnClickListener(View.OnClickListener {
            // Initialize dialog
            dialog = Dialog(this)

            // set custom dialog
            dialog!!.setContentView(R.layout.dialog_searchable_spinner)

            // set custom height and width
            //dialog!!.window!!.setLayout(600, 800)

            // set transparent background
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // show dialog
            dialog!!.show()

            // Initialize and assign variable
            val editText = dialog!!.findViewById<EditText>(R.id.edit_text)
            val heading = dialog!!.findViewById<TextView>(R.id.dialog_heading)
            heading.setText("Rating")



            val listView: ListView = dialog!!.findViewById(R.id.list_view)


            // set adapter
            listView.adapter = adapter6
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter6.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable) {}
            })
            listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                        // set selected item on textView
                        findViewById<TextView>(R.id.search_rating2)!!.setText(adapter6.getItem(position))



                        rating=position

                        Log.e("Selected Rating ","Paymaster is " +rating.toString())


                        dialog!!.dismiss()
                    }
        })




            val adapter7 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.Paymaster))
            adapter7.setDropDownViewResource(R.layout.single_spinner_items)
        findViewById<View>(R.id.search_paymaster2)!!.setOnClickListener(View.OnClickListener {
            // Initialize dialog
            dialog = Dialog(this)

            // set custom dialog
            dialog!!.setContentView(R.layout.dialog_searchable_spinner)

            // set custom height and width
            //dialog!!.window!!.setLayout(600, 800)

            // set transparent background
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            // show dialog
            dialog!!.show()

            // Initialize and assign variable
            val editText = dialog!!.findViewById<EditText>(R.id.edit_text)
            val heading = dialog!!.findViewById<TextView>(R.id.dialog_heading)
            heading.setText("Paymaster")



            val listView: ListView = dialog!!.findViewById(R.id.list_view)


            // set adapter
            listView.adapter = adapter7
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter7.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable) {}
            })
            listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                        // set selected item on textView
                        findViewById<TextView>(R.id.search_paymaster2)!!.setText(adapter7.getItem(position))



                        paymaster=position

                        Log.e("Selected Paymaster ","Paymaster is " +paymaster.toString())


                        dialog!!.dismiss()
                    }
        })




        setAllfields(vendorlist[index])



        findViewById<TextView>(R.id.search_paymaster2).text= resources.getStringArray(R.array.Paymaster)[vendorlist[index].paymaster!!.toInt()]
        findViewById<TextView>(R.id.search_rating2).text= vendorlist[index].rating.toString()
        business_category=vendorlist[index].businessCategoryId!!.toInt();
        business_type=vendorlist[index].businessTypeId!!.toInt();
        country=vendorlist[index].countryId!!.toInt();
        state=vendorlist[index].stateId!!.toInt();
        city=vendorlist[index].cityId!!.toInt();
        rating=vendorlist[index].rating!!.toInt();
        paymaster=vendorlist[index].paymaster!!.toInt();



        updateBtn.setOnClickListener()
        {


            if(validate())
            {
                GetRetrofitInstance.instance.UpdateVendoreFeedbackApi(PrefManager.getString(this, PrefManager.TOKEN_TYPE) + " " + PrefManager.getString(this, PrefManager.ACCESS_TOKEN),

                        name,
                        business_name,
                        business_category,
                        business_sub_category,
                        business_type,
                        gst_number,
                        country,
                        state,
                        city,
                        mobile_number,
                        email,
                        website,
                        rating,
                        paymaster,
                        rupees,
                        description,
                        feedback,
                        vendor_id,
                        mobile_number2
                ).enqueue(object : Callback<StatusMessage>{
                    override fun onResponse(call: Call<StatusMessage>, response: Response<StatusMessage>) {
                        Log.e("UpdateVendor Responce ",""+response.body()!!.status)
                        if(response.body()!!.status==true)
                        {
                            Toast.makeText(this@Update_Vendor_FeedBack_Activity,""+response.body()!!.message,Toast.LENGTH_SHORT).show()


                            var intent :Intent= Intent(this@Update_Vendor_FeedBack_Activity,DashboardActivity::class.java)
                            startActivity(intent)
                            finish()




                        }

                    }

                    override fun onFailure(call: Call<StatusMessage>, t: Throwable) {

                        Log.e("Update Vendor Responce ",""+t.message)

                    }

                })


            }
        }








    }

    fun setAllfields(customers: Customers)
    {
        vendor_id=customers.id.toString()
        etname.setText(customers.name)
        etbusiness_name.setText(customers.businessName)
        etgst_number.setText(customers.gstNumber)
        etmobile_number.setText(customers.mobileNumber)
        etmobile_number2.setText(customers.mobileNumber2)
        etemail.setText(customers.email)
        etwebsite.setText(customers.website)
        etrupees.setText(customers.rupees)
        etdescription.setText(customers.description)
        etfeedback.setText(customers.feedback)


    }

    fun validate():Boolean{

        name=etname.text.toString()
        business_name=etbusiness_name.text.toString()
        gst_number=etgst_number.text.toString()
        mobile_number=etmobile_number.text.toString()
        mobile_number2=etmobile_number2.text.toString()
        email=etemail.text.toString()
        website=etwebsite.text.toString()
        rupees=etrupees.text.toString()
        description=etdescription.text.toString()
        feedback=etfeedback.text.toString()


        if (name.isNullOrEmpty()){
            etname?.requestFocus()
            etname?.error="Please Enter Name"
            return false
        }
        if (business_name.isNullOrEmpty()){
            etbusiness_name?.requestFocus()
            etbusiness_name?.error="Please Enter Buisness Name"
            return false
        }
        if(business_category==0)
        {
            Log.e("","Please Select Business Category")
            Toast.makeText(this,"Please Select Business Category",Toast.LENGTH_SHORT).show()
            return false
        }
        if(business_type==0)
        {
            Toast.makeText(this,"Please Select Business Type",Toast.LENGTH_SHORT).show()
            return false
        }


        if(country==0)
        {
            Toast.makeText(this,"Please Select Country Type",Toast.LENGTH_SHORT).show()
            return false
        }
        if(state==0)
        {
            Toast.makeText(this,"Please Select State Type",Toast.LENGTH_SHORT).show()
            return false
        }
        if(city==0)
        {
            Toast.makeText(this,"Please Select City Type",Toast.LENGTH_SHORT).show()
            return false
        }
        if (mobile_number.length!=10){   //mobile_alternate!!.length!==10
            etmobile_number?.requestFocus()
            etmobile_number?.error="Mobile number should be 10 digits"
            return false
        }

        if(mobile_number2.isNotEmpty()) {

                if (mobile_number2.length!=10){
                    etmobile_number2?.requestFocus()
                    etmobile_number2?.error="Mobile number should be 10 digits"
                    return false
                }
        }
        if(email.isNotEmpty())
        {
            //        if (email.isNullOrEmpty()){
//            //EMAIL_ADDRESS_PATTERN.matcher(email).matches();
//            etemail?.requestFocus()
//            etemail?.error="Please Enter Email"
//            return false
//        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etemail?.requestFocus()
            etemail?.error="Please Enter Valid  Email"
            return false
        }
        }
//

//
//
//        if (website.isNullOrEmpty()){
//            etwebsite?.requestFocus()
//            etwebsite?.error="Please Enter Website"
//            return false
//        }

        if(rating==0)
        {
            Toast.makeText(this,"Please Select Rating",Toast.LENGTH_SHORT).show()
            return false
        }
        if(paymaster==0)
        {
            Toast.makeText(this,"Please Select Paymaster",Toast.LENGTH_SHORT).show()
            return false
        }
        if (rupees.isNullOrEmpty())
        {
            etrupees?.requestFocus()
            etrupees?.error="Please Enter Rupees"
            return false
        }
        if (rupees.equals("0"))
        {
            etrupees?.requestFocus()
            etrupees?.error="Zero rupees not accepted"
            return false
        }
        if (rupees.startsWith("0"))
        {
            etrupees?.requestFocus()
            etrupees?.error="Zero rupees not accepted"
            return false
        }
        if (rupees.startsWith(".")==true){
            etrupees?.requestFocus()
            etrupees?.error="Rupees not accepted"
            return false
        }
        if (rupees.toInt()==0)
        {
            etrupees?.requestFocus()
            etrupees?.error="Zero rupees not accepted"
            return false
        }
//        if (description.isNullOrEmpty()){
//            etdescription?.requestFocus()
//            etdescription?.error="Please Enter Descriptions"
//            return false
//        }
//        if (feedback.isNullOrEmpty()){
//            etfeedback?.requestFocus()
//            etfeedback?.error="Please Enter Feedback"
//            return false
//        }



        return true
    }
    fun getAllCountryList(context: Context,customers: Customers) :ArrayList<CountryModel>{
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token= PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)





        GetRetrofitInstance.instance.countryApi(tokentype + " " + token).enqueue(object :
            Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllCountryList", "response : " + response.body())

                countryAllList = countryList!!.data
                Log.e("getAllCountryList", "countryAllList : " + countryAllList.size.toString())


                Log.e("All Country List ", countryAllList.size.toString())


                var country_name_list: java.util.ArrayList<String> = ArrayList()
                var country_id_list: java.util.ArrayList<String> = ArrayList()
               // country_name_list.add("Select Country")
             ///   country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())
                var selectedValue =   country_name_list[country_id_list.indexOf(customers.countryId.toString()).toInt()]
                findViewById<TextView>(R.id.search_spinner_country)!!.setText(selectedValue)




                val adapter3 =  ArrayAdapter(
                    context,
                    android.R.layout.simple_dropdown_item_1line,
                    country_name_list
                )
                adapter3.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_countyr.adapter = adapter3








                spinner_countyr.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parentView: AdapterView<*>?,
                            selectedItemView: View,
                            position: Int,
                            id: Long
                        ) {
                            (parentView!!.getChildAt(0) as TextView).setTextColor(
                                resources.getColor(
                                    R.color.black
                                )
                            )
                            country = country_id_list.get(position).toInt()
                            getAllStateList(country,customers,false)
                        }

                        override fun onNothingSelected(parentView: AdapterView<*>?) {}
                    }

                findViewById<View>(R.id.search_spinner_country)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@Update_Vendor_FeedBack_Activity)

                    // set custom dialog
                    dialog!!.setContentView(R.layout.dialog_searchable_spinner)

                    // set custom height and width
                    //dialog!!.window!!.setLayout(600, 800)

                    // set transparent background
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    // show dialog
                    dialog!!.show()

                    // Initialize and assign variable
                    val editText = dialog!!.findViewById<EditText>(R.id.edit_text)
                    val heading = dialog!!.findViewById<TextView>(R.id.dialog_heading)
                    heading.setText("Country")



                    val listView: ListView = dialog!!.findViewById(R.id.list_view)




                    // set adapter
                    listView.adapter = adapter3
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                                s: CharSequence,
                                start: Int,
                                count: Int,
                                after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                            adapter3.filter.filter(s)
                        }

                        override fun afterTextChanged(s: Editable) {}
                    })
                    listView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_spinner_country)!!.setText(adapter3.getItem(position))


                                val item: String = adapter3.getItem(position).toString()

                                val selectedIndex = country_name_list.indexOf(item)

                                country = country_id_list[selectedIndex].toInt()
                                getAllStateList(country,customers,false)



                                Log.e("Select Auto Value ", "" + item +""+selectedIndex)
                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })









                //  Toast.makeText(context, "All Country List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                // Toast.makeText(context, "CountryList error please try again...", Toast.LENGTH_SHORT).show()
            }

        })

        return countryAllList;
    }

    fun getAllStateList(country_id: Int,customers: Customers,isSelected:Boolean) {
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token= PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)




        GetRetrofitInstance.instance.stateApi(tokentype + " " + token, country_id).enqueue(object :
            Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllStateList", "response : " + response.body())

                countryAllList = countryList!!.data
                Log.e("getAllStateList", "stateyAllList : " + countryAllList.size.toString())


                Log.e("All Country List ", countryAllList.size.toString())


                var state_name_list: java.util.ArrayList<String> = ArrayList()
                var state_id_list: java.util.ArrayList<String> = ArrayList()
               // state_name_list.add("Select State")
              //  state_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    state_name_list.add(response.body()!!.data!![i].name!!)
                    state_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", state_name_list.toString())

                if(isSelected)
                {
                    var selectedValue =   state_name_list[state_id_list.indexOf(customers.stateId.toString()).toInt()]
                    findViewById<TextView>(R.id.search_spinner_state)!!.setText(selectedValue)
                }



                val adapter3 = ArrayAdapter(
                    this@Update_Vendor_FeedBack_Activity,
                    android.R.layout.simple_dropdown_item_1line,
                    state_name_list
                )
                adapter3.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_state.adapter = adapter3








                spinner_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View,
                        position: Int,
                        id: Long
                    ) {
                        (parentView!!.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black))
                        state = state_id_list.get(position).toInt()
                        getAllCityList(state,customers,false)
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
                }

                findViewById<View>(R.id.search_spinner_state)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@Update_Vendor_FeedBack_Activity)

                    // set custom dialog
                    dialog!!.setContentView(R.layout.dialog_searchable_spinner)

                    // set custom height and width
                    //dialog!!.window!!.setLayout(600, 800)

                    // set transparent background
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    // show dialog
                    dialog!!.show()

                    // Initialize and assign variable
                    val editText = dialog!!.findViewById<EditText>(R.id.edit_text)
                    val heading = dialog!!.findViewById<TextView>(R.id.dialog_heading)
                    heading.setText("State")



                    val listView: ListView = dialog!!.findViewById(R.id.list_view)




                    // set adapter
                    listView.adapter = adapter3
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                                s: CharSequence,
                                start: Int,
                                count: Int,
                                after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                            adapter3.filter.filter(s)
                        }

                        override fun afterTextChanged(s: Editable) {}
                    })
                    listView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_spinner_state)!!.setText(adapter3.getItem(position))


                                val item: String = adapter3.getItem(position).toString()

                                val selectedIndex = state_name_list.indexOf(item)

                                state = state_id_list[selectedIndex].toInt()
                                getAllCityList(state,customers,false)



                                Log.e("Select Auto Value ", "" + item +""+selectedIndex)
                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })









                // Toast.makeText(this@Update_Vendor_FeedBack_Activity, "All Country List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                // Toast.makeText(this@Update_Vendor_FeedBack_Activity, "CountryList error please try again...", Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun getAllCityList(state_id: Int,customers: Customers,isSelected: Boolean) {
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token= PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)




        GetRetrofitInstance.instance.cityApi(tokentype + " " + token, state_id).enqueue(object :
            Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllCityList", "response : " + response.body())

                countryAllList = countryList!!.data
                Log.e("getAllCityList", "getAllCityList : " + countryAllList.size.toString())


                var city_name_list: java.util.ArrayList<String> = ArrayList()
                var city_id_list: java.util.ArrayList<String> = ArrayList()
             //   city_name_list.add("Select City")
             //   city_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    city_name_list.add(response.body()!!.data!![i].name!!)
                    city_id_list.add(response.body()!!.data!![i].id!!.toString())
                }

                if(isSelected)
                {
                    var selectedValue =   city_name_list[city_id_list.indexOf(customers.cityId.toString()).toInt()]
                    findViewById<TextView>(R.id.search_spinner_city)!!.setText(selectedValue)
                }



                val adapter4 = ArrayAdapter(
                    this@Update_Vendor_FeedBack_Activity,
                    android.R.layout.simple_dropdown_item_1line,
                    city_name_list
                )
                adapter4.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_city.adapter = adapter4








                spinner_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parentView: AdapterView<*>?,
                        selectedItemView: View,
                        position: Int,
                        id: Long
                    ) {
                        (parentView!!.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black))
                        city = city_id_list.get(position).toInt()

                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
                }

                findViewById<View>(R.id.search_spinner_city)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@Update_Vendor_FeedBack_Activity)

                    // set custom dialog
                    dialog!!.setContentView(R.layout.dialog_searchable_spinner)

                    // set custom height and width
                    //dialog!!.window!!.setLayout(600, 800)

                    // set transparent background
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    // show dialog
                    dialog!!.show()

                    // Initialize and assign variable
                    val editText = dialog!!.findViewById<EditText>(R.id.edit_text)
                    val heading = dialog!!.findViewById<TextView>(R.id.dialog_heading)
                    heading.setText("City")



                    val listView: ListView = dialog!!.findViewById(R.id.list_view)




                    // set adapter
                    listView.adapter = adapter4
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                                s: CharSequence,
                                start: Int,
                                count: Int,
                                after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                            adapter4.filter.filter(s)
                        }

                        override fun afterTextChanged(s: Editable) {}
                    })
                    listView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_spinner_city)!!.setText(adapter4.getItem(position))


                                val item: String = adapter4.getItem(position).toString()

                                val selectedIndex = city_name_list.indexOf(item)

                                city = city_id_list[selectedIndex].toInt()



                                Log.e("Select Auto Value ", "" + item +""+selectedIndex)
                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })








                // Toast.makeText(this@Update_Vendor_FeedBack_Activity, "All getAllCityList List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                //   Toast.makeText(this@Update_Vendor_FeedBack_Activity, "CountryList error please try again...", Toast.LENGTH_SHORT).show()
            }

        })


    }


    fun getAllCategoryList(customers: Customers){
        Log.e("getAllCategoryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token= PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCategoryList", "Methode Call : " + tokentype + " " + token)





        GetRetrofitInstance.instance.categoryApi(tokentype + " " + token).enqueue(object :
            Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllCategoryList", "response : " + response.body())

                // countryAllList = countryList!!.data
                Log.e("getAllCategoryList", "countryAllList : " + countryAllList.size.toString())


                Log.e("All Country List ", countryAllList.size.toString())


                var country_name_list: java.util.ArrayList<String> = ArrayList()
                var country_id_list: java.util.ArrayList<String> = ArrayList()
              //  country_name_list.add("Business Category")
               // country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())
                var selectedValue =   country_name_list[country_id_list.indexOf(customers.businessCategoryId.toString()).toInt()]
                findViewById<TextView>(R.id.search_buisness_category)!!.setText(selectedValue)



                val adapter1 = ArrayAdapter(this@Update_Vendor_FeedBack_Activity, android.R.layout.simple_dropdown_item_1line, country_name_list)
                adapter1.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_buisness_category.adapter = adapter1



                spinner_buisness_category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                        (parentView!!.getChildAt(0) as TextView).setTextColor(
                            resources.getColor(
                                R.color.black
                            )
                        )
                        business_category = country_id_list.get(position).toInt()
                        //getAllSubCategoryList(business_category)
                        //getAllSubCategory(business_category)

                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
                }
                findViewById<View>(R.id.search_buisness_category)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@Update_Vendor_FeedBack_Activity)

                    // set custom dialog
                    dialog!!.setContentView(R.layout.dialog_searchable_spinner)

                    // set custom height and width
                    //dialog!!.window!!.setLayout(600, 800)

                    // set transparent background
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    // show dialog
                    dialog!!.show()

                    // Initialize and assign variable
                    val editText = dialog!!.findViewById<EditText>(R.id.edit_text)
                    val heading = dialog!!.findViewById<TextView>(R.id.dialog_heading)
                    heading.setText("Business Category")



                    val listView: ListView = dialog!!.findViewById(R.id.list_view)




                    // set adapter
                    listView.adapter = adapter1
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                                s: CharSequence,
                                start: Int,
                                count: Int,
                                after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                            adapter1.filter.filter(s)
                        }

                        override fun afterTextChanged(s: Editable) {}
                    })
                    listView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_buisness_category)!!.setText(adapter1.getItem(position))


                                val item: String = adapter1.getItem(position).toString()

                                val selectedIndex = country_name_list.indexOf(item)

                                business_category = country_id_list[selectedIndex].toInt()



                                Log.e("Select Auto Value ", "" + item +""+selectedIndex)
                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })









                // Toast.makeText(this@Update_Vendor_FeedBack_Activity, "All Country List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                //Toast.makeText(this@Update_Vendor_FeedBack_Activity, "CountryList error please try again...", Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun getAllBuisnessTypeList(customers: Customers){
        Log.e("getAllCategoryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token= PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)





        GetRetrofitInstance.instance.buisnessTypeApi(tokentype + " " + token).enqueue(object :
            Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllBuisnessTypeList", "response : " + response.body())

                // countryAllList = countryList!!.data
                Log.e(
                    "getAllBuisnessTypeList",
                    "countryAllList : " + countryAllList.size.toString()
                )


                var country_name_list: java.util.ArrayList<String> = ArrayList()
                var country_id_list: java.util.ArrayList<String> = ArrayList()
              //  country_name_list.add("Business Type")
              //  country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())

                var selectedValue =   country_name_list[country_id_list.indexOf(customers.businessTypeId.toString()).toInt()]
                findViewById<TextView>(R.id.search_buisness_type)!!.setText(selectedValue)


                val adapter2 = ArrayAdapter(
                    this@Update_Vendor_FeedBack_Activity,
                    android.R.layout.simple_dropdown_item_1line,
                    country_name_list
                )
                adapter2.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_buisness_type.adapter = adapter2



                spinner_buisness_type.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parentView: AdapterView<*>?,
                            selectedItemView: View,
                            position: Int,
                            id: Long
                        ) {
                            (parentView!!.getChildAt(0) as TextView).setTextColor(
                                resources.getColor(
                                    R.color.black
                                )
                            )
                            Log.e("Buisness Type Seleted ",""+country_id_list.get(position).toInt())
                            business_type = country_id_list.get(position).toInt()


                        }

                        override fun onNothingSelected(parentView: AdapterView<*>?) {}
                    }

                findViewById<View>(R.id.search_buisness_type)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@Update_Vendor_FeedBack_Activity)

                    // set custom dialog
                    dialog!!.setContentView(R.layout.dialog_searchable_spinner)

                    // set custom height and width
                    //dialog!!.window!!.setLayout(600, 800)

                    // set transparent background
                    dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    // show dialog
                    dialog!!.show()

                    // Initialize and assign variable
                    val editText = dialog!!.findViewById<EditText>(R.id.edit_text)
                    val heading = dialog!!.findViewById<TextView>(R.id.dialog_heading)
                    heading.setText("Business Type")



                    val listView: ListView = dialog!!.findViewById(R.id.list_view)




                    // set adapter
                    listView.adapter = adapter2
                    editText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                                s: CharSequence,
                                start: Int,
                                count: Int,
                                after: Int
                        ) {
                        }

                        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                            adapter2.filter.filter(s)
                        }

                        override fun afterTextChanged(s: Editable) {}
                    })
                    listView.onItemClickListener =
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_buisness_type)!!.setText(adapter2.getItem(position))


                                val item: String = adapter2.getItem(position).toString()

                                val selectedIndex = country_name_list.indexOf(item)

                                business_type = country_id_list[selectedIndex].toInt()
                                Log.e("Select Auto Value ", "" + item +""+selectedIndex)



                                Log.e("Select Auto Value ", "" + item +""+selectedIndex)
                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })









                // Toast.makeText(this@Update_Vendor_FeedBack_Activity, "All Country List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
//                Toast.makeText(
//                        this@Update_Vendor_FeedBack_Activity,
//                        "CountryList error please try again...",
//                        Toast.LENGTH_SHORT
//                ).show()
            }

        })


    }

//    fun getAllSubCategory( id:Int){
//
//        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
//        token= PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
//
//        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)
//
//        GetRetrofitInstance.instance.subcategoryApi(tokentype + " " + token,id).enqueue(object:
//            Callback<CountryList> {
//            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {
//
//                val countryList: CountryList? = response.body()
//                Log.e("getAllCategoryList", "response : " + response.body())
//
//                // countryAllList = countryList!!.data
//                Log.e("getAllCategoryList", "countryAllList : " + countryAllList.size.toString())
//
//
//                Log.e("All Country List ", countryAllList.size.toString())
//
//
//                var country_name_list: java.util.ArrayList<String> = ArrayList()
//                var country_id_list: java.util.ArrayList<String> = ArrayList()
//                country_name_list.add("Buisness Sub Category")
//                country_id_list.add("0")
//                for (i in 0 until response.body()!!.data!!.size) {
//                    country_name_list.add(response.body()!!.data!![i].name!!)
//                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
//                }
//                Log.e("All Country List ", country_name_list.toString())
//
//
//                val adapter1 = ArrayAdapter(this@Update_Vendor_FeedBack_Activity, android.R.layout.simple_dropdown_item_1line, country_name_list)
//                adapter1.setDropDownViewResource(R.layout.single_spinner_items)
//                spinner_buisness_sub__category.adapter = adapter1
//
//
//
//                spinner_buisness_sub__category.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
//                        (parentView!!.getChildAt(0) as TextView).setTextColor(
//                            resources.getColor(
//                                R.color.black
//                            )
//                        )
//                        business_sub_category = country_id_list.get(position).toInt()
//
//                    }
//
//                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
//                }
//
//
//
//
//
//
//
//
//
////               Toast.makeText(
////                       this@Update_Vendor_FeedBack_Activity,
////                       "All Country List" + countryAllList.size.toString(),
////                       Toast.LENGTH_LONG
////               ).show()
//
//
//            }
//
//            override fun onFailure(call: Call<CountryList>, t: Throwable) {
//                Log.e("SubCategory Response","Failure "+t.message)
//
//            }
//
//        })
//    }
}