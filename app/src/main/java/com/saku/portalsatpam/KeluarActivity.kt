package com.saku.portalsatpam

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.budiyev.android.codescanner.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.apihelper.UtilsApi
import kotlinx.android.synthetic.main.activity_keluar.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class KeluarActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    var preferences  = Preferences()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keluar)
        preferences.setPreferences(this)
        input.setOnClickListener {
            val intent = Intent(this@KeluarActivity,InputActivity::class.java)
            startActivity(intent)
        }
//        setSupportActionBar(toolbar)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.statusBarColor = Color.TRANSPARENT
//        }
        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            super.onBackPressed() }
//        simpan.setOnClickListener {
//            val intent = Intent(this,SelesaiActivity::class.java)
//            startActivity(intent)
//        }

        codeScanner = CodeScanner(this, scanner_view)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not
        codeScanner.isTouchFocusEnabled = true

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                initData(it.text)
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

//        scanner_view.setOnClickListener {
            codeScanner.startPreview()
//        }

        
        
    }

    private fun initData(qrcode :String) {
        val apiservice = UtilsApi().getAPIService(this@KeluarActivity)
        apiservice?.tamuKeluar(qrcode)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val message = obj.optString("message")
                            val noUrut = obj.optString("no_urut")
                            Toast.makeText(this@KeluarActivity, message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@KeluarActivity,SelesaiKeluarActivity::class.java)
                            intent.putExtra("no_urut",noUrut)
                            startActivity(intent)
                        } catch (e: Exception) {
                        }
                    }else{
                        Toast.makeText(this@KeluarActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@KeluarActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(this@KeluarActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@KeluarActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@KeluarActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(this@KeluarActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@KeluarActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
