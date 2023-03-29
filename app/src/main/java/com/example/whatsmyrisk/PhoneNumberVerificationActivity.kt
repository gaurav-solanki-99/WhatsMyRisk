package com.example.whatsmyrisk

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsmyrisk.MyConstants.Constants
import com.example.whatsmyrisk.MyModel.CeaterModel
import com.example.whatsmyrisk.MyModel.VerificationMaodel
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.R
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import com.goodiebag.pinview.Pinview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhoneNumberVerificationActivity :AppCompatActivity() {
    var otp=""
    var mobile=""
    var token=""
    var tokentype=""
    var validUser=""
    var pinview1: Pinview? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_phone_validation)
        supportActionBar?.hide()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)




        var param=intent.getBundleExtra("bundle")
        otp= param?.getString("otp").toString()
        mobile=param?.getString("mobile").toString()
        token=param?.getString("token").toString()
        tokentype=param?.getString("tokentype").toString()
        validUser=param?.getString("validUser").toString()

        Log.e("validUser","validUser  $validUser")

         pinview1 = findViewById<Pinview>(R.id.otp_pinview)
//        pinview1.setPinViewEventListener(object : Pinview.PinViewEventListener {
//            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
//                if (otp.equals(pinview1.value)){
//                    CreateVerifyApi()
//                }else{
//                  //  Toast.makeText(this@PhoneNumberVerificationActivity, "Success", Toast.LENGTH_SHORT).show()
//                }
//                //pinview1.value=otp
//
//            }
//        })

        pinview1?.apply {
            //setCursorColor(Color.BLUE);
            setTextSize(12)
            setTextColor(Color.BLACK)
            showCursor(false)
            value=otp


        }
       val  btn_verify_phone_number: TextView = findViewById<TextView>(R.id.btn_verify_phone_number);
        btn_verify_phone_number.setOnClickListener {
            if(Constants.getConnectionStatus(this))
            {
                Toast.makeText(this,"Verify Phone Number ",Toast.LENGTH_SHORT).show()
                CreateVerifyApi()
            }
            else
            {
                Toast.makeText(this,"Please Check Internet Connection ",Toast.LENGTH_SHORT).show()
            }

        }

        val  btn_tv_resend_otp: TextView = findViewById<TextView>(R.id.tv_resend_otp);
        btn_tv_resend_otp.setOnClickListener {

            Toast.makeText(this,"OTP Resend  ",Toast.LENGTH_SHORT).show()
            CreateUserApi()

        }




    }
    fun CreateVerifyApi(){

        Log.e("Number Verify mobile",mobile)
        Log.e("Number Verify otp",otp)
        val quotesApi = GetRetrofitInstance.instance

        quotesApi.verifyApi(mobile,otp).enqueue(object: Callback<VerificationMaodel> {




            override fun onFailure(call: Call<VerificationMaodel>, t: Throwable) {
                Toast.makeText(this@PhoneNumberVerificationActivity,""+ t.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<VerificationMaodel>, response: Response<VerificationMaodel>) {

                Log.e("Number Verify Code",response.code().toString())
                Log.e("Number Verify Body",response.body()?.data.toString())
                if (response != null) {
                    if (response.isSuccessful) {
//                        Toast.makeText(
//                            this@PhoneNumberVerificationActivity,
//                            "" + response.body()?.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
                        PrefManager.setString(
                                this@PhoneNumberVerificationActivity,
                                PrefManager.isRegisterd,
                               "False"
                        )
                       PrefManager.setString(
                                this@PhoneNumberVerificationActivity,
                                PrefManager.mobile_number,
                               mobile
                        )


                       if(response.body()!!.registered==true)
                       {
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.ACCESS_TOKEN, response.body()?.accessToken)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.TOKEN_TYPE, response.body()?.tokenType)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.isRegisterd, "True")
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.first_name,  response.body()!!.data!!.firstName)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.business_name, response.body()!!.data!!.businessName)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.gst_number, response.body()!!.data!!.gstNumber)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.mobile_number, response.body()!!.data!!.mobileNumber)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.mobile_number2, response.body()!!.data!!.mobileSecond)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.email, response.body()!!.data!!.email)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.website, response.body()!!.data!!.website)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.category_name, response.body()!!.data!!.categoryName)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.businesstype_name, response.body()!!.data!!.businesstypeName)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.country_name, response.body()!!.data!!.countryName)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.state_name, response.body()!!.data!!.stateName)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.city_name, response.body()!!.data!!.cityName)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.business_category_id, response.body()!!.data!!.businessCategoryId.toString())
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.business_type_id, response.body()!!.data!!.businessTypeId.toString())
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.country_id, response.body()!!.data!!.countryId.toString())
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.state_id, response.body()!!.data!!.stateId.toString())
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.city_id,response.body()!!.data!!.cityId.toString())
                           val intent:Intent = Intent(this@PhoneNumberVerificationActivity,DashboardActivity::class.java)
                           startActivity(intent)
                           finish()
                       }
                        else
                       {
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.ACCESS_TOKEN, response.body()?.accessToken)
                           PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.TOKEN_TYPE, response.body()?.tokenType)
                           val intent:Intent = Intent(this@PhoneNumberVerificationActivity,RegistrationActivity::class.java)
                           intent.putExtra("mobile",mobile)
                           startActivity(intent)
                       }
//                        if(validUser=="false")
//                       {
//
//                           val intent:Intent = Intent(this@PhoneNumberVerificationActivity,RegistrationActivity::class.java)
//                           intent.putExtra("mobile",mobile)
//                           startActivity(intent)
//                           finish()
//                       }

                    }else{
//                        Toast.makeText(
//                            this@PhoneNumberVerificationActivity,
//                            "" + response.message(),
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }
                }else{
//                    Toast.makeText(
//                        this@PhoneNumberVerificationActivity,
//                        "Server error please try again...",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }

            }
        })
    }


    fun CreateUserApi(){

        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("OTP Send")
        alertDialog.setMessage("Please wait")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()


        val quotesApi = GetRetrofitInstance.instance
        quotesApi.createAccountApi(mobile).enqueue(object : Callback<CeaterModel> {

            override fun onFailure(call: Call<CeaterModel>, t: Throwable) {
                Toast.makeText(this@PhoneNumberVerificationActivity, "" + t.message, Toast.LENGTH_SHORT).show()
                alert.dismiss()
            }

            override fun onResponse(call: Call<CeaterModel>, response: Response<CeaterModel>) {
                if (response != null) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@PhoneNumberVerificationActivity,
                            "" + response.body()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        alert.dismiss()
                        //Shred Prefrence Get
                        PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.MOBILE_NUMBER, mobile)
                        PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.OTP, response.body()?.otp)
                        PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.ACCESS_TOKEN, response.body()?.access_token)
                        PrefManager.setString(this@PhoneNumberVerificationActivity, PrefManager.TOKEN_TYPE, response.body()?.token_type)
                        pinview1?.apply {
                            //setCursorColor(Color.BLUE);
                            setTextSize(12)
                            setTextColor(Color.BLACK)
                            showCursor(false)
                            value=response.body()?.otp
                        }


                    } else {
                        Toast.makeText(
                            this@PhoneNumberVerificationActivity,
                            "" + response.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                        alert.dismiss()
                    }
                } else {
                    Toast.makeText(
                        this@PhoneNumberVerificationActivity,
                        "Server error please try again...",
                        Toast.LENGTH_SHORT
                    ).show()
                    alert.dismiss()
                }


            }
        })
    }
}