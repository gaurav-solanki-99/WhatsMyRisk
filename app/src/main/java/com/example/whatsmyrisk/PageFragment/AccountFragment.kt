package com.example.whatsmyrisk.PageFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.whatsmyrisk.DashboardActivity
import com.example.whatsmyrisk.MyConstants.Constants
import com.example.whatsmyrisk.MyModel.CountryList
import com.example.whatsmyrisk.MyModel.RegistrationModel
import com.example.whatsmyrisk.MyModel.StatusMessage
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.R
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AccountFragment : Fragment() {

    lateinit var tokentype: String
    lateinit var token: String
    lateinit var title: String
    lateinit var message: String
    lateinit var editTextTile: EditText
    lateinit var editTextMessage: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v: View = inflater.inflate(R.layout.account_fragment_layout, container, false)

        v.findViewById<TextView>(R.id.aap_heading).setText("Contact us")
        val btn_send_message: TextView = v.findViewById(R.id.btn_send_message)
         editTextTile = v.findViewById(R.id.et_conatct_title)
         editTextMessage = v.findViewById(R.id.et_conatct_message)
        v.findViewById<ImageView>(R.id.btn_back_bar).visibility=View.GONE

        if(!context?.let { Constants.getConnectionStatus(it) }!!)
        {
            Toast.makeText(context,"Please Check Internet Connection ",Toast.LENGTH_SHORT).show()
        }

        btn_send_message.setOnClickListener() {
            tokentype = PrefManager.getString(v.context, PrefManager.TOKEN_TYPE).toString()
            token = PrefManager.getString(v.context, PrefManager.ACCESS_TOKEN).toString()
            title=editTextTile.text.toString()
            message=editTextMessage.text.toString()




            Log.e("getAllCountryList", "Methode Call : " + tokentype + " " + token)
            if(validate())
            {
                ContactusApi()
            }


        }



        return v
    }

    fun ContactusApi() {
        GetRetrofitInstance.instance.contactUS(tokentype + " " + token, title, message).enqueue(object : retrofit2.Callback<StatusMessage> {
            override fun onResponse(call: Call<StatusMessage>, response: Response<StatusMessage>) {
                Toast.makeText(context,""+response.body()!!.message,Toast.LENGTH_LONG).show()
                editTextTile.setText("")
                editTextMessage?.setText("")
            }

            override fun onFailure(call: Call<StatusMessage>, t: Throwable) {
                Toast.makeText(context,"Contact Fail "+t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    fun validate():Boolean{
        title = editTextTile.text.toString()
        message=editTextMessage.text.toString()
        if (title.isNullOrEmpty()){
            editTextTile?.requestFocus()
            editTextTile?.error="Please Enter Title"
            return false
        }
        if (message.isNullOrEmpty()){
            editTextMessage?.requestFocus()
            editTextMessage?.error="Please Enter Message *"
            return false
        }



        return true
    }


}