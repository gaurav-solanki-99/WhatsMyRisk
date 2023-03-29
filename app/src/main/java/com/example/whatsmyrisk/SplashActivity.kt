package com.example.whatsmyrisk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsmyrisk.PREF.PrefManager

class SplashActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_splash)
        supportActionBar?.hide()

       var tokentype:String= PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()

      if(tokentype==null||tokentype=="")
      {
          Handler().postDelayed({
             // val intent = Intent(this,RegistrationActivity::class.java)
            val intent = Intent(this,WelcomeActivity ::class.java)
              startActivity(intent)
              finish()

          },3000)
      }
      else
      {

         var isRegister:String =  PrefManager.getString(this, PrefManager.isRegisterd).toString()

         Log.e("isRegisterd",">>>>>>>>>>>  $isRegister")



          if(isRegister.equals("True"))
          {
              Handler().postDelayed({
                  //val intent = Intent(this,RegistrationActivity ::class.java)
                  val intent = Intent(this,DashboardActivity ::class.java)
                  startActivity(intent)
                  finish()

              },3000)
          }
          else
          {
              Handler().postDelayed({
                  //val intent = Intent(this,RegistrationActivity ::class.java)
                  val intent = Intent(this,RegistrationActivity ::class.java)
                  startActivity(intent)
                  finish()

              },3000)
          }


      }


//        Handler().postDelayed({
//            val intent = Intent(this,WelcomeActivity ::class.java)
//            startActivity(intent)
//            finish()
//
//        },3000)




    }
}