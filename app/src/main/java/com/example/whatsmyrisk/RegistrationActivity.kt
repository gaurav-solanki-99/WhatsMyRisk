package com.example.whatsmyrisk

import android.R.attr
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import com.example.whatsmyrisk.MyConstants.Constants
import com.example.whatsmyrisk.MyConstants.ImageCompression
import com.example.whatsmyrisk.MyConstants.RealPathUtil
import com.example.whatsmyrisk.MyModel.CountryList
import com.example.whatsmyrisk.MyModel.CountryModel
import com.example.whatsmyrisk.MyModel.RegistrationModel
import com.example.whatsmyrisk.MyModel.UserDetaisl
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class RegistrationActivity : AppCompatActivity()
{
    var otp=""
    var mobile=""
    var mobile_alternate=""
    var token=""
    var tokentype=""
    var countryAllList : ArrayList<CountryModel> = ArrayList()
    var countryAllListString : ArrayList<String> = ArrayList()


    //~~~~~~~~~~~Testing
    lateinit var autosuggest_buisness_category:AutoCompleteTextView
    lateinit var spinner_buisness_category2:Spinner
    lateinit var categoryImage:ImageView






    lateinit var spinner_buisness_category:AutoCompleteTextView
    lateinit var spinner_buisness_type:AutoCompleteTextView
    lateinit var spinner_countyr:AutoCompleteTextView
    lateinit var spinner_state:AutoCompleteTextView
    lateinit var spinner_city:AutoCompleteTextView
    lateinit var btn_welcome_process :TextView

    var select_country_id:String = "0"
    var select_state_id:String = "0"
    var select_city_id:String = "0"
    var select_category_id:String = "0"
    var select_buisnessType_id:String = "0"



    var etUserName: EditText? = null
    var etBuisnessName: EditText? = null
    var etGstNumnber: EditText? = null
    var etMobileNo: EditText? = null
    var etMobileNo2: EditText? = null
    var etMobileNo3: EditText? = null
    var etEmailId: EditText? = null
    var etWebsite: EditText? = null
    lateinit var pickphoto: TextView
    var checkBox:CheckBox?=null


    lateinit  var userName: String
    lateinit var buisnessName: String
    lateinit var gstNumnber: String
    lateinit  var  mobileNo: String
    lateinit  var mobileNo2: String
    lateinit   var mobileNo3: String
    lateinit   var emailId: String
    lateinit   var website: String

    private val OPERATION_CHOOSE_PHOTO = 2
    private var currentPhotoPath: String? = ""
    private var photoURI: Uri? = null

    var termsConditions : Boolean=false

    lateinit var imageCompression: ImageCompression



    var isCategotyDropDown=false;



    //Searchabale Spinner
    var textview: TextView? = null
    var arrayList: ArrayList<String>? = null
    var dialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_registration)
        supportActionBar?.hide()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)


        imageCompression = ImageCompression(this)
        val languages =  resources.getStringArray(R.array.Languages)
        //mobile=intent.getStringExtra("mobile").toString()
        mobile=PrefManager.getString(this, PrefManager.mobile_number).toString()
         spinner_buisness_category = findViewById<AutoCompleteTextView>(R.id.spinner_buisness_category)
         spinner_buisness_type = findViewById<AutoCompleteTextView>(R.id.spinner_buisness_type)
         spinner_countyr = findViewById<AutoCompleteTextView>(R.id.spinner_countyr)
         spinner_state = findViewById<AutoCompleteTextView>(R.id.spinner_state)
         spinner_city = findViewById<AutoCompleteTextView>(R.id.spinner_city)
         btn_welcome_process = findViewById<TextView>(R.id.btn_welcome_process2)

        checkBox=findViewById<CheckBox>(R.id.checkBox)




        findViewById<TextView>(R.id.tv_term_condition).setOnClickListener(){

             startActivity(Intent(this, TermsConditionActivity::class.java))

        }



        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //Searchabale Spinner
        // assign variable
        // assign variable
        textview = findViewById(R.id.testView)

        // initialize array list

        // initialize array list
        arrayList = ArrayList()

        // set value in array list

        // set value in array list
        arrayList!!.add("DSA Self Paced")
        arrayList!!.add("Complete Interview Prep")
        arrayList!!.add("Amazon SDE Test Series")
        arrayList!!.add("Compiler Design")
        arrayList!!.add("Git & Github")
        arrayList!!.add("Python foundation")
        arrayList!!.add("Operating systems")
        arrayList!!.add("Theory of Computation")




        textview!!.setOnClickListener(View.OnClickListener {
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
            val listView: ListView = dialog!!.findViewById(R.id.list_view)

            // Initialize array adapter
            val adapter = ArrayAdapter(
                    this, android.R.layout.simple_list_item_1,
                    arrayList!!
            )

            // set adapter
            listView.adapter = adapter
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    adapter.filter.filter(s)
                }

                override fun afterTextChanged(s: Editable) {}
            })
            listView.onItemClickListener =
                    OnItemClickListener { parent, view, position, id -> // when item selected from list
                        // set selected item on textView
                        textview!!.setText(adapter.getItem(position))

                        // Dismiss dialog
                        dialog!!.dismiss()
                    }
        })

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //Searchabale Spinner


        // autosuggest_buisness_category = findViewById<AutoCompleteTextView>(R.id.autoComplete_category)
      //   spinner_buisness_category2 = findViewById<Spinner>(R.id.spinner_category)
      //   categoryImage = findViewById<ImageView>(R.id.imageview_category)




//        categoryImage.setOnClickListener(){
//            Toast.makeText(this,"Value "+isCategotyDropDown,Toast.LENGTH_SHORT).show()
//
//            if(isCategotyDropDown==false)
//            {
//                Toast.makeText(this,"false "+isCategotyDropDown,Toast.LENGTH_SHORT).show()
//
//                isCategotyDropDown=true
//                spinner_buisness_category2.visibility=View.VISIBLE
//                autosuggest_buisness_category.visibility=View.GONE
//
//            }
//            else if(isCategotyDropDown==true)
//            {
//                Toast.makeText(this,"true "+isCategotyDropDown,Toast.LENGTH_SHORT).show()
//
//                isCategotyDropDown=false
//                spinner_buisness_category2.visibility=View.GONE
//                autosuggest_buisness_category.visibility=View.VISIBLE
//
//            }
//        }














        etUserName =findViewById<EditText>(R.id.et_regs_username)
        etBuisnessName =findViewById<EditText>(R.id.et_regs_buisnessName)
        etGstNumnber =findViewById<EditText>(R.id.et_regs_gstNumber)
        etMobileNo =findViewById<EditText>(R.id.et_regs_mobileNo)
        etMobileNo2 =findViewById<EditText>(R.id.et_regs_mobileNo2)
        etMobileNo3 =findViewById<EditText>(R.id.et_regs_mobileNo3)
        etEmailId =findViewById<EditText>(R.id.et_regs_emaild)
        etWebsite =findViewById<EditText>(R.id.et_regs_website)
        pickphoto =findViewById<TextView>(R.id.pickgallry)

        findViewById<EditText>(R.id.et_regs_mobileNo).setText(mobile)
        findViewById<EditText>(R.id.et_regs_mobileNo).isEnabled=false


        //Code for Hide Drop Dwon & visible









        //To get All country List
       getAllCountryList()


      //To get All Category List
        getAllCategoryList()



        //To get All Category List
        getAllBuisnessTypeList()
        pickphoto.setOnClickListener(View.OnClickListener {
            Constants.checkPermission(this) { isGranted ->
                if (isGranted) {
                    selectImage()

                } else {
                    showSettingsDialog()
                }
            }
        })








        btn_welcome_process.setOnClickListener {

            if(Constants.getConnectionStatus(this)) {

                if (validate()) {

                    var multipartprofile: MultipartBody.Part? = null
                    // nca_certificate
                    if (currentPhotoPath?.isNotEmpty()!!) {
                        val file = File(currentPhotoPath)
                        val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        multipartprofile = MultipartBody.Part.createFormData("user_certificate", file.name, reqFile)
                    }


                    // userName = etUserName?.text.toString()
                    //        buisnessName = etBuisnessName?.text.toString()
                    //        gstNumnber = etGstNumnber?.text.toString()
                    //        mobile = etMobileNo?.text.toString()
                    //        emailId = etEmailId?.text.toString()
                    //        website = etWebsite?.text.toString()
                    // create a map of data to pass along
                    val name: RequestBody = Constants.createPartFromString(userName)
                    val business_name: RequestBody = Constants.createPartFromString(buisnessName)
                    val business_category: RequestBody = Constants.createPartFromString(select_category_id)
                    val business_type: RequestBody = Constants.createPartFromString(select_buisnessType_id)
                    val gst_number: RequestBody = Constants.createPartFromString(gstNumnber)
                    val country: RequestBody = Constants.createPartFromString(select_country_id)
                    val state: RequestBody = Constants.createPartFromString(select_state_id)
                    val city: RequestBody = Constants.createPartFromString(select_city_id)
                    val mobile_number: RequestBody = Constants.createPartFromString(mobile)
                    val mobile_alternate_2: RequestBody = Constants.createPartFromString(mobile_alternate)
                    val email: RequestBody = Constants.createPartFromString(emailId)
                    val website: RequestBody = Constants.createPartFromString(website)

                    registrationUser(
                            name,
                            business_name,
                            business_category,
                            business_type,
                            gst_number,
                            country,
                            state,
                            city,
                            mobile_number,
                            email,
                            website,
                            mobile_alternate_2,
                            multipartprofile
                    )


                }
            }else
            {
                Toast.makeText(this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show()

            }
        }

        findViewById<TextView>(R.id.btn_log_out_2).setOnClickListener(){
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Log Out")
            builder.setMessage("Are you sure you want to logout ?")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                PrefManager.setString(this, PrefManager.first_name, "")
                PrefManager.setString(this, PrefManager.business_name, "")
                PrefManager.setString(this, PrefManager.gst_number, "")
                PrefManager.setString(this, PrefManager.mobile_number, "")
                PrefManager.setString(this, PrefManager.email, "")
                PrefManager.setString(this, PrefManager.website, "")
                PrefManager.setString(this, PrefManager.MOBILE_NUMBER, "")
                PrefManager.setString(this, PrefManager.OTP, "")
                PrefManager.setString(this, PrefManager.ACCESS_TOKEN, "")
                PrefManager.setString(this, PrefManager.TOKEN_TYPE, "")

                var intent: Intent = Intent(this, PhoneNumberActivity::class.java)
                this.startActivity(intent)
                startActivity(intent)
                finish()

            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->

                return@OnClickListener
            })
            builder.show()

        }



    }

    //To Open Camera & File
     fun selectImage()
     {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Upload Certificate")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                capturePhoto()
//                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////                val f = File(Environment.getExternalStorageDirectory(), "temp.jpg")
////                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f))
//                startActivityForResult(intent, 1)
            } else if (options[item] == "Choose from Gallery") {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }
    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createImageFile()
        val uri = FileProvider.getUriForFile(this, packageName + ".provider", photoFile!!
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, 1)
    }

     fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        //getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        var var3: File? = null
        try {
            var3 = File.createTempFile("WhatsMyRisk" + timeStamp + '_', ".png", storageDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        photoURI = Uri.fromFile(File(var3!!.absolutePath))
        val temp = var3.absolutePath
        currentPhotoPath = temp
        return var3
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode){
//
//
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
//
//
//
//        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("RESULT", resultCode.toString())

                photoURI = data?.data

                if (photoURI != null) {
                    photoURI?.let { uri ->
                        currentPhotoPath = RealPathUtil.getRealPathFromURIAPI19(this, uri)!!
                        setData()
                        Log.e("PHOTOS SELECTED ", currentPhotoPath.toString())
                        Toast.makeText(this,"Certificate saved Successfully",Toast.LENGTH_LONG).show()

                    }
                }
            }
        }

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {



                Log.e("PHOTOS CAPTURE ", currentPhotoPath.toString())
                Log.e("PHOTOS CAPTURE ", photoURI.toString())
                if (photoURI != null) {
                    photoURI?.let { uri ->
                        currentPhotoPath = RealPathUtil.getRealPathFromURIAPI19(this, uri)!!
                        setData()
                        Log.e("PHOTOS CAPTURE ", currentPhotoPath.toString())
                        Toast.makeText(this,"Certificate saved Successfully",Toast.LENGTH_LONG).show()
                    }
                }



            } else {
                //mImageExt = ""
            }
        }


//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == 1) {
//                Log.e("RESULT CAMERA", requestCode.toString())
//                if (data?.data != null) {
//                    if (data.data != null) {
//                        photoURI = data.data
//                        Log.e("PHOTOS CAPTURE ", data.data.toString())
//                    } else {
//                        Toast.makeText(this, "Photo Not Selected ", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(this, "Photo Not Selected", Toast.LENGTH_SHORT).show()
//                }
//
//
//            }
//        }

    }
    fun validate():Boolean{
        userName = etUserName?.text.toString()
        buisnessName = etBuisnessName?.text.toString()
        gstNumnber = etGstNumnber?.text.toString()
        mobile = etMobileNo?.text.toString()
        mobile_alternate = etMobileNo2?.text.toString()
        emailId = etEmailId?.text.toString()
        website = etWebsite?.text.toString()


        if (userName.isNullOrEmpty()){
            etUserName?.requestFocus()
            etUserName?.error="Please Enter Name "
            return false
        }
        if (buisnessName.isNullOrEmpty()){
            etBuisnessName?.requestFocus()
            etBuisnessName?.error="Please Enter Business Name"
            return false
        }

        if(select_category_id=="0")
        { //ll_spinner_buisness_category
            findViewById<View>(R.id.ll_spinner_buisness_category).requestFocus()
            Toast.makeText(this, "Please Select Business Category ", Toast.LENGTH_LONG).show()

            return  false
        }

        if(select_buisnessType_id=="0")
        { //ll_spinner_buisness_category
            findViewById<View>(R.id.ll_spinner_buisness_type).requestFocus()
            Toast.makeText(this, "Please Select Business Type ", Toast.LENGTH_LONG).show()

            return  false
        }

//
//        if (gstNumnber.isNullOrEmpty()){
//            etGstNumnber?.requestFocus()
//            etGstNumnber?.error="Please Enter GST Number"
//            return false
//        }
        if(select_country_id=="0")
        { //ll_spinner_buisness_category
            findViewById<View>(R.id.ll_spinner_countyr).requestFocus()
            Toast.makeText(this, "Please Select Country  ", Toast.LENGTH_LONG).show()

            return  false
        }
        if(select_state_id=="0")
        { //ll_spinner_buisness_category
            findViewById<View>(R.id.ll_spinner_state).requestFocus()
            Toast.makeText(this, "Please Select State", Toast.LENGTH_LONG).show()

            return  false
        }
        if(select_city_id=="0")
        { //ll_spinner_buisness_category
            findViewById<View>(R.id.ll_spinner_city).requestFocus()
            Toast.makeText(this, "Please Select City", Toast.LENGTH_LONG).show()

            return  false
        }
        if(mobile_alternate.isNotEmpty()) {
                if(mobile_alternate.length!=10)
                {
                    etMobileNo2?.requestFocus()
                    etMobileNo2?.error="Mobile number should be 10 digits"
                    return false
                }
        }

        if(emailId.isNotEmpty()) {

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches())
                {
                    etEmailId?.requestFocus()
                    etEmailId?.error="Please Enter Valid  Email"
                    return false
                }
        }

        if(checkBox!!.isChecked()==false){
            Toast.makeText(this, "Please accept Terms & Conditioned ", Toast.LENGTH_LONG).show()

            return  false
        }



        return true
    }
    override fun onBackPressed() {

        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to Exit ?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->

            finishAffinity()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
            return@OnClickListener
        })
        builder.show()



    }


    fun getAllCountryList() :ArrayList<CountryModel>{
        Log.e("getAllCountryList", "Methode Call : ")

       // tokentype = "Bearer"
       // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

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
                //  country_name_list.add("Select Country")
                // country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())


                val adapter3 = ArrayAdapter(
                        this@RegistrationActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        country_name_list
                )
                adapter3.setDropDownViewResource(R.layout.single_spinner_items)
                // spinner_countyr.adapter = adapter3


                spinner_countyr.setAdapter(adapter3)
                //  spinner_countyr.threshold=0

                spinner_countyr.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                    val item: String = adapterView.getItemAtPosition(i).toString()

                    val selectedIndex = country_name_list.indexOf(item)


                    select_country_id = country_id_list[selectedIndex]

                    Log.e("Select Country Value ", "" + item + "" + select_country_id)
                    getAllStateList(select_country_id.toInt())


                }


                findViewById<View>(R.id.search_spinner_country)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@RegistrationActivity)

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
                    heading.setText("Select Country")


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
                            OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_spinner_country)!!.setText(adapter3.getItem(position))


                                val item: String = adapter3.getItem(position).toString()

                                val selectedIndex = country_name_list.indexOf(item)


                                select_country_id = country_id_list[selectedIndex]

                                Log.e("Select Country Value ", "" + item + "" + select_country_id)
                                getAllStateList(select_country_id.toInt())

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


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


                // Toast.makeText(this@RegistrationActivity, "All Country List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                // Toast.makeText(this@RegistrationActivity, "CountryList error please try again...", Toast.LENGTH_SHORT).show()
            }

        })

        return countryAllList;
    }



    fun getAllStateList(country_id: Int) {
        Log.e("getAllCountryList", "Methode Call : ")

       // tokentype = "Bearer"
       // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

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


                val adapter3 = ArrayAdapter(
                        this@RegistrationActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        state_name_list
                )
                adapter3.setDropDownViewResource(R.layout.single_spinner_items)
                //  spinner_state.adapter = adapter3


                spinner_state.setAdapter(adapter3)
                // spinner_state.threshold=0

                spinner_state.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                    val item: String = adapterView.getItemAtPosition(i).toString()

                    val selectedIndex = state_name_list.indexOf(item)

                    select_state_id = state_id_list[selectedIndex]
                    getAllCityList(select_state_id.toInt())




                    Log.e("Select Auto Value ", "" + item + "" + selectedIndex)

                }



                findViewById<View>(R.id.search_spinner_state)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@RegistrationActivity)

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
                    heading.setText("Select State")


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
                            OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_spinner_state)!!.setText(adapter3.getItem(position))


                                val item: String = adapter3.getItem(position).toString()

                                val selectedIndex = state_name_list.indexOf(item)

                                select_state_id = state_id_list[selectedIndex]
                                getAllCityList(select_state_id.toInt())

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


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
//                Toast.makeText(
//                    this@RegistrationActivity,
//                    "All Country List" + countryAllList.size.toString(),
//                    Toast.LENGTH_LONG
//                ).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
//                Toast.makeText(
//                    this@RegistrationActivity,
//                    "CountryList error please try again...",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

        })


    }

    fun getAllCityList(state_id: Int) {
        Log.e("getAllCountryList", "Methode Call : ")

       // tokentype = "Bearer"
       // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

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
                // city_name_list.add("Select City")
                //   city_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    city_name_list.add(response.body()!!.data!![i].name!!)
                    city_id_list.add(response.body()!!.data!![i].id!!.toString())
                }


                val adapter4 = ArrayAdapter(
                        this@RegistrationActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        city_name_list
                )
                adapter4.setDropDownViewResource(R.layout.single_spinner_items)
                // spinner_city.adapter = adapter4
                spinner_city.setAdapter(adapter4)
                //spinner_city.threshold=0

                spinner_city.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                    val item: String = adapterView.getItemAtPosition(i).toString()

                    val selectedIndex = city_name_list.indexOf(item)

                    select_city_id = city_id_list[selectedIndex]




                    Log.e("Select Auto Value ", "" + item + "" + selectedIndex)

                }

                findViewById<View>(R.id.search_spinner_city)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@RegistrationActivity)

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
                    heading.setText("Select City")


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
                            OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_spinner_city)!!.setText(adapter4.getItem(position))


                                val item: String = adapter4.getItem(position).toString()

                                val selectedIndex = city_name_list.indexOf(item)

                                select_city_id = city_id_list[selectedIndex]

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


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


//                Toast.makeText(
//                    this@RegistrationActivity,
//                    "All getAllCityList List" + countryAllList.size.toString(),
//                    Toast.LENGTH_LONG
//                ).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
//                Toast.makeText(
//                    this@RegistrationActivity,
//                    "CountryList error please try again...",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

        })


    }





    fun getAllCategoryList(){
        Log.e("getAllCategoryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

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
                //   country_name_list.add("Business Category")
                //  country_id_list.add("0")
                for (i in 0 until response.body()!!.data.size) {
                    country_name_list.add(response.body()!!.data[i].name!!)
                    country_id_list.add(response.body()!!.data[i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())


                val adapter1 = ArrayAdapter(
                        this@RegistrationActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        country_name_list
                )
                adapter1.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_buisness_category.setAdapter(adapter1)
                // spinner_buisness_category.threshold=0

                spinner_buisness_category.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                    val item: String = adapterView.getItemAtPosition(i).toString()

                    val selectedIndex = country_name_list.indexOf(item)

                    select_category_id = country_id_list[selectedIndex]



                    Log.e("Select Auto Value ", "" + item + "" + selectedIndex)


                }


                //Buisness Category Search


                findViewById<View>(R.id.search_buisness_category)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@RegistrationActivity)

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
                            OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.search_buisness_category)!!.setText(adapter1.getItem(position))


                                val item: String = adapter1.getItem(position).toString()


                                val selectedIndex = country_name_list.indexOf(item)

                                select_category_id = country_id_list[selectedIndex]

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
//                Toast.makeText(
//                    this@RegistrationActivity,
//                    "CountryList error please try again...",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

        })


    }

    fun getAllBuisnessTypeList(){
        Log.e("getAllCategoryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()

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
                for (i in 0 until response.body()!!.data.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())


                val adapter2 = ArrayAdapter(
                        this@RegistrationActivity,
                        android.R.layout.simple_dropdown_item_1line,
                        country_name_list
                )
                adapter2.setDropDownViewResource(R.layout.single_spinner_items)
//                spinner_buisness_type.adapter = adapter2

                spinner_buisness_type.setAdapter(adapter2)


                // spinner_buisness_type.threshold=0

                spinner_buisness_type.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
                    val item: String = adapterView.getItemAtPosition(i).toString()

                    val selectedIndex = country_name_list.indexOf(item)

                    select_buisnessType_id = country_id_list[selectedIndex]



                    Log.e("Select Auto Value ", "" + item + "" + selectedIndex)

                }

                findViewById<View>(R.id.Search_buisness_type)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(this@RegistrationActivity)

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
                            OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                findViewById<TextView>(R.id.Search_buisness_type)!!.setText(adapter2.getItem(position))


                                val item: String = adapter2.getItem(position).toString()

                                val selectedIndex = country_name_list.indexOf(item)

                                select_buisnessType_id = country_id_list[selectedIndex]

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


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


//                Toast.makeText(
//                    this@RegistrationActivity,
//                    "All Country List" + countryAllList.size.toString(),
//                    Toast.LENGTH_LONG
//                ).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
//                Toast.makeText(
//                    this@RegistrationActivity,
//                    "CountryList error please try again...",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

        })


    }


    fun registrationUser(
            name: RequestBody,
            business_name: RequestBody,
            business_category: RequestBody,
            business_type: RequestBody,
            gst_number: RequestBody,
            country: RequestBody,
            state: RequestBody,
            city: RequestBody,
            mobile_number: RequestBody,
            email: RequestBody,
            website: RequestBody,
            mobile_number2: RequestBody,
            finalfile: MultipartBody.Part?
    ){
        val alertDialog: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alertDialog.setTitle("Registration")
        alertDialog.setMessage("Please wait")
        val alert: android.app.AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()

        tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        token =PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)
        GetRetrofitInstance.instance.registrationAccountApi(
                tokentype + " " + token,
                name,
                business_name,
                business_category,
                business_type,
                gst_number,
                country,
                state,
                city,
                mobile_number,
                email,
                website,
                mobile_number2,
                finalfile
        ).enqueue(object : Callback<RegistrationModel> {
            override fun onResponse(
                    call: Call<RegistrationModel>,
                    response: Response<RegistrationModel>
            ) {
                // Log.e("Registration  Response", response.body()!!.data.toString())
                //Toast.makeText(this@RegistrationActivity,""+response.body()?.message,Toast.LENGTH_SHORT).show()
                Log.e("Registration  Response", response.body()!!.data.toString())

                if (response.body()!!.status == true) {

                    var userDetails: UserDetaisl = response.body()!!.data!!

                    PrefManager.setString(this@RegistrationActivity, PrefManager.isRegisterd, "True")
                    PrefManager.setString(this@RegistrationActivity, PrefManager.first_name, userDetails.firstName)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.business_name, userDetails.businessName)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.gst_number, userDetails.gstNumber)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.mobile_number, userDetails.mobileNumber)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.mobile_number2, userDetails.mobile_second)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.email, userDetails.email)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.website, userDetails.website)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.category_name, userDetails.categoryName)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.businesstype_name, userDetails.businesstypeName)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.country_name, userDetails.countryName)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.state_name, userDetails.stateName)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.city_name, userDetails.cityName)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.business_category_id, userDetails.businessCategoryId)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.business_type_id, userDetails.businessTypeId)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.country_id, userDetails.countryId)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.state_id, userDetails.stateId)
                    PrefManager.setString(this@RegistrationActivity, PrefManager.city_id, userDetails.cityId)


                    alert.dismiss()
                    val intent: Intent = Intent(
                            this@RegistrationActivity,
                            DashboardActivity::class.java
                    )


                    // intent.putExtra("userDetails",userDetails as Serializable)


                    startActivity(intent)
                    finish()
                } else {
                    alert.dismiss()
                    Toast.makeText(this@RegistrationActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<RegistrationModel>, t: Throwable) {
                alert.dismiss()
                Log.e("Registration  Response", "Failed")
            }
        })
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions" as CharSequence)
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings." as CharSequence)
        builder.setPositiveButton(
                "GOTO SETTINGS" as CharSequence,
                DialogInterface.OnClickListener { dialog, `$noName_1` ->
                    dialog.cancel()
                    openSettings()
                })
        builder.setNegativeButton("Cancel" as CharSequence, null)
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent("android.settings.APPLICATION_DETAILS_SETTINGS")
        val uri = Uri.fromParts("package", packageName, null as String?)
        intent.data = uri
        //startSetting.launch(intent)
        startActivity(intent)
    }
    private fun setData() {
        currentPhotoPath = imageCompression.compressImage(currentPhotoPath)
    }




}