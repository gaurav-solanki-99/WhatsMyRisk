package com.example.whatsmyrisk.PageFragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.whatsmyrisk.MyModel.Buisness
import com.example.whatsmyrisk.MyModel.BuisnessList
import com.example.whatsmyrisk.MyModel.CountryList
import com.example.whatsmyrisk.MyModel.CountryModel
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.R
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BottomSearchFragment :  Fragment() {

   lateinit var  spinner_buisness_category :Spinner
    lateinit var spinner_countyr:Spinner
    lateinit var spinner_state :Spinner
    lateinit var spinner_city :Spinner
    var token=""
    var tokentype=""
    var select_country_id:String = ""
    var select_state_id:String = ""
    var select_city_id:String = ""
    var select_category_id:String = ""
    var countryAllList : ArrayList<CountryModel> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(
            R.layout.bottom_sheet_search,
            container, false

        )


        openDialog(v.context)





//        val algo_button: Button = v.findViewById(R.id.algo_button)
//        val course_button: Button = v.findViewById(R.id.course_button)
//        algo_button.setOnClickListener( {
//            fun onClick(v: View?) {
//                Toast.makeText(
//                    activity,
//                    "Algorithm Shared", Toast.LENGTH_SHORT
//                )
//                    .show()
//                dismiss()
//            }
//        })
//        course_button.setOnClickListener( {
//            fun onClick(v: View?) {
//                Toast.makeText(
//                    activity,
//                    "Course Shared", Toast.LENGTH_SHORT
//                )
//                    .show()
//                dismiss()
//            }
//        })
        return v
    }


    fun openDialog(context : Context){
        val view1: View = layoutInflater.inflate(R.layout.bottom_sheet_search2, null)
        val dialog = activity?.let { BottomSheetDialog(it, R.style.BottomSheetDialog) }
        dialog!!.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.setContentView(view1)

         spinner_buisness_category = view1.findViewById<Spinner>(R.id.spinner_buisness_category)

         spinner_countyr = view1.findViewById<Spinner>(R.id.spinner_countyr)
         spinner_state = view1.findViewById<Spinner>(R.id.spinner_state)
         spinner_city = view1.findViewById<Spinner>(R.id.spinner_city)
        getAllCountryList(context)
        getAllCategoryList(context)

//        if (spinner_buisness_category != null) {
//            val adapter2 = ArrayAdapter(context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.SearchByBuisnessCategory))
//            adapter2.setDropDownViewResource(R.layout.single_spinner_items)
//            spinner_buisness_category.adapter = adapter2
//
//        }

//        if (spinner_countyr != null) {
//            val adapter2 = ArrayAdapter(context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Country2))
//            adapter2.setDropDownViewResource(R.layout.single_spinner_items)
//            spinner_countyr.adapter = adapter2
//
//        }

//        if (spinner_state != null) {
//            val adapter2 = ArrayAdapter(context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.State))
//            adapter2.setDropDownViewResource(R.layout.single_spinner_items)
//            spinner_state.adapter = adapter2
//
//        }

//
//        if (spinner_city != null) {
//            val adapter2 = ArrayAdapter(context, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.City))
//            adapter2.setDropDownViewResource(R.layout.single_spinner_items)
//            spinner_city.adapter = adapter2
//
//        }








        view1.findViewById<TextView>(R.id.bnt_search_dialog).setOnClickListener(){
            searchBuisnessMethodeCall(context)


//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.container, SearchFragment())
//            transaction?.addToBackStack(null)
//
//            transaction?.commit()
            dialog.dismiss()
        }
        dialog.show()











    }


    fun getAllCountryList(context: Context) :ArrayList<CountryModel>{
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token= PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

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


                val adapter3 = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
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
                            select_country_id = country_id_list.get(position)
                            getAllStateList(context,select_country_id.toInt())
                        }

                        override fun onNothingSelected(parentView: AdapterView<*>?) {}
                    }









                Toast.makeText(
                    context,
                    "All Country List" + countryAllList.size.toString(),
                    Toast.LENGTH_LONG
                ).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                Toast.makeText(
                    context,
                    "CountryList error please try again...",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

        return countryAllList;
    }



    fun getAllStateList(context: Context,country_id: Int) {
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

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
                state_name_list.add("State")
                state_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    state_name_list.add(response.body()!!.data!![i].name!!)
                    state_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", state_name_list.toString())


                val adapter3 = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
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
                        select_state_id = state_id_list.get(position)
                       getAllCityList(context,select_state_id.toInt())
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
                }









                Toast.makeText(
                    context,
                    "All Country List" + countryAllList.size.toString(),
                    Toast.LENGTH_LONG
                ).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                Toast.makeText(
                    context,
                    "CountryList error please try again...",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })


    }




    fun getAllCityList(context: Context,state_id: Int) {
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

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
                city_name_list.add("City")
                city_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    city_name_list.add(response.body()!!.data!![i].name!!)
                    city_id_list.add(response.body()!!.data!![i].id!!.toString())
                }


                val adapter4 = ArrayAdapter(
                    context,
                    android.R.layout.simple_spinner_item,
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
                        select_city_id = city_id_list.get(position)

                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
                }









                Toast.makeText(
                    context,
                    "All getAllCityList List" + countryAllList.size.toString(),
                    Toast.LENGTH_LONG
                ).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                Toast.makeText(
                    context,
                    "CountryList error please try again...",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })


    }


    fun getAllCategoryList(context: Context){
        Log.e("getAllCategoryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

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
                country_name_list.add("Buisness Category")
                country_id_list.add("0")
                for (i in 0 until response.body()!!.data.size) {
                    country_name_list.add(response.body()!!.data[i].name!!)
                    country_id_list.add(response.body()!!.data[i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())


                val adapter1 = ArrayAdapter(context,
                    android.R.layout.simple_spinner_item,
                    country_name_list
                )
                adapter1.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_buisness_category.adapter = adapter1



                spinner_buisness_category.onItemSelectedListener =
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
                            select_category_id = country_id_list.get(position)

                        }

                        override fun onNothingSelected(parentView: AdapterView<*>?) {}
                    }









                Toast.makeText(
                    context,
                    "All Country List" + countryAllList.size.toString(),
                    Toast.LENGTH_LONG
                ).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                Toast.makeText(
                    context,
                    "CountryList error please try again...",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })


    }


   fun  searchBuisnessMethodeCall(context: Context)
    {
        tokentype= PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()
       GetRetrofitInstance.instance.BuisnessSearchApi(tokentype + " " + token,1,"","","","".toInt(),"".toInt(),"".toInt()).enqueue(object : Callback<BuisnessList>{


           override fun onResponse(call: Call<BuisnessList>, response: Response<BuisnessList>) {
               Log.e("Buisness Search", "Response"+response.body().toString())
               if(response.body()!!.status==true)
               {
                 var buisnessList:ArrayList<Buisness> = response.body()!!.data
               }



           }

           override fun onFailure(call: Call<BuisnessList>, t: Throwable) {
               Log.e("Buisness Search", "Failure"+t.message)
           }

       })

   }

}