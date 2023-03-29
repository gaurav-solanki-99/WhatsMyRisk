package com.example.whatsmyrisk.MyModel
import com.google.gson.annotations.SerializedName


data class CountryList (
        @SerializedName("status"  ) var status  : Boolean?        = null,
        @SerializedName("data"    ) var data    : ArrayList<CountryModel> = arrayListOf(),
        @SerializedName("message" ) var message : String?         = null
)

data class CountryModel (
        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("name" ) var name : String? = null
)
//
//data class CountryList(val status:Boolean, val data:ArrayList<CountryModel>, val message:String)
//data class CountryModel(val id:Int,val name:String)