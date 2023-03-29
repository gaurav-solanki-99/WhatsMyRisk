package com.example.whatsmyrisk.MyConstants

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Constants {

    companion object {
        const val BASE_URL = "http://192.168.0.250/whatsmyrisk/api/"
        fun createPartFromString(partString: String): RequestBody {
            return RequestBody.create(MultipartBody.FORM, partString)
        }
        fun checkPermission(activity: FragmentActivity, listener: (Boolean) -> Unit) {
            Dexter.withActivity(activity).withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        listener(true)
                    } else if (report.isAnyPermissionPermanentlyDenied) {
                        listener(false)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener(null).check()
        }

        fun getConnectionStatus(context: Context): Boolean {
            val mNetworkInfoMobile: NetworkInfo?
            val mNetworkInfoWifi: NetworkInfo?
            val mConnectivityManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
            mNetworkInfoMobile = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            mNetworkInfoWifi = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            try {
                return if (mNetworkInfoMobile!!.isConnected || mNetworkInfoWifi!!.isConnected) {
                    true
                } else {
                    false
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return if (mNetworkInfoWifi!!.isConnected) {
                true
            } else {
                false
            }
        }


    }
}