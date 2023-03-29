package com.example.whatsmyrisk.RetrofitInstance

import com.example.whatsmyrisk.MyInterfaces.ApiInterface
import com.example.whatsmyrisk.MyModel.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.Header
import java.util.concurrent.TimeUnit

class GetRetrofitInstance private constructor()  {

    private val api: ApiInterface
  //  val baseUrl = "https://quotable.io/"
    val baseUrl = "https://laravel.cppatidar.com/"

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        api = retrofit.create(ApiInterface::class.java)
    }

  //1 Create Account
  fun createAccountApi(mobileNo:String) : Call<CeaterModel>{ return  api.createAccount(mobileNo) }

  //2 Verify Number
  fun verifyApi(mobileNo:String,otp:String) : Call<VerificationMaodel>{ return  api.verifyaccount(mobileNo,otp) }

  //3 Rgistration Form
  fun countryApi(token: String):Call<CountryList>{
        return api.getAllCountyr(token)
  }

    fun cutomerListApi(token: String):Call<CustomerList>{
        return api.getAllcustomerlist(token)
  }

    fun vendorListApi(token: String):Call<CustomerList>{
        return api.getAllvendorlist(token)
    }

  //4 Rgistration Form
  fun stateApi(token: String,country_id:Int):Call<CountryList>{
        return api.getAllSate(token,country_id)
  }

  //4 Rgistration Form
  fun cityApi(token: String,state_id:Int):Call<CountryList>{
        return api.getAllCity(token,state_id)
  }

  //5 Category Form
    fun categoryApi(token: String):Call<CountryList>{
        return api.getAllCategory(token)
    }

    //6 Buisness Form
    fun buisnessTypeApi(token: String):Call<CountryList>{
        return api.getAllBuisnessType(token)
    }

    fun btermConditionsApi(token: String):Call<TermsConditions>{
        return api.termconditions(token)
    }


    fun subcategoryApi(token: String,business_category_id:Int):Call<CountryList>{
        return api.getAllSubCategory(token,business_category_id)
    }

    //7 Registration Api
    fun registrationAccountApi(token:String,
                               name: RequestBody,
                               business_name:RequestBody,
                               business_category:RequestBody,
                               business_type:RequestBody,
                               gst_number:RequestBody,
                               country:RequestBody,
                               state:RequestBody,
                               city:RequestBody,
                               mobile_number:RequestBody,
                               email:RequestBody,
                               website:RequestBody,
                               mobile_number2:RequestBody,
                               file: MultipartBody.Part?) : Call<RegistrationModel>{
        return  api.registration_user(token,name, business_name, business_category, business_type, gst_number, country, state, city, mobile_number, email, website,mobile_number2, file) }

  fun importVendorApi(token: String,file: MultipartBody.Part?,types: RequestBody,): Call<StatusMessage>{  return api.importVendor(token,file,types)}


    //update Profile
    fun updateProfileApi(token:String,
                               name: RequestBody,
                               business_name:RequestBody,
                               business_category:RequestBody,
                               business_type:RequestBody,
                               gst_number:RequestBody,
                               country:RequestBody,
                               state:RequestBody,
                               city:RequestBody,
                               email:RequestBody,
                               website:RequestBody,
                               file: MultipartBody.Part?,
                               mobile_number:RequestBody,
                               mobile_second:RequestBody,) : Call<UpdateProfile>{ return  api.updateProfile_user(token,name, business_name, business_category, business_type, gst_number, country, state, city, email, website, file,mobile_number,mobile_second) }





    fun AddCustomerFeedbackApi(token:String,
                               name: String,
                               business_name:String,
                               business_category:Int,
                               business_sub__category:Int,
                               business_type:Int,
                               gst_number:String,
                               country:Int,
                               state:Int,
                               city:Int,
                               mobile_number:String,
                               email:String,
                               website:String,
                               rating: Int,
                               paymaster: Int,
                               rupees: String,
                               description: String,
                               feedback: String,
    mobile_number2: String) : Call<StatusMessage>{
        return  api.CustomerFeedbackApi(token,name, business_name, business_category, business_sub__category,business_type, gst_number, country, state, city, mobile_number, email, website, rating,paymaster,rupees,description,feedback,mobile_number2) }


    fun AddVendoreFeedbackApi(token:String,
                              name: String,
                              business_name:String,
                              business_category:Int,
                              business_sub__category:Int,
                              business_type:Int,
                              gst_number:String,
                              country:Int,
                              state:Int,
                              city:Int,
                              mobile_number:String,
                              email:String,
                              website:String,
                              rating: Int,
                              paymaster: Int,
                              rupees: String,
                              description: String,
                              feedback: String,
                              mobile_number2:String) : Call<StatusMessage>{ return  api.VendorFeedbackApi(token,name, business_name, business_category, business_sub__category,business_type, gst_number, country, state, city, mobile_number, email, website, rating,paymaster,rupees,description,feedback,mobile_number2) }


    fun UpdateVendoreFeedbackApi(token:String,
                              name: String,
                              business_name:String,
                              business_category:Int,
                              business_sub__category:Int,
                              business_type:Int,
                              gst_number:String,
                              country:Int,
                              state:Int,
                              city:Int,
                              mobile_number:String,
                              email:String,
                              website:String,
                              rating: Int,
                              paymaster: Int,
                              rupees: String,
                              description: String,
                              feedback: String,
                                 vendor_id:String,
                              mobile_number2:String) : Call<StatusMessage>{ return  api.UpdateVendorFeedbackApi(token,name, business_name, business_category, business_sub__category,business_type, gst_number, country, state, city, mobile_number, email, website, rating,paymaster,rupees,description,feedback,vendor_id,mobile_number2) }


    fun UpdateCustomerFeedbackApi(token:String,
                                 name: String,
                                 business_name:String,
                                 business_category:Int,
                                 business_sub__category:Int,
                                 business_type:Int,
                                 gst_number:String,
                                 country:Int,
                                 state:Int,
                                 city:Int,
                                 mobile_number:String,
                                 email:String,
                                 website:String,
                                 rating: Int,
                                 paymaster: Int,
                                 rupees: String,
                                 description: String,
                                 feedback: String,
                                  customer_id:String,
                                 mobile_number2:String) : Call<StatusMessage>{ return  api.UpdateCutomerFeedbackApi(token,name, business_name, business_category, business_sub__category,business_type, gst_number, country, state, city, mobile_number, email, website, rating,paymaster,rupees,description,feedback,customer_id,mobile_number2) }



    fun BuisnessSearchApi(
       token:String,
       business_category_id: Int,
       gst_number:String,
       business_name:String,
      mobile_number:String,
      country_id:Int,
      state_id:Int,
      city_id:Int
    ):Call<BuisnessList>{
         return api.BuisnessSearch(token,business_category_id,gst_number,business_name,mobile_number,country_id,state_id,city_id)

    }


    //4 Rgistration Form
    fun contactUS(token: String,title:String,messages:String):Call<StatusMessage>{
        return api.getContactUS(token,title,messages)
    }


    private fun simulateDelay() {
        try {
            Thread.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    companion object {
        private var projectRepository: GetRetrofitInstance? = null

        val instance: GetRetrofitInstance
            @Synchronized get() {
                if (projectRepository == null) {
                    if (projectRepository == null) {
                        projectRepository = GetRetrofitInstance()
                    }
                }
                return projectRepository!!
            }
    }


}