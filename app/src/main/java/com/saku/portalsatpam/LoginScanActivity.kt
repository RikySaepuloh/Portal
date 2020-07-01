package com.saku.portalsatpam

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.saku.portalsatpam.apihelper.LoginResponse
import com.saku.portalsatpam.apihelper.UtilsApi
import kotlinx.android.synthetic.main.activity_login_scan.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginScanActivity : AppCompatActivity() {
    var preferences  = Preferences()
    var params = ""
    private lateinit var codeScanner: CodeScanner

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_scan)
        preferences.setPreferences(this@LoginScanActivity)
        overridePendingTransition(R.anim.zoom_enter,R.anim.fade_out);
        try {
            params = intent.getStringExtra("params")
        } catch (e: Exception) {
        }
        val window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor = Color.TRANSPARENT

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
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                if(params=="login"){
                    login(it.text)
                }else{
                    val idTamu = intent.getStringExtra("id_tamu")
                    tamuKeluar(it.text,idTamu)
                }
            }

//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//            finishAffinity()
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        codeScanner.startPreview()

//        login.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
////            val tabletSize = resources.getBoolean(R.bool.isTablet)
////            if (!tabletSize) {
////                // do something
////            } else {
////                Toast.makeText(this,"Gunakan Tab (Device) agar aplikasi dapat berjalan dengan baik",Toast.LENGTH_LONG).show()
////                // do something else
////            }
//            val intent = Intent(this@LoginScanActivity,MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    private fun tamuKeluar(qrcode:String,id_tamu:String){
        val utilsapi= UtilsApi().getAPIService(this)
        utilsapi?.tamuKeluarSatpam(qrcode,id_tamu)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val obj = JSONObject(response.body()!!.string())
                        Toast.makeText(this@LoginScanActivity, obj.optString("message"), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginScanActivity, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
//                        preferences.saveToken(response.body()!!.token.toString()) // Fungsi untuk menyimpan token menggunakan preferences
//                        preferences.saveExpires(response.body()!!.expires_in.toString())
//                        preferences.saveTokenType(response.body()!!.token_type.toString())
//                        preferences.saveLogStatus(true) // Fungsi untuk menyimpan parameter bahwa user sedang login dan aktif

//                        val intent = Intent(this@LoginScanActivity, MainActivity::class.java)
//                        startActivity(intent)
//                        finishAffinity()
                    }else{
                        Toast.makeText(this@LoginScanActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@LoginScanActivity, "Username/Password masih kosong", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    Toast.makeText(this@LoginScanActivity, "Username/Password salah", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@LoginScanActivity, "Token Invalid", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404 || response.code() == 405){
                    Toast.makeText(this@LoginScanActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@LoginScanActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun login(qrcode:String){
        val utilsapi= UtilsApi().getAPIService(this)
        utilsapi?.login(qrcode)?.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {

                        preferences.saveToken(response.body()!!.token.toString()) // Fungsi untuk menyimpan token menggunakan preferences
                        preferences.saveExpires(response.body()!!.expires_in.toString())
                        preferences.saveTokenType(response.body()!!.token_type.toString())
                        preferences.saveLogStatus(true) // Fungsi untuk menyimpan parameter bahwa user sedang login dan aktif

                        val intent = Intent(this@LoginScanActivity, MainActivity::class.java)
                        startActivity(intent)
//                        overridePendingTransition(R.anim.zoom_enter,0);
                        finishAffinity()
                    }else{
                        Toast.makeText(this@LoginScanActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@LoginScanActivity, "Username/Password masih kosong", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    Toast.makeText(this@LoginScanActivity, "Username/Password salah", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@LoginScanActivity, "Token Invalid", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404 || response.code() == 405){
                    Toast.makeText(this@LoginScanActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Toast.makeText(this@LoginScanActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
