package com.example.whatsmyrisk.MyInterfaces

import com.example.whatsmyrisk.MyModel.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface
{

    @FormUrlEncoded
    @POST("whatsmyrisk/api/auth/createAccount")
    fun createAccount(@Field("mobile_number")  password:String) : Call<CeaterModel>

    @FormUrlEncoded
    @POST("whatsmyrisk/api/auth/otpVerify")
    fun verifyaccount(
            //@Header("Authorization")token:String,
                      @Field("mobile_number")  mobilenumber:String,
                      @Field("otp")  otp:String ) : Call<VerificationMaodel>


    @GET("whatsmyrisk/api/customerlist")
    fun getAllcustomerlist(@Header("Authorization")token:String) : Call<CustomerList>




    @GET("whatsmyrisk/api/vendorlist")
    fun getAllvendorlist(@Header("Authorization")token:String) : Call<CustomerList>

    @POST("whatsmyrisk/api/country")
    fun getAllCountyr(@Header("Authorization")token:String) : Call<CountryList>


    @GET("whatsmyrisk/api/category")
    fun getAllCategory(@Header("Authorization")token:String) : Call<CountryList>

    @POST("whatsmyrisk/api/subcategorylist")
    @FormUrlEncoded
    fun getAllSubCategory(@Header("Authorization")token:String,
                          @Field("business_category_id")  business_category_id:Int) : Call<CountryList>

    @GET("whatsmyrisk/api/businesstype")
    fun getAllBuisnessType(@Header("Authorization")token:String) : Call<CountryList>


    @POST("whatsmyrisk/api/state")
    @FormUrlEncoded
    fun getAllSate(@Header("Authorization")token:String,
                   @Field("country_id")  country_id:Int) : Call<CountryList>


    @POST("whatsmyrisk/api/city")
    @FormUrlEncoded
    fun getAllCity(@Header("Authorization")token:String,
                   @Field("state_id")  country_id:Int) : Call<CountryList>



    @POST("whatsmyrisk/api/contactus")
    @FormUrlEncoded
    fun getContactUS(@Header("Authorization")token:String,
                   @Field("title")  title:String,
                     @Field("description")  description:String) : Call<StatusMessage>



    @Multipart
    @POST("whatsmyrisk/api/register")
    fun registration_user(@Header("Authorization")token:String,
                          @Part("name")  name: RequestBody,
                          @Part("business_name")  business_name:RequestBody,
                          @Part("business_category")  business_category:RequestBody,
                          @Part("business_type")  business_type:RequestBody,
                          @Part("gst_number")  gst_number:RequestBody,
                          @Part("country")  country:RequestBody,
                          @Part("state")  state:RequestBody,
                          @Part("city")  city:RequestBody,
                          @Part("mobile_number")  mobile_number:RequestBody,
                          @Part("email")  email:RequestBody,
                          @Part("website")  website:RequestBody,
                          @Part("mobile_second")  mobile_second:RequestBody,
                          @Part file : MultipartBody.Part?,
                          ) : Call<RegistrationModel>


    @Multipart
    @POST("whatsmyrisk/api/importVendor")
    fun importVendor(@Header("Authorization")token:String,
                     @Part file : MultipartBody.Part?,@Part("types") types: RequestBody, ) : Call<StatusMessage>



    @Multipart
    @POST("whatsmyrisk/api/updateProfile")
    fun updateProfile_user(
        @Header("Authorization")token:String,
                          @Part("name")  name: RequestBody,
                          @Part("business_name")  business_name:RequestBody,
                          @Part("business_category")  business_category:RequestBody,
                          @Part("business_type")  business_type:RequestBody,
                          @Part("gst_number")  gst_number:RequestBody,
                          @Part("country")  country:RequestBody,
                          @Part("state")  state:RequestBody,
                          @Part("city")  city:RequestBody,
                          @Part("email")  email:RequestBody,
                          @Part("website")  website:RequestBody,
                          @Part file : MultipartBody.Part?,
                           @Part("mobile_number")  mobile_number:RequestBody,
                           @Part("mobile_second")  mobile_second:RequestBody,
    ) : Call<UpdateProfile>



    @POST("whatsmyrisk/api/customerfeedback")
    @FormUrlEncoded
    fun CustomerFeedbackApi(@Header("Authorization")token:String,
                          @Field("name") name: String,
                          @Field("business_name")  businessname:String,
                          @Field("business_category")  businesscategory:Int,
                          @Field("business_subcategory")  business_subcategory:Int,
                          @Field("business_type")  businesstype:Int,
                          @Field("gst_number")  gstnumber:String,
                          @Field("country")  country:Int,
                          @Field("state")  state:Int,
                          @Field("city")  city:Int,
                          @Field("mobile_number")  mobilenumber:String,
                          @Field("email")  email:String,
                          @Field("website")  website:String,
                          @Field("rating")  rating:Int,
                          @Field("paymaster")  paymaster:Int,
                          @Field("rupees")  rupees:String,
                          @Field("description")  description:String,
                          @Field("feedback")  feedback:String,
                          @Field("additional_number")  mobile_number2: String,
    ) : Call<StatusMessage>




    @POST("whatsmyrisk/api/vendorfeedback")
    @FormUrlEncoded
    fun VendorFeedbackApi(@Header("Authorization")token:String,
                            @Field("name") name: String,
                            @Field("business_name")  businessname:String,
                            @Field("business_category")  businesscategory:Int,
                            @Field("business_subcategory")  business_subcategory:Int,
                            @Field("business_type")  businesstype:Int,
                            @Field("gst_number")  gstnumber:String,
                            @Field("country")  country:Int,
                            @Field("state")  state:Int,
                            @Field("city")  city:Int,
                            @Field("mobile_number")  mobilenumber:String,
                            @Field("email")  email:String,
                            @Field("website")  website:String,
                            @Field("rating")  rating:Int,
                            @Field("paymaster")  paymaster:Int,
                            @Field("rupees")  rupees:String,
                            @Field("description")  description:String,
                            @Field("feedback")  feedback:String,
                            @Field("additional_number")  mobile_number2: String,
    ) : Call<StatusMessage>





    @POST("whatsmyrisk/api/vendorFeedbackupdate")
    @FormUrlEncoded
    fun UpdateVendorFeedbackApi(@Header("Authorization")token:String,
                          @Field("name") name: String,
                          @Field("business_name")  businessname:String,
                          @Field("business_category")  businesscategory:Int,
                          @Field("business_subcategory")  business_subcategory:Int,
                          @Field("business_type")  businesstype:Int,
                          @Field("gst_number")  gstnumber:String,
                          @Field("country")  country:Int,
                          @Field("state")  state:Int,
                          @Field("city")  city:Int,
                          @Field("mobile_number")  mobilenumber:String,
                          @Field("email")  email:String,
                          @Field("website")  website:String,
                          @Field("rating")  rating:Int,
                          @Field("paymaster")  paymaster:Int,
                          @Field("rupees")  rupees:String,
                          @Field("description")  description:String,
                          @Field("feedback")  feedback:String,
                          @Field("vendor_id")  vendor_id:String,
                          @Field("additional_number")  mobile_number2: String,
    ) : Call<StatusMessage>


    @POST("whatsmyrisk/api/customerFeedbackupdate")
    @FormUrlEncoded
    fun UpdateCutomerFeedbackApi(@Header("Authorization")token:String,
                                @Field("name") name: String,
                                @Field("business_name")  businessname:String,
                                @Field("business_category")  businesscategory:Int,
                                @Field("business_subcategory")  business_subcategory:Int,
                                @Field("business_type")  businesstype:Int,
                                @Field("gst_number")  gstnumber:String,
                                @Field("country")  country:Int,
                                @Field("state")  state:Int,
                                @Field("city")  city:Int,
                                @Field("mobile_number")  mobilenumber:String,
                                @Field("email")  email:String,
                                @Field("website")  website:String,
                                @Field("rating")  rating:Int,
                                @Field("paymaster")  paymaster:Int,
                                @Field("rupees")  rupees:String,
                                @Field("description")  description:String,
                                @Field("feedback")  feedback:String,
                                @Field("customer_id")  vendor_id:String,
                                @Field("additional_number")  mobile_number2: String,
    ) : Call<StatusMessage>




    @POST("whatsmyrisk/api/businesssearch")
    @FormUrlEncoded
    fun BuisnessSearch(@Header("Authorization")token:String,
                            @Field("business_category_id") business_category_id: Int,
                            @Field("gst_number")  gst_number:String,
                            @Field("business_name")  business_name:String,
                            @Field("mobile_number")  mobile_number:String,
                            @Field("country_id")  country_id:Int,
                            @Field("state_id")  state_id:Int,
                            @Field("city_id")  city_id:Int
                           ) : Call<BuisnessList>


    @GET("whatsmyrisk/api/termconditions")
    fun termconditions(@Header("Authorization")token:String) : Call<TermsConditions>


  /*  @POST("whatsmyrisk/api/vendorfeedback")
    @FormUrlEncoded
    fun VendorFeedbackApi(@Header("Authorization")token:String,
                            @Field("name")  name: String,
                            @Field("business_name")  business_name:String,
                            @Field("business_category")  business_category:String,
                            @Field("business_type")  business_type:String,
                            @Field("gst_number")  gst_number:String,
                            @Field("country")  country:String,
                            @Field("state")  state:String,
                            @Field("city")  city:String,
                            @Field("mobile_number")  mobile_number:String,
                            @Field("email")  email:String,
                            @Field("website")  website:String,
                            @Field("rating")  rating:String,
                            @Field("paymaster")  paymaster:String,
                            @Field("rupees")  rupees:String,
                            @Field("description")  description:String,
                            @Field("feedback")  feedback:String,
    ) : Call<FeedBackModel>*/

}