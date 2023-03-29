package com.example.whatsmyrisk

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.whatsmyrisk.MyConstants.Constants.Companion.getConnectionStatus
import com.example.whatsmyrisk.MyModel.CeaterModel
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.jar.Manifest


class PhoneNumberActivity :AppCompatActivity() {
    var etmobilenumber: EditText? = null
    private var strmobile=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_phone_number)
        supportActionBar?.hide()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        val btn_next =findViewById<TextView>(R.id.btn_next_process2)
        etmobilenumber =findViewById<EditText>(R.id.etmobilenumber)

        supportActionBar?.hide()
        findViewById<EditText>(R.id.etmobilenumber).setSelection(findViewById<EditText>(R.id.etmobilenumber).length())

        findViewById<EditText>(R.id.etmobilenumber).addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                if (!s.toString().contains("+91")) {
                    findViewById<EditText>(R.id.etmobilenumber).setText("+91")
                }
            }
        })



         Log.e("Internet Connection "," >>>>>>> "+ getConnectionStatus(this))

     getConnectionStatus(this)




          btn_next.setOnClickListener {

              if(getConnectionStatus(this))
              {
                  if (validate()){
                      CreateUserApi()
                  }
              }
              else
              {
                  Toast.makeText(this,"Please Check Internet Connection ",Toast.LENGTH_SHORT).show()
              }


          }


    }
    override fun onBackPressed() {

        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Exit")
        builder.setMessage("Are you sure you want to Exit ?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
            finishAffinity()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
            return@OnClickListener
        })
        builder.show()



    }
    fun validate():Boolean{
        strmobile = etmobilenumber?.text.toString().replace("+91","")
        if (strmobile.length == 0){
            etmobilenumber?.requestFocus()
            etmobilenumber?.error="Please enter valid mobile number"
            return false
        }
        if (!isValidMobile(strmobile)){
            etmobilenumber?.requestFocus()
            etmobilenumber?.error="Please enter valid mobile number"
            return false
        }
        if (strmobile.length!==10){
            etmobilenumber?.requestFocus()
            etmobilenumber?.error="Please enter valid mobile number"
            return false
        }

        return true
    }
    fun CreateUserApi(){

        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("OTP Send")
        alertDialog.setMessage("Please wait")
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()


        val quotesApi = GetRetrofitInstance.instance
        quotesApi.createAccountApi(strmobile).enqueue(object : Callback<CeaterModel> {

            override fun onFailure(call: Call<CeaterModel>, t: Throwable) {
                Toast.makeText(this@PhoneNumberActivity, "" + t.message, Toast.LENGTH_SHORT).show()
                alert.dismiss()
            }

            override fun onResponse(call: Call<CeaterModel>, response: Response<CeaterModel>) {

                Log.e("Mobile No Send",response.body().toString())
                if (response != null) {

                    if (response.isSuccessful) {

                         // if(response.body()?.user_status?.toInt()==1)
                          if(response.isSuccessful)
                          {

                              Toast.makeText(
                                      this@PhoneNumberActivity,
                                      "" + response.body()?.message,
                                      Toast.LENGTH_SHORT
                              ).show()
                              alert.dismiss()
                              //Shred Prefrence Get
                              PrefManager.setString(this@PhoneNumberActivity, PrefManager.MOBILE_NUMBER, strmobile)
                              PrefManager.setString(this@PhoneNumberActivity, PrefManager.OTP, response.body()?.otp)
                              PrefManager.setString(this@PhoneNumberActivity, PrefManager.ACCESS_TOKEN, response.body()?.access_token)
                              PrefManager.setString(this@PhoneNumberActivity, PrefManager.TOKEN_TYPE, response.body()?.token_type)


                              val bundle = Bundle()
                              bundle.putString("mobile", strmobile)
                              bundle.putString("otp", response.body()?.otp)
                              bundle.putString("token", response.body()?.access_token)
                              bundle.putString("tokentype", response.body()?.token_type)
                              bundle.putString("validUser", response.body()?.status.toString())
                              Log.e("NumberActivity", "validUser  " + response.body()?.status)

                              //sendMessage(strmobile,response.body()!!.otp)
                              val intent: Intent = Intent(
                                      this@PhoneNumberActivity,
                                      PhoneNumberVerificationActivity::class.java
                              )
                              intent.putExtra("bundle", bundle)
                              startActivity(intent)
                              finish()
                          }
                        else
                          {       alert.dismiss()
                                Toast.makeText(this@PhoneNumberActivity,"In user inactive",Toast.LENGTH_SHORT).show()
                          }
                    } else {
                        Toast.makeText(
                                this@PhoneNumberActivity,
                                "" + response.message(),
                                Toast.LENGTH_SHORT
                        ).show()
                        alert.dismiss()
                    }
                } else {
                    Toast.makeText(
                            this@PhoneNumberActivity,
                            "Server error please try again...",
                            Toast.LENGTH_SHORT
                    ).show()
                    alert.dismiss()
                }


            }
        })
    }
    fun sendMessage(number:String, otp:String){
        Log.e("SendMessage",">>>>>>>>>>>>>>>>>")
        val phoneNumber = number
        val message = otp

        // on the below line we are creating a try and catch block
        try {

            // on below line we are initializing sms manager.
            val smsManager: SmsManager = SmsManager.getDefault()


            // PErmission



            // on below line we are sending text message.
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)

            // on below line we are displaying a toast message for message send,
            Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {

            // on catch block we are displaying toast message for error.

            Log.e("SendMessage","Error>>>>>>>>>>>>>>>>>"+e.message.toString())
            Toast.makeText(applicationContext, "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
                    .show()
        }
    }
    private fun isValidMobile(mobile: String):Boolean{
        return Patterns.PHONE.matcher(mobile).matches()
    }




}