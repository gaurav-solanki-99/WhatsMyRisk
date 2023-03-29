package com.example.whatsmyrisk

import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsmyrisk.MyConstants.Constants
import com.example.whatsmyrisk.MyModel.StatusMessage
import com.example.whatsmyrisk.PREF.PrefManager
import com.example.whatsmyrisk.RetrofitInstance.GetRetrofitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class AddVendorActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_vender_layout)
        supportActionBar?.hide()
        findViewById<TextView>(R.id.aap_heading).setText("Add Vendor")

        findViewById<ImageView>(R.id.btn_back_bar).setOnClickListener(){
            print("Back Methode Call")
            onBackPressed()
        }
        findViewById<TextView>(R.id.btn_download_template).setOnClickListener(){
            val url = "https://laravel.cppatidar.com/whatsmyrisk/public/exceldownload/file_example_XLS_10.xls"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }



        findViewById<TextView>(R.id.btn_next_customer).setOnClickListener()
        {
            val intent = Intent(this,AddFeedBackActivity()::class.java)
            intent.putExtra("AppTile"," Vendor")
            startActivity(intent)
        }

        findViewById<TextView>(R.id.btn_upload_vendor).setOnClickListener(View.OnClickListener {
           // Toast.makeText(this,"Upload click",Toast.LENGTH_LONG).show()

            if (!Constants.getConnectionStatus(this)) {
                Toast.makeText(this, "Please Check Internet Connection ", Toast.LENGTH_SHORT).show()
            } else
            {
            val mimetypes = arrayOf(
                    "application/vnd.ms-excel",  // .xls
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx
            )
            intent = Intent(Intent.ACTION_GET_CONTENT); // or use ACTION_OPEN_DOCUMENT
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            //val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
            //pdfIntent.type = "application/"
            //pdfIntent.type = "application/vnd.ms-excel"
            // pdfIntent.type = "application/pdf"
            //pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, 12)
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {


                print("File Upload Data >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + data?.data)
                var pdfUri = data?.data!!
                /*  if (pdfUri != null) {
                      pdfUri?.let { uri ->
                          currentPhotoPath =
                              getFilePathFromUri(this, uri,false)!!
                      }
                  }*/
              //  print("File Upload Data >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + currentPhotoPath)

                val uri: Uri = data?.data!!
                val uriString: String = uri.toString()
                Log.e("TAG", "onActivityResult: "+uriString )
                Log.e("TAG", "onActivityResult: "+getFilePathFromUri(this,pdfUri,true) )

                /* var pdfName: String? = null

                 val cursor = contentResolver.query(uri, null, null, null, null)
                 val nameIndex: Int = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                 cursor!!.moveToFirst()
                 // nameView.setText(cursor!!.getString(nameIndex))


                 var newUri = pdfUri.toString().replace("7156", cursor!!.getString(nameIndex))
                 //newUri=Uri.fromFile(File(newUri)).toString()


                 Log.e(
                     "PDF", ">>>>>>>>>>>>>>>>>>>>>>>>" + newUri + "  NAME " + cursor!!.getString(
                         nameIndex
                     )
                 )*/

                // var multipartprofile: MultipartBody.Part? = null
                // nca_certificate
                // if (newUri.isNotEmpty()) {
                //  val file = File(newUri)
//                    val `is`: InputStream = FileInputStream(file)
//                    val reqFile = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
                //val reqFile = file.asRequestBody("application/*".toMediaTypeOrNull())

                //multipartprofile = MultipartBody.Part.createFormData("excel", file.name, reqFile)
                //   }

                var newuri= Uri.fromFile(File(getFilePathFromUri(this,pdfUri,true)))
                Log.e("TAG", "onActivityResult: "+newuri )
                val progressDialog2 = ProgressDialog(this)
                progressDialog2.setMessage("Uploading")
                progressDialog2.show()

                uploadCustomer(newuri.path.toString())


            }
        }
    }

    private fun uploadCustomer(newUri: String) {

        var tokentype = PrefManager.getString(this, PrefManager.TOKEN_TYPE).toString()
        var token = PrefManager.getString(this, PrefManager.ACCESS_TOKEN).toString()
        Log.e("getAllBuisnessTypeList", "Methode Call : " + tokentype + " " + token)
        var multipartBody: MultipartBody.Part? = null
        if (newUri?.isNotEmpty()!!) {
            val file = File(newUri)
            val reqFile = file.asRequestBody("*/*".toMediaTypeOrNull())
            multipartBody = MultipartBody.Part.createFormData("excel", file.name, reqFile)

            val types: RequestBody = Constants.createPartFromString("1")
            GetRetrofitInstance.instance.importVendorApi(tokentype + " " + token, multipartBody,types)
                    .enqueue(
                            object : retrofit2.Callback<StatusMessage> {
                                override fun onResponse(call: Call<StatusMessage>, response: Response<StatusMessage>) {

                                    if(response.code()==200)
                                    {
                                        Toast.makeText(this@AddVendorActivity, "" + response.body()!!.message, Toast.LENGTH_LONG).show()
                                        val intent = Intent(this@AddVendorActivity,DashboardActivity ::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this@AddVendorActivity, "Please try again", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<StatusMessage>, t: Throwable) {
                                    Log.e("ErrorPDF", t.message.toString())
                                    Toast.makeText(
                                            this@AddVendorActivity,
                                            "Error  " + t.message,
                                            Toast.LENGTH_SHORT
                                    ).show()
                                }

                            })

        }


    }
    fun getFilePathFromUri(context: Context, uri: Uri, uniqueName: Boolean): String =
            if (uri.path?.contains("file://") == true) uri.path!!
            else getFileFromContentUri(context, uri, uniqueName).path
    private fun getFileFromContentUri(context: Context, contentUri: Uri, uniqueName: Boolean): File {
        // Preparing Temp file name
        val fileExtension = getFileExtension(context, contentUri) ?: ""
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = ("temp_file_" + if (uniqueName) timeStamp else "") + ".$fileExtension"
        // Creating Temp file
        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()
        // Initialize streams
        var oStream: FileOutputStream? = null
        var inputStream: InputStream? = null

        try {
            oStream = FileOutputStream(tempFile)
            inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let { copy(inputStream, oStream) }
            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Close streams
            inputStream?.close()
            oStream?.close()
        }

        return tempFile
    }
    private fun getFileExtension(context: Context, uri: Uri): String? =
            if (uri.scheme == ContentResolver.SCHEME_CONTENT)
                MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
            else uri.path?.let { MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(it)).toString()) }
    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }
}