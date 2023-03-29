package com.example.whatsmyrisk.MyModel

import com.google.gson.annotations.SerializedName


data class UpdateProfile (

    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("user"    ) var user    : UpdateUser?    = UpdateUser(),
    @SerializedName("message" ) var message : String?  = null

)


data class Media2 (

        @SerializedName("id"                ) var id               : Int?              = null,
        @SerializedName("model_type"        ) var modelType        : String?           = null,
        @SerializedName("model_id"          ) var modelId          : Int?              = null,
        @SerializedName("uuid"              ) var uuid             : String?           = null,
        @SerializedName("collection_name"   ) var collectionName   : String?           = null,
        @SerializedName("name"              ) var name             : String?           = null,
        @SerializedName("file_name"         ) var fileName         : String?           = null,
        @SerializedName("mime_type"         ) var mimeType         : String?           = null,
        @SerializedName("disk"              ) var disk             : String?           = null,
        @SerializedName("conversions_disk"  ) var conversionsDisk  : String?           = null,
        @SerializedName("size"              ) var size             : Int?              = null,
        @SerializedName("manipulations"     ) var manipulations    : ArrayList<String> = arrayListOf(),
        @SerializedName("custom_properties" ) var customProperties : ArrayList<String> = arrayListOf(),
        @SerializedName("responsive_images" ) var responsiveImages : ArrayList<String> = arrayListOf(),
        @SerializedName("order_column"      ) var orderColumn      : Int?              = null,
        @SerializedName("created_at"        ) var createdAt        : String?           = null,
        @SerializedName("updated_at"        ) var updatedAt        : String?           = null

)

data class UpdateUser (

    @SerializedName("id"                      ) var id                    : Int?              = null,
    @SerializedName("first_name"              ) var firstName             : String?           = null,
    @SerializedName("last_name"               ) var lastName              : String?           = null,
    @SerializedName("email"                   ) var email                 : String?           = null,
    @SerializedName("mobile_number"           ) var mobileNumber          : String?           = null,
    @SerializedName("mobile_second"           ) var mobileSecond          : String?           = null,
    @SerializedName("business_name"           ) var businessName          : String?           = null,
    @SerializedName("gst_number"              ) var gstNumber             : String?           = null,
    @SerializedName("country_id"              ) var countryId             : Int?              = null,
    @SerializedName("state_id"                ) var stateId               : Int?              = null,
    @SerializedName("city_id"                 ) var cityId                : Int?              = null,
    @SerializedName("business_category_id"    ) var businessCategoryId    : Int?              = null,
    @SerializedName("business_subcategory_id" ) var businessSubcategoryId : String?           = null,
    @SerializedName("business_type_id"        ) var businessTypeId        : Int?              = null,
    @SerializedName("website"                 ) var website               : String?           = null,
    @SerializedName("otp"                     ) var otp                   : String?           = null,
    @SerializedName("created_at"              ) var createdAt             : String?           = null,
    @SerializedName("updated_at"              ) var updatedAt             : String?           = null,
    @SerializedName("country_name"            ) var country_name             : String?           = null,
    @SerializedName("state_name"              ) var state_name             : String?           = null,
    @SerializedName("city_name"               ) var city_name             : String?           = null,
    @SerializedName("category_name"           ) var category_name             : String?           = null,
    @SerializedName("businesstype_name"       ) var businesstype_name             : String?           = null,
    @SerializedName("profile_picture"         ) var profilePicture        : String?           = null,
    @SerializedName("media"                   ) var media                 : ArrayList<Media2> = arrayListOf()


)