package com.example.whatsmyrisk

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsmyrisk.MyModel.TermsConditions
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class TermsConditionActivity : AppCompatActivity()
{
    var token=""
    var tokentype=""
    var textView : TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_terms_condition)
        supportActionBar?.hide()
        textView=findViewById(R.id.terms_tv)

        findViewById<ImageView>(R.id.btn_back_bar).setOnClickListener(){
           onBackPressed()
        }


         getAllData();
    }

     fun getAllData() {
         tokentype= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
         token= PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
         GetRetrofitInstance.instance.btermConditionsApi(tokentype + " " + token).enqueue(object :Callback<TermsConditions>{
             override fun onResponse(call: Call<TermsConditions>, response: Response<TermsConditions>) {
                 if(response.body()!!.status==true)
                 {
                    var details :String =  response.body()!!.data[0].optionValue.toString()
                     textView!!.setText(details)
                 }
             }

             override fun onFailure(call: Call<TermsConditions>, t: Throwable) {
                 TODO("Not yet implemented")
             }

         })

    }
}