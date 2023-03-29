package com.example.whatsmyrisk.MyModel


import com.google.gson.annotations.SerializedName


data class BuisnessList (

    @SerializedName("status"  ) var status  : Boolean?        = null,
    @SerializedName("data"    ) var data    : ArrayList<Buisness> = arrayListOf(),
    @SerializedName("message" ) var message : String?         = null

)

data class Buisness (

    @SerializedName("id"                      ) var id                    : Int?    = null,
    @SerializedName("name"                    ) var name                  : String? = null,
    @SerializedName("business_name"           ) var businessName          : String? = null,
    @SerializedName("user_id"                 ) var userId                : Int?    = null,
    @SerializedName("business_category_id"    ) var businessCategoryId    : String? = null,
    @SerializedName("business_subcategory_id" ) var businessSubcategoryId : String? = null,
    @SerializedName("business_type_id"        ) var businessTypeId        : String? = null,
    @SerializedName("country_id"              ) var countryId             : String? = null,
    @SerializedName("state_id"                ) var stateId               : String? = null,
    @SerializedName("city_id"                 ) var cityId                : String? = null,
    @SerializedName("gst_number"              ) var gstNumber             : String? = null,
    @SerializedName("mobile_number"           ) var mobileNumber          : String? = null,
    @SerializedName("email"                   ) var email                 : String? = null,
    @SerializedName("website"                 ) var website               : String? = null,
    @SerializedName("rating"                  ) var rating                : String? = null,
    @SerializedName("paymaster"               ) var paymaster             : Int?    = null,
    @SerializedName("rupees"                  ) var rupees                : String? = null,
    @SerializedName("description"             ) var description           : String? = null,
    @SerializedName("feedback"                ) var feedback              : String? = null,
    @SerializedName("type"                    ) var type                  : Int?    = null

)