package com.example.whatsmyrisk.PREF


import android.content.Context
import android.content.SharedPreferences

class PrefManager(var _context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0
    var isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }

    companion object {
        private const val PREF_NAME = "androidhive-welcome"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"




        const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
        const val STAGE = "STAGE"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val USER_ID = "USER_ID"
        const val TOKEN_TYPE = "TOKEN_TYPE"
        const val FIREBASE_TOKEN = "TOKEN_TYPE"
        const val MOBILE_NUMBER = "MOBILE_NUMBER"
        const val OTP = "OTP"
        const val first_name = "first_name"

        const val last_name = "last_name"
        const val email = "email"
        const val mobile_number = "mobile_number"
        const val mobile_number2 = "mobile_number2"
        const val business_name = "business_name"
        const val business_category = "business_category"
        const val gst_number = "gst_number"
        const val isRegisterd = "isRegisterd"
        const val country_id = "country_id"
        const val state_id = "state_id"
        const val city_id = "city_id"
        const val business_category_id = "business_category_id"
        const val business_subcategory_id = "business_subcategory_id"
        const val business_type_id = "business_type_id"
        const val website = "website"
        const val country_name = "country_name"
        const val state_name = "state_name"
        const val city_name = "city_name"
        const val category_name = "category_name"
        const val businesstype_name = "businesstype_name"










        const val NOTIFICATION_GIVEAWAY_ID = "NOTIFICATION_GIVEAWAY_ID"
        fun getEditor(context: Context?): SharedPreferences.Editor {
            val sharedpreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedpreferences.edit()
        }

        fun getSharedPreference(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        fun getString(context: Context, name: String?): String? {
            val sharedPreferences = getSharedPreference(context)
            return sharedPreferences.getString(name, "")
        }

        fun setString(context: Context?, name: String?, value: String?) {
            val editor = getEditor(context)
            editor.putString(name, value)
            editor.commit()
        }
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}