package com.example.whatsmyrisk

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.whatsmyrisk.MyModel.UserDetaisl
import com.example.whatsmyrisk.PageFragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable


class DashboardActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_layout)
        supportActionBar?.hide()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)


        val navigationView: BottomNavigationView = findViewById<BottomNavigationView>(R.id.navigationView)

     // var userDetails:UserDetaisl =  intent.getSerializableExtra("userDetails") as UserDetaisl
       // Log.e("userDetails  ", "DashboardActivity >> $userDetails")



        loadFragment(HomeFragment())

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {

                    loadFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }



                R.id.navigation_account -> {

                    loadFragment(AccountFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_report -> {


                    loadFragment(ProfileFragment())
                   // loadFragment(ReportFragment())
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment


        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed()
    {

        val builder = AlertDialog.Builder(this)
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
}