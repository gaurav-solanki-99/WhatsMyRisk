package com.example.whatsmyrisk.MyModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CustomerList (

        @SerializedName("status"  ) var status  : Boolean?        = null,
        @SerializedName("data"    ) var data    : ArrayList<Customers> = arrayListOf(),
        @SerializedName("message" ) var message : String?         = null

)



data class Customers (

        @SerializedName("id"                      ) var id                    : Int?    = null,
        @SerializedName("name"                    ) var name                  : String? = null,
        @SerializedName("business_name"           ) var businessName          : String? = null,
        @SerializedName("user_id"                 ) var userId                : Int?    = null,
        @SerializedName("business_category_id"    ) var businessCategoryId    : Int?    = null,
        @SerializedName("business_subcategory_id" ) var businessSubcategoryId : Int?    = null,
        @SerializedName("business_type_id"        ) var businessTypeId        : Int?    = null,
        @SerializedName("country_id"              ) var countryId             : Int?    = null,
        @SerializedName("state_id"                ) var stateId               : Int?    = null,
        @SerializedName("city_id"                 ) var cityId                : Int?    = null,
        @SerializedName("gst_number"              ) var gstNumber             : String? = null,
        @SerializedName("mobile_number"           ) var mobileNumber          : String? = null,
        @SerializedName("additional_number"        ) var mobileNumber2          : String? = null,
        @SerializedName("email"                   ) var email                 : String? = null,
        @SerializedName("website"                 ) var website               : String? = null,
        @SerializedName("rating"                  ) var rating                : String? = null,
        @SerializedName("paymaster"               ) var paymaster             : Int?    = null,
        @SerializedName("rupees"                  ) var rupees                : String? = null,
        @SerializedName("description"             ) var description           : String? = null,
        @SerializedName("feedback"                ) var feedback              : String? = null,
        @SerializedName("is_active"               ) var isActive              : Int?    = null,
        @SerializedName("type"                    ) var type                  : Int?    = null,
        @SerializedName("created_at"              ) var createdAt             : String? = null,
        @SerializedName("updated_at"              ) var updatedAt             : String? = null

):Serializable