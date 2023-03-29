package com.example.whatsmyrisk.MyModel
import com.google.gson.annotations.SerializedName

data class CeaterModel(
    val status:Boolean,
    val user_status:String,
    val message:String,
    val otp:String,
    val access_token:String,
    val token_type:String,
    val expires_at:String,
    )




