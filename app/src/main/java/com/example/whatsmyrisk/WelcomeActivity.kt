package com.example.whatsmyrisk

import Step2
import Step3
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.whatsmyrisk.Fragments.Step1
import com.example.whatsmyrisk.MyAdapter.ViewPagerAdapter
import com.example.whatsmyrisk.MyInterfaces.ApiInterface
import com.example.whatsmyrisk.MyModel.DumyList
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WelcomeActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_welcome)
        supportActionBar?.hide()
        val welcomeTextView = findViewById<TextView>(R.id.welocmetext)
        val tab_viewpager = findViewById<ViewPager>(R.id.tab_viewpager)
        val dotsIndicator = findViewById<SpringDotsIndicator>(R.id.spring_dots_indicator)
        val btn_next:TextView=findViewById<TextView>(R.id.btn_welcome_process)
        setupViewPager(tab_viewpager)
        dotsIndicator.setViewPager(tab_viewpager)


        //~~~~~~~~~~~~~~~~~~~~~~~~~~Test Dumy Api For  Retrofit~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        print("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
//        val quotesApi = GetRetrofitInstance.instance
//        quotesApi.getAllData().enqueue(object:Callback<DumyList>{
//
//          override fun onFailure(call: Call<DumyList>, t: Throwable) {
//             // Toast.makeText(this@WelcomeActivity,""+ t.message,Toast.LENGTH_SHORT).show()
//          }
//
//          override fun onResponse(call: Call<DumyList>, response: Response<DumyList>) {
//
//              //response.body();
//
//              //Toast.makeText(this@WelcomeActivity,""+ response.body(),Toast.LENGTH_SHORT).show()
//
//          }
//      })



//        Toast.makeText(this,"Api rESPONSE $result.",Toast.LENGTH_SHORT).show()
//        print("DUMY GET Api Response >>>>>>>>>>>  "+result)
//        if(result!=null){
//            print("DUMY GET Api Response >>>>>>>>>>>  "+result)
//        }
        // Checking the results


        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~















        btn_next.setOnClickListener {



             val intent: Intent = Intent(this,PhoneNumberActivity::class.java)
              startActivity(intent)
             finish()
        }



    }


    //
    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        adapter.addFragment(Step1(), "Step1")
        adapter.addFragment(Step2(), "Step2")
        adapter.addFragment(Step3(), "Step3")

        // setting adapter to view pager.
        viewpager.setAdapter(adapter)
    }
}