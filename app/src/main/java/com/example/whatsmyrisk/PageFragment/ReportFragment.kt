package com.example.whatsmyrisk.PageFragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.whatsmyrisk.DashboardActivity
import com.example.whatsmyrisk.MyConstants.Constants
import com.example.whatsmyrisk.MyConstants.ImageCompression
import com.example.whatsmyrisk.MyConstants.RealPathUtil
import com.example.whatsmyrisk.MyModel.*
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.R
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

class ReportFragment : Fragment() {

    var dialog: Dialog? = null
    lateinit var context2:  Context


    var country_name_list: java.util.ArrayList<String> = ArrayList()
    var country_id_list: java.util.ArrayList<String> = ArrayList()

    var etUserName: EditText? = null
    var etBuisnessName: EditText? = null
    var etGstNumnber: EditText? = null
    var etMobileNo: EditText? = null
    var etMobileNo2: EditText? = null
    var etMobileNo3: EditText? = null
    var etEmailId: EditText? = null
    var etWebsite: EditText? = null
    lateinit var pickphoto: TextView

    lateinit var spinner_buisness_category: Spinner
    lateinit var spinner_buisness_type: Spinner
    lateinit var spinner_countyr: Spinner
    lateinit var spinner_state: Spinner
    lateinit var spinner_city: Spinner
    lateinit var btn_welcome_process: TextView
    lateinit var userDetails: UserDetaisl


    var select_country_id: String = "0"
    var select_state_id: String = "0"
    var select_city_id: String = "0"
    var select_category_id: String = "0"
    var select_buisnessType_id: String = "0"

    var token = ""
    var tokentype = ""
    private var currentPhotoPath: String? = ""
    lateinit  var userName: String
    lateinit var buisnessName: String
    lateinit var gstNumnber: String
    lateinit  var  mobileNo: String
    lateinit  var mobileNo2: String
    lateinit   var mobileNo3: String
    lateinit   var emailId: String
    lateinit   var website: String

    private var photoURI: Uri? = null
    lateinit var imageCompression: ImageCompression








    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.report_fragment_layout, container, false)
//        var userDetails = arguments?.getSerializable("userDetails") as UserDetaisl
//        Log.e("userDetails","ReportFragment >>  $userDetails")

        activity?.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        context2=view.context


        etUserName = view.findViewById<EditText>(R.id.et_prof_username)
        etBuisnessName = view.findViewById<EditText>(R.id.et_prof_buisnessName)
        etGstNumnber = view.findViewById<EditText>(R.id.et_prof_gstNumber)
        etMobileNo = view.findViewById<EditText>(R.id.et_prof_mobileNo)
        etMobileNo2 = view.findViewById<EditText>(R.id.et_prof_mobileNo2)
        etMobileNo3 = view.findViewById<EditText>(R.id.et_prof_mobileNo3)
        etEmailId = view.findViewById<EditText>(R.id.et_prof_emaild)
        etWebsite = view.findViewById<EditText>(R.id.et_prof_website)
        pickphoto = view.findViewById<TextView>(R.id.pickgallry)


        spinner_buisness_category = view.findViewById<Spinner>(R.id.spinner_buisness_category)
        spinner_buisness_type = view.findViewById<Spinner>(R.id.spinner_buisness_type)
        spinner_countyr = view.findViewById<Spinner>(R.id.spinner_countyr)
        spinner_state = view.findViewById<Spinner>(R.id.spinner_state)
        spinner_city = view.findViewById<Spinner>(R.id.spinner_city)
        imageCompression = ImageCompression(requireContext())



        Log.e("CategoryID>>",""+PrefManager.getString(view.context, PrefManager.business_category_id).toString())
        


        // if(userDetails!=null)
        // {
        etUserName!!.setText(PrefManager.getString(view.context, PrefManager.first_name).toString())
        etBuisnessName!!.setText(PrefManager.getString(view.context, PrefManager.business_name).toString())
        etGstNumnber!!.setText(PrefManager.getString(view.context, PrefManager.gst_number).toString())
        etMobileNo!!.setText(PrefManager.getString(view.context, PrefManager.mobile_number).toString())
        etMobileNo2!!.setText(PrefManager.getString(view.context, PrefManager.mobile_number2).toString())
        etMobileNo3!!.setText(PrefManager.getString(view.context, PrefManager.mobile_number).toString())
        etEmailId!!.setText(PrefManager.getString(view.context, PrefManager.email).toString())
        etWebsite!!.setText(PrefManager.getString(view.context, PrefManager.website).toString())


        select_category_id=PrefManager.getString(view.context, PrefManager.business_category_id).toString()
        select_buisnessType_id=PrefManager.getString(view.context, PrefManager.business_type_id).toString()
        select_country_id=PrefManager.getString(view.context, PrefManager.country_id).toString()
        select_state_id=PrefManager.getString(view.context, PrefManager.state_id).toString()
        select_city_id=PrefManager.getString(view.context, PrefManager.city_id).toString()










        view.findViewById<TextView>(R.id.search_buisness_category)!!.setText(PrefManager.getString(view.context, PrefManager.category_name).toString())
        view.findViewById<TextView>(R.id.search_buisness_type)!!.setText(PrefManager.getString(view.context, PrefManager.businesstype_name).toString())
        view.findViewById<TextView>(R.id.search_spinner_country2)!!.setText(PrefManager.getString(view.context, PrefManager.country_name).toString())
        view.findViewById<TextView>(R.id.search_spinner_state)!!.setText(PrefManager.getString(view.context, PrefManager.state_name).toString())
        view.findViewById<TextView>(R.id.search_spinner_city)!!.setText(PrefManager.getString(view.context, PrefManager.city_name).toString())






//         select_state_id = PrefManager.getString(view.context, PrefManager.first_name).toString()
//         select_city_id = PrefManager.getString(view.context, PrefManager.first_name).toString()
//         select_category_id = PrefManager.getString(view.context, PrefManager.first_name).toString()
//         select_buisnessType_id = PrefManager.getString(view.context, PrefManager.first_name).toString()




        //}


        //To get All country List
        getAllCountryList(view.context)


        //To get All Category List
        getAllCategoryList(view.context)

        //To get All Category List
        getAllBuisnessTypeList(view.context)


        //spinner_countyr.setSelection(country_id_list.indexOf(1))
        pickphoto.setOnClickListener(View.OnClickListener {
            Constants.checkPermission(requireActivity()) { isGranted ->
                if (isGranted) {
                    selectImage()

                } else {
                    showSettingsDialog()
                }
            }
        })


        view.findViewById<TextView>(R.id.btn_update_profile).setOnClickListener {

              Log.e("Update Profile ", ""+select_city_id.toString())

            if (validate(view))
            {



                    var multipartprofile: MultipartBody.Part? = null

                    if (currentPhotoPath?.isNotEmpty()!!) {
                        val file = File(currentPhotoPath)
                        val reqFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                        multipartprofile =
                            MultipartBody.Part.createFormData("user_certificate", file.name, reqFile)
                    }


                Log.e("Updated Category ","$select_category_id")

                    val name: RequestBody = Constants.createPartFromString(userName)
                    val business_name: RequestBody = Constants.createPartFromString(buisnessName)
                    val business_category: RequestBody = Constants.createPartFromString(select_category_id)
                    val business_type: RequestBody = Constants.createPartFromString(select_buisnessType_id)
                    val gst_number: RequestBody = Constants.createPartFromString(gstNumnber)
                    val country: RequestBody = Constants.createPartFromString(select_country_id)
                    val state: RequestBody = Constants.createPartFromString(select_state_id)
                    val city: RequestBody = Constants.createPartFromString(select_city_id)
                    val email: RequestBody = Constants.createPartFromString(emailId)
                    val website: RequestBody = Constants.createPartFromString(website)
                    val mobile_number: RequestBody = Constants.createPartFromString(mobileNo)
                    val mobile_second: RequestBody = Constants.createPartFromString(mobileNo2)


                updateUserProfile(
                    view.context,
                    name,
                    business_name,
                    business_category,
                    business_type,
                    gst_number,
                    country,
                    state,
                    city,
                    email,
                    website,
                    multipartprofile,
                        mobile_number,
                        mobile_second
                )


         }
        }













        return view

    }
    fun selectImage()
    {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(requireContext())
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
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(requireActivity())
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
        val uri = Uri.fromParts("package", activity?.packageName, null as String?)
        intent.data = uri
        //startSetting.launch(intent)
        startActivity(intent)
    }

    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile = createImageFile()
        val uri = FileProvider.getUriForFile(requireContext(), activity?.packageName + ".provider", photoFile!!
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
                        currentPhotoPath = RealPathUtil.getRealPathFromURIAPI19(requireContext(), uri)!!
                        setData()
                        Log.e("PHOTOS SELECTED ", currentPhotoPath.toString())
                        Toast.makeText(requireContext(), "Certificate saved Successfully", Toast.LENGTH_LONG).show()

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
                        currentPhotoPath = RealPathUtil.getRealPathFromURIAPI19(requireContext(), uri)!!
                        setData()
                        Log.e("PHOTOS CAPTURE ", currentPhotoPath.toString())
                        Toast.makeText(requireContext(), "Certificate saved Successfully", Toast.LENGTH_LONG).show();


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
    private fun setData() {
        currentPhotoPath = imageCompression.compressImage(currentPhotoPath)

    }

    fun getAllCountryList(context: Context){
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype= PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token=PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)





        GetRetrofitInstance.instance.countryApi(tokentype + " " + token).enqueue(object :
                Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllCountryList", "response : " + response.body())

               // countryAllList = countryList!!.data
               // Log.e("getAllCountryList", "countryAllList : " + countryAllList.size.toString())


              //  Log.e("All Country List ", countryAllList.size.toString())



             //   country_name_list.add("Select Country")
             //   country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())


                val adapter3 = ArrayAdapter(
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
                                select_country_id = country_id_list.get(position)
                                getAllStateList(select_country_id.toInt(),context)
                            }

                            override fun onNothingSelected(parentView: AdapterView<*>?) {}
                        }


                activity!!.findViewById<View>(R.id.search_spinner_country2)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(context)

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
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                Log.e("@@@", "Country View ID "+R.id.search_spinner_country2+ view)

                                activity!!.findViewById<TextView>(R.id.search_spinner_country2)!!.setText(adapter3.getItem(position))


                                val item: String = adapter3.getItem(position).toString()

                                val selectedIndex = country_name_list.indexOf(item)


                                select_country_id = country_id_list[selectedIndex]

                                Log.e("Select Country Value ", "" + item + "" + select_country_id)
                                getAllStateList(select_country_id.toInt(),context)

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })









                // Toast.makeText(this@RegistrationActivity, "All Country List" + countryAllList.size.toString(), Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<CountryList>, t: Throwable) {
                // Toast.makeText(this@RegistrationActivity, "CountryList error please try again...", Toast.LENGTH_SHORT).show()
            }

        })


    }

    fun getAllStateList(country_id: Int, context: Context) {
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        var tokentype = PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        var token = PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)




        GetRetrofitInstance.instance.stateApi(tokentype + " " + token, country_id).enqueue(object :
                Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllStateList", "response : " + response.body())

                //  countryAllList = countryList!!.data
                //    Log.e("getAllStateList", "stateyAllList : " + countryAllList.size.toString())


                //   Log.e("All Country List ", countryAllList.size.toString())


                var state_name_list: java.util.ArrayList<String> = ArrayList()
                var state_id_list: java.util.ArrayList<String> = ArrayList()
              //  state_name_list.add("State")
              //  state_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    state_name_list.add(response.body()!!.data!![i].name!!)
                    state_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", state_name_list.toString())


                val adapter3 = ArrayAdapter(
                        context,
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
                        select_state_id = state_id_list.get(position)
                        getAllCityList(select_state_id.toInt(), context)
                    }

                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
                }


                activity!!.findViewById<View>(R.id.search_spinner_state)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(context)

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
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                activity!!.findViewById<TextView>(R.id.search_spinner_state)!!.setText(adapter3.getItem(position))


                                val item: String = adapter3.getItem(position).toString()

                                val selectedIndex = state_name_list.indexOf(item)

                                select_state_id = state_id_list[selectedIndex]
                                getAllCityList(select_state_id.toInt(),context)

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


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


    fun getAllCityList(state_id: Int, context: Context) {
        Log.e("getAllCountryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype = PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token = PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)




        GetRetrofitInstance.instance.cityApi(tokentype + " " + token, state_id).enqueue(object :
                Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllCityList", "response : " + response.body())

                // countryAllList = countryList!!.data
                //   Log.e("getAllCityList", "getAllCityList : " + countryAllList.size.toString())


                var city_name_list: java.util.ArrayList<String> = ArrayList()
                var city_id_list: java.util.ArrayList<String> = ArrayList()
              //  city_name_list.add("City")
             //   city_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    city_name_list.add(response.body()!!.data!![i].name!!)
                    city_id_list.add(response.body()!!.data!![i].id!!.toString())
                }


                val adapter4 = ArrayAdapter(
                        context,
                        android.R.layout.simple_dropdown_item_1line,
                        city_name_list
                )
                adapter4.setDropDownViewResource(R.layout.single_spinner_items)
                spinner_city.adapter = adapter4








//                spinner_city.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(
//                            parentView: AdapterView<*>?,
//                            selectedItemView: View,
//                            position: Int,
//                            id: Long
//                    ) {
//                        (parentView!!.getChildAt(0) as TextView).setTextColor(resources.getColor(R.color.black))
//                        select_city_id = city_id_list.get(position)
//
//
//
//                    }
//
//                    override fun onNothingSelected(parentView: AdapterView<*>?) {}
//                }

                activity!!.findViewById<View>(R.id.search_spinner_city)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(context)

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
                            AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                                // set selected item on textView
                                activity!!.findViewById<TextView>(R.id.search_spinner_city)!!.setText(adapter4.getItem(position))


                                val item: String = adapter4.getItem(position).toString()

                                val selectedIndex = city_name_list.indexOf(item)

                                select_city_id = city_id_list[selectedIndex]

                                Toast.makeText(context,"Selected City Id is $selectedIndex $select_city_id",Toast.LENGTH_SHORT).show()

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })



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


    fun getAllCategoryList(context: Context) {
        Log.e("getAllCategoryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype = PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token = PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllCategoryList", "Methode Call : " + tokentype + " " + token)





        GetRetrofitInstance.instance.categoryApi(tokentype + " " + token).enqueue(object :
                Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllCategoryList", "response : " + response.body())

                // countryAllList = countryList!!.data
                //   Log.e("getAllCategoryList", "countryAllList : " + countryAllList.size.toString())


                // Log.e("All Country List ", countryAllList.size.toString())


                var country_name_list: java.util.ArrayList<String> = ArrayList()
                var country_id_list: java.util.ArrayList<String> = ArrayList()
               // country_name_list.add("Buisness Category")
              //  country_id_list.add("0")
                for (i in 0 until response.body()!!.data.size) {
                    country_name_list.add(response.body()!!.data[i].name!!)
                    country_id_list.add(response.body()!!.data[i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())


                val adapter1 = ArrayAdapter(
                        context,
                        android.R.layout.simple_dropdown_item_1line,
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

                activity!!.findViewById<View>(R.id.search_buisness_category)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(context)

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
                                activity!!.findViewById<TextView>(R.id.search_buisness_category)!!.setText(adapter1.getItem(position))


                                val item: String = adapter1.getItem(position).toString()


                                val selectedIndex = country_name_list.indexOf(item)

                                select_category_id = country_id_list[selectedIndex]

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


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

    fun getAllBuisnessTypeList(context: Context) {
        Log.e("getAllCategoryList", "Methode Call : ")

        // tokentype = "Bearer"
        // token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNGJjNjE0MWU1MTdkYjk4NmY5ZjNmYzBhNzc1N2YyODZhZGFhNjU1YzQyMDFkMWM1N2M4YzQzZmUxMWU3MGVjZjFlYTY3MWU1ZjU5OTRjYzYiLCJpYXQiOjE2NTIyNTQwMzQuNTg1MzYyLCJuYmYiOjE2NTIyNTQwMzQuNTg1MzczLCJleHAiOjE3MTU0MTI0MzQuNTQyNTgzLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.CI6yTfs8qqtABh-GuLeB7VOFCTe6A5EvuoGEvEl4r9FQR9qleAi4LcqzCdOFj9f1V53Acfkn-y1DzlsH0SgmVt_W8rwux-ykNfyJqZNfJynEaoqRY1c27dxWPg6XeqJzrVKtXhyS8CmRyeJaEOTRRUnvr4PJGh0cKYrAAYFohdoUbhn-vvYKk9HObR3efXbt3kN3KhEVD3M7Z8ZkhH8nhDr2eb7w1b1o5TfVjXtavD0wTzxvXHp29h189UkgcODRJn_hxuOVZKSjfGhUNeY_LPlzOa36g2XGDnzcSaZ8ZoDmzrWWCHxueCHmsK2A3QMpAsawpmcNPLZ3aYMrvMh44B0VOjIzniTGdghOh4FsbY5ps6Beu4J9lQUGPjlHRKoPtyYZsXmcncUU0VlWeh-AzUZgdgD_BDnidqSeMUHbSVBMuOMgRQF-SledRDTfgfwNG-_4FO9Oa3GsQDZwCFtp-FgEHRo56-6Tajlz-IJMad5kpOmdZul_j7gELivoWyC7113xNSP_C3a-7iv-YV-Fkw3DiwuHyo0GM0TPH2rsX5nnPc-7NpS2PhwCgqDtzttYLECeHszULeX6Vv_P_xxNBcvypUyGO7gIbG9IMcTkIhgq6jsmMsaemYhzebr2FAL2V8OsxTxHhTk8iZQTOE1Qr28x0jr1zHq9SBMEAOmEN7w"

        tokentype = PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token = PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()

        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)





        GetRetrofitInstance.instance.buisnessTypeApi(tokentype + " " + token).enqueue(object :
                Callback<CountryList> {
            override fun onResponse(call: Call<CountryList>, response: Response<CountryList>) {

                val countryList: CountryList? = response.body()
                Log.e("getAllBuisnessTypeList", "response : " + response.body())

                // countryAllList = countryList!!.data
//                Log.e(
//                        "getAllBuisnessTypeList",
//                        "countryAllList : " + countryAllList.size.toString()
//                )


                var country_name_list: java.util.ArrayList<String> = ArrayList()
                var country_id_list: java.util.ArrayList<String> = ArrayList()
              //  country_name_list.add("Buisness Type")
               // country_id_list.add("0")
                for (i in 0 until response.body()!!.data!!.size) {
                    country_name_list.add(response.body()!!.data!![i].name!!)
                    country_id_list.add(response.body()!!.data!![i].id!!.toString())
                }
                Log.e("All Country List ", country_name_list.toString())


                val adapter2 = ArrayAdapter(
                        view!!.context,
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
                                select_buisnessType_id = country_id_list.get(position)


                            }

                            override fun onNothingSelected(parentView: AdapterView<*>?) {}
                        }



                activity!!.findViewById<View>(R.id.search_buisness_type)!!.setOnClickListener(View.OnClickListener {
                    // Initialize dialog
                    dialog = Dialog(context)

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
                                activity!!.findViewById<TextView>(R.id.search_buisness_type)!!.setText(adapter2.getItem(position))


                                val item: String = adapter2.getItem(position).toString()

                                val selectedIndex = country_name_list.indexOf(item)

                                select_buisnessType_id = country_id_list[selectedIndex]

                                // Dismiss dialog
                                dialog!!.dismiss()
                            }
                })


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
    fun validate(view: View):Boolean{
        userName = etUserName?.text.toString()

        buisnessName = etBuisnessName?.text.toString()
        gstNumnber = etGstNumnber?.text.toString()
        emailId = etEmailId?.text.toString()
        website = etWebsite?.text.toString()
        mobileNo=etMobileNo?.text.toString()
        mobileNo2=etMobileNo2?.text.toString()

        

        
        


        etMobileNo!!.isEnabled=false
        if (userName.isNullOrEmpty()){
            etUserName?.requestFocus()
            etUserName?.error="Please enter Name "
            return false
        }
        if (buisnessName.isNullOrEmpty()){
            etBuisnessName?.requestFocus()
            etBuisnessName?.error="Please Enter Buisness Name"
            return false
        }
        if(select_category_id=="0")
        {
            Toast.makeText(activity,"Please Select Business category",Toast.LENGTH_LONG).show()
            return  false
        }
        if(select_buisnessType_id=="0")
        {
            Toast.makeText(activity,"Please Select Business Type",Toast.LENGTH_LONG).show()
            return  false
        }
        if(gstNumnber.isNotEmpty())
        {
            if (gstNumnber.length!=15){
                etGstNumnber?.requestFocus()
                etGstNumnber?.error="Please enter valid GST number"
                return false
            }
        }


        if(select_country_id=="0")
        {
            Toast.makeText(activity,"Please Select Country",Toast.LENGTH_LONG).show()
            return  false
        }
        if(select_state_id=="0")
        {
            Toast.makeText(activity,"Please Select State",Toast.LENGTH_LONG).show()
            return  false
        }
        if(select_city_id=="0")
        {
            Toast.makeText(activity,"Please Select City",Toast.LENGTH_LONG).show()
            return  false
        }

        if (mobileNo.length!=10){   //mobile_alternate!!.length!==10
            etMobileNo?.requestFocus()
            etMobileNo?.error="Mobile number should be 10 digits"
            return false
        }
        if(mobileNo2.isNotEmpty()) {
            if (mobileNo2.length!=10){
                etMobileNo2?.requestFocus()
                etMobileNo2?.error="Mobile number should be 10 digits"
                return false
            }
        }
        if(emailId.isNotEmpty())
        {
//            if (emailId.isNullOrEmpty()){
//                etEmailId?.requestFocus()
//                etEmailId?.error="Please Enter Email"
//                return false
//            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
                etEmailId?.requestFocus()
                etEmailId?.error="Please Enter valid Email"
                return false
            }
        }



//        if (website.isNullOrEmpty()){
//            etWebsite?.requestFocus()
//            etWebsite?.error="Please Enter website"
//            return false
//        }

        return true
    }




    fun updateUserProfile(
        context: Context,
        name: RequestBody,
        business_name: RequestBody,
        business_category: RequestBody,
        business_type: RequestBody,
        gst_number: RequestBody,
        country: RequestBody,
        state: RequestBody,
        city: RequestBody,
        email: RequestBody,
        website: RequestBody,
        finalfile: MultipartBody.Part?,
        mobile_number: RequestBody,
        mobile_second: RequestBody,
    ){


        Log.e("File Upload is ", ""+finalfile)

        Log.e("All Selected ID","$select_city_id $select_state_id $select_city_id $select_buisnessType_id $select_category_id "+Constants.createPartFromString(select_country_id))

        tokentype= PrefManager.getString(context, PrefManager.TOKEN_TYPE).toString()
        token =PrefManager.getString(context, PrefManager.ACCESS_TOKEN).toString()
        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)
        GetRetrofitInstance.instance.updateProfileApi(
            tokentype + " " + token,
            name,
            business_name,
            business_category,
            business_type,
            gst_number,
            country,
            state,
            city,
            email,
            website,
            finalfile,
                mobile_number,
                mobile_second

        ).enqueue(object : Callback<UpdateProfile> {
            override fun onResponse(
                call: Call<UpdateProfile>,
                response: Response<UpdateProfile>
            ) {
                // Log.e("Registration  Response", response.body()!!.data.toString())
                //Toast.makeText(this@RegistrationActivity,""+response.body()?.message,Toast.LENGTH_SHORT).show()
                Log.e("UpdateProfile  Response", response.body()!!.user.toString())
//                Log.e("UpdateProfile  Response Code", response.code().toString())
                var message = response.body()!!.message


                if(response.body()!!.status==true)
                {
                    var UpdateUser :UpdateUser = response.body()!!.user!!

                    Log.e("Updated Value "," $select_category_id "+UpdateUser.category_name)
                    Log.e("Updated Value "," "+UpdateUser.businesstype_name)
                    Log.e("Updated Value "," "+UpdateUser.country_name)
                    Log.e("Updated Value "," "+UpdateUser.state_name)
                    Log.e("Updated Value "," "+UpdateUser.city_name)

                    PrefManager.setString(context, PrefManager.first_name,UpdateUser.firstName)
                    PrefManager.setString(context, PrefManager.business_name,UpdateUser.businessName)
                    PrefManager.setString(context, PrefManager.gst_number,UpdateUser.gstNumber)
                    PrefManager.setString(context, PrefManager.mobile_number,UpdateUser.mobileNumber)
                    PrefManager.setString(context, PrefManager.email,UpdateUser.email)
                    PrefManager.setString(context, PrefManager.website,UpdateUser.website)
                    PrefManager.setString(context, PrefManager.mobile_number2,UpdateUser.mobileSecond)
                    PrefManager.setString(context, PrefManager.category_name,UpdateUser.category_name)
                    PrefManager.setString(context, PrefManager.businesstype_name,UpdateUser.businesstype_name)
                    PrefManager.setString(context, PrefManager.country_name,UpdateUser.country_name)
                    PrefManager.setString(context, PrefManager.state_name,UpdateUser.state_name)
                    PrefManager.setString(context, PrefManager.city_name,UpdateUser.city_name)

                    Toast.makeText(activity,"Update Profile Successfully",Toast.LENGTH_LONG).show()





                    val intent: Intent = Intent(context, DashboardActivity::class.java)



                    // intent.putExtra("userDetails",userDetails as Serializable)



                    startActivity(intent)
                    activity!!.finish()
                }
                else
                {
                   Toast.makeText(context,""+message,Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<UpdateProfile>, t: Throwable) {
                Log.e("UpdateProfile  Response", "Failed "+t.message)
            }
        })
    }

}





