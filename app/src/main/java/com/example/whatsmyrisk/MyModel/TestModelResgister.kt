//package com.example.whatsmyrisk
//
//import android.app.Activity
//import android.content.DialogInterface
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.*
//import android.widget.AdapterView.OnItemSelectedListener
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import com.example.whatsmyrisk.MyConstants.Constants
//import com.example.whatsmyrisk.MyConstants.ImageCompression
//import com.example.whatsmyrisk.MyConstants.RealPathUtil
//import com.example.whatsmyrisk.MyModel.CountryList
//import com.example.whatsmyrisk.MyModel.CountryModel
//import com.example.whatsmyrisk.MyModel.RegistrationModel
//import com.example.whatsmyrisk.MyModel.UserDetaisl
//import com.example.whatsmyrisk.PREF.PrefManager
//import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.io.File
//
//
//class RegistrationActivity : AppCompatActivity()
//{
//    var otp=""
//    var mobile=""
//    var mobile_alternate=""
//    var token=""
//    var tokentype=""
//    var countryAllList : ArrayList<CountryModel> = ArrayList()
//    var countryAllListString : ArrayList<String> = ArrayList()
//
//
//    lateinit var autoCompleteTextView: AutoCompleteTextView
//
//    lateinit var spinner_buisness_category:Spinner
//    lateinit var spinner_buisness_type:Spinner
//    lateinit var spinner_countyr:Spinner
//    lateinit var spinner_state:Spinner
//    lateinit var spinner_city:Spinner
//    lateinit var btn_welcome_process :TextView
//
//    var select_country_id:String = ""
//    var select_state_id:String = ""
//    var select_city_id:String = ""
//    var select_category_id:String = ""
//    var select_buisnessType_id:String = ""
//
//
//
//    var etUserName: EditText? = null
//    var etBuisnessName: EditText? = null
//    var etGstNumnber: EditText? = null
//    var etMobileNo: EditText? = null
//    var etMobileNo2: EditText? = null
//    var etMobileNo3: EditText? = null
//    var etEmailId: EditText? = null
//    var etWebsite: EditText? = null
//    lateinit var pickphoto: TextView
//
//
//    lateinit  var userName: String
//    lateinit var buisnessName: String
//    lateinit var gstNumnber: String
//    lateinit  var  mobileNo: String
//    lateinit  var mobileNo2: String
//    lateinit   var mobileNo3: String
//    lateinit   var emailId: String
//    lateinit   var website: String
//
//    private val OPERATION_CHOOSE_PHOTO = 2
//    private var currentPhotoPath: String? = ""
//    private var photoURI: Uri? = null
//
//    lateinit var imageCompression: ImageCompression
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_registration)
//        supportActionBar?.hide()
//        imageCompression = ImageCompression(this)
//        val languages =  resources.getStringArray(R.array.Languages)
//        mobile=intent.getStringExtra("mobile").toString()
//        spinner_buisness_category = findViewById<Spinner>(R.id.spinner_buisness_category)
//        spinner_buisness_type = findViewById<Spinner>(R.id.spinner_buisness_type)
//        spinner_countyr = findViewById<Spinner>(R.id.spinner_countyr)
//        spinner_state = findViewById<Spinner>(R.id.spinner_state)
//        spinner_city = findViewById<Spinner>(R.id.spinner_city)
//        btn_welcome_process = findViewById<TextView>(R.id.btn_welcome_process2)
//
//
//        autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoTextView)
//
//
//
//
//
//        etUserName =findViewById<EditText>(R.id.et_regs_username)
//        etBuisnessName =findViewById<EditText>(R.id.et_regs_buisnessName)
//        etGstNumnber =findViewById<EditText>(R.id.et_regs_gstNumber)
//        etMobileNo =findViewById<EditText>(R.id.et_regs_mobileNo)
//        etMobileNo2 =findViewById<EditText>(R.id.et_regs_mobileNo2)
//        etMobileNo3 =findViewById<EditText>(R.id.et_regs_mobileNo3)
//        etEmailId =findViewById<EditText>(R.id.et_regs_emaild)
//        etWebsite =findViewById<EditText>(R.id.et_regs_website)
//        pickphoto =findViewById<TextView>(R.id.pickgallry)
//
//        findViewById<EditText>(R.id.et_regs_mobileNo).setText(mobile)
//        findViewById<EditText>(R.id.et_regs_mobileNo).isEnabled=false
//
//
//
//
//
//
//
//        //To get All country List
//        getAllCountryList()
//
//
//        //To get All Category List
//        getAllCategoryList()
//
//        //To get All Category List
//        getAllBuisnessTypeList()
//        pickphoto.setOnClickListener(View.OnClickListener {
//            Constants.checkPermission(this) { isGranted ->
//                if (isGranted) {
//                    val intent = Intent("android.intent.action.GET_CONTENT")
//                    intent.type = "image/*"
//                    startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
//                } else {
//                    showSettingsDialog()
//                }
//            }
//        })
//
//
//
//
//
//
//
//
//        btn_welcome_process.setOnClickListener {
//
//            if (validate())
//            {
//
//                var multipartprofile: MultipartBody.Part? = null
//                // nca_certificate
//                if (currentPhotoPath?.isNotEmpty()!!) {
//                    val file = File(currentPhotoPath)
//                    val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//                    multipartprofile =
//                            MultipartBody.Part.createFormData("user_certificate", file.name, reqFile)
//                }
//
//
//                // userName = etUserName?.text.toString()
//                //        buisnessName = etBuisnessName?.text.toString()
//                //        gstNumnber = etGstNumnber?.text.toString()
//                //        mobile = etMobileNo?.text.toString()
//                //        emailId = etEmailId?.text.toString()
//                //        website = etWebsite?.text.toString()
//                // create a map of data to pass along
//                val name: RequestBody = Constants.createPartFromString(userName)
//                val business_name: RequestBody = Constants.createPartFromString(buisnessName)
//                val business_category: RequestBody = Constants.createPartFromString("1")
//                val business_type: RequestBody = Constants.createPartFromString("1")
//                val gst_number: RequestBody = Constants.createPartFromString(gstNumnber)
//                val country: RequestBody = Constants.createPartFromString("1")
//                val state: RequestBody = Constants.createPartFromString("1")
//                val city: RequestBody = Constants.createPartFromString("1")
//                val mobile_number: RequestBody = Constants.createPartFromString(mobile)
//                val mobile_alternate_2: RequestBody = Constants.createPartFromString(mobile_alternate)
//                val email: RequestBody = Constants.createPartFromString(emailId)
//                val website: RequestBody = Constants.createPartFromString(website)
//
//                registrationUser(name, business_name, business_category, business_type, gst_number, country, state, city, mobile_number, email, website, mobile_alternate_2, multipartprofile)
//
//
//
//
//            }
//        }
//
//
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//            OPERATION_CHOOSE_PHOTO ->
//                if (resultCode == Activity.RESULT_OK) {
//                    photoURI = data?.data
//                    if (photoURI != null) {
//                        photoURI?.let { uri ->
//                            currentPhotoPath = RealPathUtil.getRealPathFromURIAPI19(this, uri)!!
//                            setData()
//                        }
//                    }
//                }
//        }
//    }
//    fun validate():Boolean{
//        userName = etUserName?.text.toString()
//        buisnessName = etBuisnessName?.text.toString()
//        gstNumnber = etGstNumnber?.text.toString()
//        mobile = etMobileNo?.text.toString()
//        mobile_alternate = etMobileNo2?.text.toString()
//        emailId = etEmailId?.text.toString()
//        website = etWebsite?.text.toString()
//        if (userName.isNullOrEmpty()){
//            etUserName?.requestFocus()
//            etUserName?.error="Please Enter Name "
//            return false
//        }
//        if (buisnessName.isNullOrEmpty()){
//            etBuisnessName?.requestFocus()
//            etBuisnessName?.error="Please Enter Buisness Name"
//            return false
//        }
//
//        if(select_category_id=="0")
//        { //ll_spinner_buisness_category
//            findViewById<View>(R.id.ll_spinner_buisness_category).requestFocus()
//            Toast.makeText(this, "Please Select Buisness Category ", Toast.LENGTH_LONG).show()
//
//            return  false
//        }
//
//        if(select_buisnessType_id=="0")
//        { //ll_spinner_buisness_category
//            findViewById<View>(R.id.ll_spinner_buisness_type).requestFocus()
//            Toast.makeText(this, "Please Select Buisness Type ", Toast.LENGTH_LONG).show()
//
//            return  false
//        }
//
//
//        if (gstNumnber.isNullOrEmpty()){
//            etGstNumnber?.requestFocus()
//            etGstNumnber?.error="Please Enter GST Number"
//            return false
//        }
//        if(select_country_id=="0")
//        { //ll_spinner_buisness_category
//            findViewById<View>(R.id.ll_spinner_countyr).requestFocus()
//            Toast.makeText(this, "Please Select Country  ", Toast.LENGTH_LONG).show()
//
//            return  false
//        }
//        if(select_state_id=="0")
//        { //ll_spinner_buisness_category
//            findViewById<View>(R.id.ll_spinner_state).requestFocus()
//            Toast.makeText(this, "Please Select State", Toast.LENGTH_LONG).show()
//
//            return  false
//        }
//        if(select_city_id=="0")
//        { //ll_spinner_buisness_category
//            findViewById<View>(R.id.ll_spinner_city).requestFocus()
//            Toast.makeText(this, "Please Select City", Toast.LENGTH_LONG).show()
//
//            return  false
//        }
//
//        if(mobile_alternate!!.length!==10)
//        {
//            etMobileNo2?.requestFocus()
//            etMobileNo2?.error="Mobile number should be 10 digits"
//            return false
//        }
//
//
//
//
//        if (emailId.isNullOrEmpty()){
//            //EMAIL_ADDRESS_PATTERN.matcher(email).matches();
//            etEmailId?.requestFocus()
//            etEmailId?.error="Please Enter Email"
//            return false
//        }
//        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches())
//        {
//            etEmailId?.requestFocus()
//            etEmailId?.error="Please Enter Valid  Email"
//            return false
//        }
//
//        if (website.isNullOrEmpty()){
//            etWebsite?.requestFocus()
//            etWebsite?.error="Please Enter Website"
//            return false
//        }
//
//        return true
//    }
//
//
//
//    fun getAllCountryList() :ArrayList<CountryModel>{
//        Log.e("getAllCountryList", "Methode Call : ")
//
//        // tokentype = "Bearer"
//        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"
//
//        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
//        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
//
//        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)
//
//
//
//
//
//        GetRetrofitInstance.instance.countryApi(tokentype + " " + token).enqueue(object :
//                Callback<CountryList> {
//            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {
//
//                val countryList: CountryList? = response.body()
//                Log.e("getAllCountryList", "response : " + response.body())
//
//                countryAllList = countryList!!.data
//                Log.e("getAllCountryList", "countryAllList : " + countryAllList.size.toString())
//
//
//                Log.e("All Country List ", countryAllList.size.toString())
//
//
//                var country_name_list: java.util.ArrayList<String> = ArrayList()
//                var country_id_list: java.util.ArrayList<String> = ArrayList()
//                country_name_list.add("Select Country")
//                country_id_list.add("0")
//                for (i in 0 until response.body()!!.data!!.size) {
//                    country_name_list.add(response.body()!!.data!![i].name!!)
//                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
//                }
//                Log.e("All Country List ", country_name_list.toString())
//
//
//                val adapter3 = ArrayAdapter(
//                        this@RegistrationActivity,
//                        android.R.layout.simple_spinner_item,
//                        country_name_list
//                )
//                adapter3.setDropDownViewResource(R.layout.single_spinner_items)
//                spinner_countyr.adapter = adapter3
//
//
//
//                spinner_countyr.onItemSelectedListener =
//                        object : OnItemSelectedListener {
//                            override fun onItemSelected(
//                                    parentView: AdapterView<*>?,
//                                    selectedItemView: View,
//                                    position: Int,
//                                    id: Long
//                            ) {
//                                (parentView!!.getChildAt(0) as TextView).setTextColor(
//                                        resources.getColor(
//                                                R.color.black
//                                        )
//                                )
//                                select_country_id = country_id_list.get(position)
//                                getAllStateList(select_country_id.toInt())
//                            }
//
//                            override fun onNothingSelected(parentView: AdapterView<*>?) {}
//                        }
//
//
//                // Toast.makeText(this@RegistrationActivity, "All Country List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()
//
//
//            }
//
//            override fun onFailure(call: Call<CountryList>, t: Throwable) {
//                // Toast.makeText(this@RegistrationActivity, "CountryList error please try again...", Toast.LENGTH_SHORT).show()
//            }
//
//        })
//
//        return countryAllList;
//    }
//
//
//
//    fun getAllStateList(country_id: Int) {
//        Log.e("getAllCountryList", "Methode Call : ")
//
//        // tokentype = "Bearer"
//        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"
//
//        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
//        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
//
//        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)
//
//
//
//
//        GetRetrofitInstance.instance.stateApi(tokentype + " " + token, country_id).enqueue(object :
//                Callback<CountryList> {
//            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {
//
//                val countryList: CountryList? = response.body()
//                Log.e("getAllStateList", "response : " + response.body())
//
//                countryAllList = countryList!!.data
//                Log.e("getAllStateList", "stateyAllList : " + countryAllList.size.toString())
//
//
//                Log.e("All Country List ", countryAllList.size.toString())
//
//
//                var state_name_list: java.util.ArrayList<String> = ArrayList()
//                var state_id_list: java.util.ArrayList<String> = ArrayList()
//                state_name_list.add("Select State")
//                state_id_list.add("0")
//                for (i in 0 until response.body()!!.data!!.size) {
//                    state_name_list.add(response.body()!!.data!![i].name!!)
//                    state_id_list.add(response.body()!!.data!![i].id!!.toString())
//                }
//                Log.e("All Country List ", state_name_list.toString())
//
//
//                val adapter3 = ArrayAdapter(
//                        this@RegistrationActivity,
//                        android.R.layout.simple_spinner_item,
//                        state_name_list
//                )
//                adapter3.setDropDownViewResource(R.layout.single_spinner_items)
//                spinner_state.adapter = adapter3
//
//
//
//
//
//
//
//
//                spinner_state.onItemSelectedListener = object : OnItemSelectedListener {
//                    override fun onItemSelected(
//                            parentView: AdapterView<*>?,
//                            selectedItemView: View,
//                            position: Int,
//                            id: Long
//                    ) {
//                        (parentView!!.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black))
//                        select_state_id = state_id_list.get(position)
//                        getAllCityList(select_state_id.toInt())
//                    }
//
//                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
//                }
//
//
////
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "All Country List" + countryAllList.size.toString(),
////                    Toast.LENGTH_LONG
////                ).show()
//
//
//            }
//
//            override fun onFailure(call: Call<CountryList>, t: Throwable) {
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "CountryList error please try again...",
////                    Toast.LENGTH_SHORT
////                ).show()
//            }
//
//        })
//
//
//    }
//
//
//
//    fun getAllCityList(state_id: Int) {
//        Log.e("getAllCountryList", "Methode Call : ")
//
//        // tokentype = "Bearer"
//        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"
//
//        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
//        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
//
//        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)
//
//
//
//
//        GetRetrofitInstance.instance.cityApi(tokentype + " " + token, state_id).enqueue(object :
//                Callback<CountryList> {
//            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {
//
//                val countryList: CountryList? = response.body()
//                Log.e("getAllCityList", "response : " + response.body())
//
//                countryAllList = countryList!!.data
//                Log.e("getAllCityList", "getAllCityList : " + countryAllList.size.toString())
//
//
//                var city_name_list: java.util.ArrayList<String> = ArrayList()
//                var city_id_list: java.util.ArrayList<String> = ArrayList()
//                city_name_list.add("Select City")
//                city_id_list.add("0")
//                for (i in 0 until response.body()!!.data!!.size) {
//                    city_name_list.add(response.body()!!.data!![i].name!!)
//                    city_id_list.add(response.body()!!.data!![i].id!!.toString())
//                }
//
//
//                val adapter4 = ArrayAdapter(
//                        this@RegistrationActivity,
//                        android.R.layout.simple_spinner_item,
//                        city_name_list
//                )
//                adapter4.setDropDownViewResource(R.layout.single_spinner_items)
//                spinner_city.adapter = adapter4
//
//
//
//
//
//
//
//
//                spinner_city.onItemSelectedListener = object : OnItemSelectedListener {
//                    override fun onItemSelected(
//                            parentView: AdapterView<*>?,
//                            selectedItemView: View,
//                            position: Int,
//                            id: Long
//                    ) {
//                        (parentView!!.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black))
//                        select_city_id = city_id_list.get(position)
//
//                    }
//
//                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
//                }
//
//
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "All getAllCityList List" + countryAllList.size.toString(),
////                    Toast.LENGTH_LONG
////                ).show()
//
//
//            }
//
//            override fun onFailure(call: Call<CountryList>, t: Throwable) {
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "CountryList error please try again...",
////                    Toast.LENGTH_SHORT
////                ).show()
//            }
//
//        })
//
//
//    }
//
//
//
//
//
//    fun getAllCategoryList(){
//        Log.e("getAllCategoryList", "Methode Call : ")
//
//        // tokentype = "Bearer"
//        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"
//
//        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
//        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
//
//        Log.e("getAllCategoryList", "Methode Call : " + tokentype + " " + token)
//
//
//
//
//
//        GetRetrofitInstance.instance.categoryApi(tokentype + " " + token).enqueue(object :
//                Callback<CountryList> {
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
//                country_name_list.add("Buisness Category")
//                country_id_list.add("0")
//                for (i in 0 until response.body()!!.data.size) {
//                    country_name_list.add(response.body()!!.data[i].name!!)
//                    country_id_list.add(response.body()!!.data[i].id!!.toString())
//                }
//                Log.e("All Country List ", country_name_list.toString())
//
//
//                val adapter1 = ArrayAdapter(
//                        this@RegistrationActivity,
//                        android.R.layout.simple_spinner_item,
//                        country_name_list
//                )
//                adapter1.setDropDownViewResource(R.layout.single_spinner_items)
//                spinner_buisness_category.adapter = adapter1
//
//
//                //~~~~~~~~~~~~~~~~~~~~~Auto~~~~~~~~~~~~~~`
//
//                val adapter: ArrayAdapter<String> = ArrayAdapter(
//                        this@RegistrationActivity,
//                        android.R.layout.simple_spinner_item,
//                        country_name_list
//                )
//
//                autoCompleteTextView.setAdapter(adapter)
//                autoCompleteTextView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
//                    val item: String = adapterView.getItemAtPosition(i).toString()
//
//                    val selectedIndex = country_name_list.indexOf(item)
//
//
//                    Log.e("Select Auto Value ", "" + item +""+selectedIndex)
//
//                }
//
//
////                var select_state_list=""
////               val customerAdapter = DropDownAdapter(this@RegistrationActivity, R.layout.simple_spinner2_item, country_name_list)
////                autoCompleteTextView.setAdapter(customerAdapter)
////                autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id1 ->
////                                select_state_list = country_name_list[position]!!
////                                println("onItemClickListener="+ select_state_list)
////                                if(select_state_list != "0") {
////
////
//////                                    if (getConnectionStatus(this@RegistrationActivity)){
//////                                        select_city_list = "0"
//////                                        edittext_city.setText("")
//////                                        getcity(select_state_list)
//////                                    } else{
//////                                        ProjectUtils().response_popup(this@RegistrationActivity, "no internet")
//////                                    }
////                                }
////                            }
//
//
//                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`
//
//
//                spinner_buisness_category.onItemSelectedListener =
//                        object : OnItemSelectedListener {
//                            override fun onItemSelected(
//                                    parentView: AdapterView<*>?,
//                                    selectedItemView: View,
//                                    position: Int,
//                                    id: Long
//                            ) {
//                                (parentView!!.getChildAt(0) as TextView).setTextColor(
//                                        resources.getColor(
//                                                R.color.black
//                                        )
//                                )
//                                select_category_id = country_id_list.get(position)
//
//                            }
//
//                            override fun onNothingSelected(parentView: AdapterView<*>?) {}
//                        }
//
//
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "All Country List" + countryAllList.size.toString(),
////                    Toast.LENGTH_LONG
////                ).show()
//
//
//            }
//
//            override fun onFailure(call: Call<CountryList>, t: Throwable) {
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "CountryList error please try again...",
////                    Toast.LENGTH_SHORT
////                ).show()
//            }
//
//        })
//
//
//    }
//
//    fun getAllBuisnessTypeList(){
//        Log.e("getAllCategoryList", "Methode Call : ")
//
//        // tokentype = "Bearer"
//        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"
//
//        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
//        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
//
//        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)
//
//
//
//
//
//        GetRetrofitInstance.instance.buisnessTypeApi(tokentype + " " + token).enqueue(object :
//                Callback<CountryList> {
//            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {
//
//                val countryList: CountryList? = response.body()
//                Log.e("getAllBuisnessTypeList", "response : " + response.body())
//
//                // countryAllList = countryList!!.data
//                Log.e(
//                        "getAllBuisnessTypeList",
//                        "countryAllList : " + countryAllList.size.toString()
//                )
//
//
//                var country_name_list: java.util.ArrayList<String> = ArrayList()
//                var country_id_list: java.util.ArrayList<String> = ArrayList()
//                country_name_list.add("Buisness Type")
//                country_id_list.add("0")
//                for (i in 0 until response.body()!!.data!!.size) {
//                    country_name_list.add(response.body()!!.data!![i].name!!)
//                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
//                }
//                Log.e("All Country List ", country_name_list.toString())
//
//
//                val adapter2 = ArrayAdapter(
//                        this@RegistrationActivity,
//                        android.R.layout.simple_spinner_item,
//                        country_name_list
//                )
//                adapter2.setDropDownViewResource(R.layout.single_spinner_items)
//                spinner_buisness_type.adapter = adapter2
//
//
//
//                spinner_buisness_type.onItemSelectedListener =
//                        object : OnItemSelectedListener {
//                            override fun onItemSelected(
//                                    parentView: AdapterView<*>?,
//                                    selectedItemView: View,
//                                    position: Int,
//                                    id: Long
//                            ) {
//                                (parentView!!.getChildAt(0) as TextView).setTextColor(
//                                        resources.getColor(
//                                                R.color.black
//                                        )
//                                )
//                                select_buisnessType_id = country_id_list.get(position)
//
//                            }
//
//                            override fun onNothingSelected(parentView: AdapterView<*>?) {}
//                        }
//
//
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "All Country List" + countryAllList.size.toString(),
////                    Toast.LENGTH_LONG
////                ).show()
//
//
//            }
//
//            override fun onFailure(call: Call<CountryList>, t: Throwable) {
////                Toast.makeText(
////                    this@RegistrationActivity,
////                    "CountryList error please try again...",
////                    Toast.LENGTH_SHORT
////                ).show()
//            }
//
//        })
//
//
//    }
//
//
//    fun registrationUser(
//            name: RequestBody,
//            business_name: RequestBody,
//            business_category: RequestBody,
//            business_type: RequestBody,
//            gst_number: RequestBody,
//            country: RequestBody,
//            state: RequestBody,
//            city: RequestBody,
//            mobile_number: RequestBody,
//            email: RequestBody,
//            website: RequestBody,
//            mobile_number2: RequestBody,
//            finalfile: MultipartBody.Part?
//    ){
//
//        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
//        token =PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
//        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)
//        GetRetrofitInstance.instance.registrationAccountApi(
//                tokentype + " " + token,
//                name,
//                business_name,
//                business_category,
//                business_type,
//                gst_number,
//                country,
//                state,
//                city,
//                mobile_number,
//                email,
//                website,
//                mobile_number2,
//                finalfile
//        ).enqueue(object : Callback<RegistrationModel> {
//            override fun onResponse(
//                    call: Call<RegistrationModel>,
//                    response: Response<RegistrationModel>
//            ) {
//                // Log.e("Registration  Response", response.body()!!.data.toString())
//                //Toast.makeText(this@RegistrationActivity,""+response.body()?.message,Toast.LENGTH_SHORT).show()
//                Log.e("Registration  Response", response.body()!!.data.toString())
//
//                if (response.body()!!.status == true) {
//                    var userDetails: UserDetaisl = response.body()!!.data!!
//
//                    PrefManager.setString(this@RegistrationActivity, PrefManager.first_name, userDetails.firstName)
//                    PrefManager.setString(this@RegistrationActivity, PrefManager.business_name, userDetails.businessName)
//                    PrefManager.setString(this@RegistrationActivity, PrefManager.gst_number, userDetails.gstNumber)
//                    PrefManager.setString(this@RegistrationActivity, PrefManager.mobile_number, userDetails.mobileNumber)
//                    PrefManager.setString(this@RegistrationActivity, PrefManager.email, userDetails.email)
//                    PrefManager.setString(this@RegistrationActivity, PrefManager.website, userDetails.website)
//
//
//                    val intent: Intent = Intent(this@RegistrationActivity, DashboardActivity::class.java)
//
//
//                    // intent.putExtra("userDetails",userDetails as Serializable)
//
//
//                    startActivity(intent)
//                    finish()
//                }
//
//            }
//
//            override fun onFailure(call: Call<RegistrationModel>, t: Throwable) {
//                Log.e("Registration  Response", "Failed")
//            }
//        })
//    }
//
//    private fun showSettingsDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Need Permissions" as CharSequence)
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings." as CharSequence)
//        builder.setPositiveButton(
//                "GOTO SETTINGS" as CharSequence,
//                DialogInterface.OnClickListener { dialog, `$noName_1` ->
//                    dialog.cancel()
//                    openSettings()
//                })
//        builder.setNegativeButton("Cancel" as CharSequence, null)
//        builder.show()
//    }
//
//    private fun openSettings() {
//        val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
//        val uri = Uri.fromParts("package", packageName, null as String?)
//        intent.data = uri
//        //startSetting.launch(intent)
//        startActivity(intent)
//    }
//    private fun setData() {
//        currentPhotoPath = imageCompression.compressImage(currentPhotoPath)
//    }
//
//
//
//
//}