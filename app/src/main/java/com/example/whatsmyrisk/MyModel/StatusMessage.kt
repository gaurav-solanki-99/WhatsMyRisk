package com.example.whatsmyrisk.MyModel


import com.google.gson.annotations.SerializedName


data class StatusMessage (

        @SerializedName("status"  ) var status  : Boolean? = null,
        @SerializedName("message" ) var message : String?  = null

)

