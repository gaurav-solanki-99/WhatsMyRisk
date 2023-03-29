package com.example.whatsmyrisk.MyModel




import com.google.gson.annotations.SerializedName


data class TermsConditions (

    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf(),
    @SerializedName("message" ) var message : String?         = null

)


data class Data (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("option_name"  ) var optionName  : String? = null,
    @SerializedName("option_value" ) var optionValue : String? = null,
    @SerializedName("created_at"   ) var createdAt   : String? = null,
    @SerializedName("updated_at"   ) var updatedAt   : String? = null

)