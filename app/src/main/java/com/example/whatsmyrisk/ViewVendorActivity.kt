package com.example.whatsmyrisk

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsmyrisk.MyModel.CountryList
import com.example.whatsmyrisk.MyModel.CountryModel
import com.example.whatsmyrisk.MyModel.Customers
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewVendorActivity : AppCompatActivity() {

    var token=""
    var tokentype=""
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

    var countryAllList : ArrayList<CountryModel> = ArrayList()

    lateinit  var customerlist:ArrayList<Customers>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_feed_back)
        supportActionBar?.hide()
        findViewById<TextView>(R.id.heading).setText("Vendor Details")
        findViewById<ImageView>(R.id.btn_back_bar).setOnClickListener(){
            onBackPressed()
        }


        etUserName = findViewById<TextView>(R.id.tv_name)
        etGstNumnber = findViewById<TextView>(R.id.tv_gst_number)
        etMobileNo = findViewById<TextView>(R.id.tv_mobile)
        etMobileNo2 = findViewById<TextView>(R.id.tv_mobile_second)
        etEmailId = findViewById<TextView>(R.id.tv_email)
        etWebsite = findViewById<TextView>(R.id.tv_website)
        etBuisnessName = findViewById<TextView>(R.id.tv_buisness_category)
        etBusinessType = findViewById<TextView>(R.id.tv_buisness_type_name)
        etCountry = findViewById<TextView>(R.id.tv_country)
        etState = findViewById<TextView>(R.id.tv_state)
        etCity = findViewById<TextView>(R.id.tv_city)
        etBuisnessCategory = findViewById<TextView>(R.id.tv_category_name)

        var bundle: Bundle? = intent.extras
        var index : Int? =  bundle?.getInt("INDEX")
        var user :String? = bundle?.getString("User")
        customerlist = bundle?.getSerializable("List") as ArrayList<Customers>

        etUserName!!.setText(customerlist[index!!].name)
        etBuisnessName!!.setText(customerlist[index!!].businessName)
        etGstNumnber!!.setText(customerlist[index!!].gstNumber)
        etMobileNo!!.setText(customerlist[index!!].mobileNumber)
        etMobileNo2!!.setText(customerlist[index!!].mobileNumber2)
        etEmailId!!.setText(customerlist[index!!].email)
        etWebsite!!.setText(customerlist[index!!].website)
//        etBuisnessCategory!!.setText(customerlist[index!!].businessCategoryId.toString())
//        etBusinessType!!.setText(customerlist[index!!].businessTypeId.toString())
//        etCountry!!.setText(customerlist[index!!].countryId.toString())
//        etState!!.setText(customerlist[index!!].stateId.toString())
//        etCity!!.setText(customerlist[index!!].cityId.toString())


        getAllCountryList(this,customerlist[index])
        getAllCategoryList(customerlist[index])
        getAllBuisnessTypeList(customerlist[index])
        getAllStateList(customerlist[index!!].countryId!!.toInt(),customerlist[index],true)
        getAllCityList(customerlist[index!!].stateId!!.toInt(),customerlist[index],true)





    }

    fun getAllCountryList(context: Context, customers: Customers) :ArrayList<CountryModel>{
        Log.e("getAllCountryList", "Methode Call : ")


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
                country_name_list.add("Select Country")
                country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())
                var selectedValue =   country_name_list[country_id_list.indexOf(customers.countryId.toString()).toInt()]
                //findViewById<TextView>(R.id.search_spinner_country)!!.setText(selectedValue)
                etCountry!!.setText(selectedValue)
















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
                state_name_list.add("Select State")
                state_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    state_name_list.add(response.body()!!.data!![i].name!!)
                    state_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", state_name_list.toString())

                if(isSelected)
                {
                    var selectedValue =   state_name_list[state_id_list.indexOf(customers.stateId.toString()).toInt()]
                    etState!!.setText(selectedValue)
                }

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
                city_name_list.add("Select City")
                city_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    city_name_list.add(response.body()!!.data!![i].name!!)
                    city_id_list.add(response.body()!!.data!![i].id!!.toString())
                }

                if(isSelected)
                {
                    var selectedValue =   city_name_list[city_id_list.indexOf(customers.cityId.toString()).toInt()]
                    etCity!!.setText(selectedValue)
                }

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
                country_name_list.add("Business Category")
                country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())
                var selectedValue =   country_name_list[country_id_list.indexOf(customers.businessCategoryId.toString()).toInt()]
                etBuisnessCategory!!.setText(selectedValue)

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
                country_name_list.add("Business Type")
                country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())

                var selectedValue =   country_name_list[country_id_list.indexOf(customers.businessTypeId.toString()).toInt()]
                etBusinessType!!.setText(selectedValue)












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
}