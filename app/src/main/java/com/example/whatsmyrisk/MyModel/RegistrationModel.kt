package com.example.whatsmyrisk.MyModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class RegistrationModel (

    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("data"    ) var data    : UserDetaisl?    = UserDetaisl(),
    @SerializedName("message" ) var message : String?  = null

)

data class Media (

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

data class Pivot (

    @SerializedName("model_id"   ) var modelId   : Int?    = null,
    @SerializedName("role_id"    ) var roleId    : Int?    = null,
    @SerializedName("model_type" ) var modelType : String? = null

)


data class Roles (

    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("name"       ) var name      : String? = null,
    @SerializedName("guard_name" ) var guardName : String? = null,
    @SerializedName("title"      ) var title     : String? = null,
    @SerializedName("is_active"  ) var isActive  : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null,
    @SerializedName("pivot"      ) var pivot     : Pivot?  = Pivot()

)


data class UserDetaisl (

    @SerializedName("id"                      ) var id                    : Int?             = null,
    @SerializedName("first_name"              ) var firstName             : String?          = null,
    @SerializedName("last_name"               ) var lastName              : String?          = null,
    @SerializedName("email"                   ) var email                 : String?          = null,
    @SerializedName("mobile_number"           ) var mobileNumber          : String?          = null,
    @SerializedName("mobile_second"           ) var mobile_second          : String?          = null,
    @SerializedName("business_name"           ) var businessName          : String?          = null,
    @SerializedName("gst_number"              ) var gstNumber             : String?          = null,
    @SerializedName("country_id"              ) var countryId             : String?          = null,
    @SerializedName("state_id"                ) var stateId               : String?          = null,
    @SerializedName("city_id"                 ) var cityId                : String?          = null,
    @SerializedName("business_category_id"    ) var businessCategoryId    : String?          = null,
    @SerializedName("business_subcategory_id" ) var businessSubcategoryId : String?          = null,
    @SerializedName("business_type_id"        ) var businessTypeId        : String?          = null,
    @SerializedName("website"                 ) var website               : String?          = null,
    @SerializedName("email_verified_at"       ) var emailVerifiedAt       : String?          = null,
    @SerializedName("is_active"               ) var isActive              : Int?             = null,
    @SerializedName("otp"                     ) var otp                   : String?          = null,
    @SerializedName("created_at"              ) var createdAt             : String?          = null,
    @SerializedName("updated_at"              ) var updatedAt             : String?          = null,
    @SerializedName("deleted_at"              ) var deletedAt             : String?          = null,
    @SerializedName("country_name"            ) var countryName           : String?          = null,
    @SerializedName("state_name"              ) var stateName             : String?          = null,
    @SerializedName("city_name"               ) var cityName              : String?          = null,
    @SerializedName("category_name"           ) var categoryName          : String?          = null,
    @SerializedName("businesstype_name"       ) var businesstypeName      : String?          = null,
    @SerializedName("media"                   ) var media                 : ArrayList<Media> = arrayListOf(),
    @SerializedName("roles"                   ) var roles                 : ArrayList<Roles> = arrayListOf()

)