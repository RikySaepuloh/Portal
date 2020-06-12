package com.saku.portalsatpam

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.util.DeviceProperties.isTablet
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    val MY_PERMISSION_REQUEST = 100
    val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(arePermissionsEnabled()){
//          permissions granted, continue flow normally
            }else{
                requestMultiplePermissions();
            }
        }
        login.setOnClickListener {
            vibrate(longArrayOf(0, 350))
//            val tabletSize = resources.getBoolean(R.bool.isTablet)
//            if (!tabletSize) {
//                // do something
//            } else {
//                Toast.makeText(this,"Gunakan Tab (Device) agar aplikasi dapat berjalan dengan baik",Toast.LENGTH_LONG).show()
//                // do something else
//            }
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestMultiplePermissions(){
        val remainingPermissions: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission)
            }
        }
        requestPermissions(remainingPermissions.toTypedArray(), MY_PERMISSION_REQUEST)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun arePermissionsEnabled(): Boolean {
        for (permission in permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSION_REQUEST) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(permissions[i]!!)) {
                        AlertDialog.Builder(this)
                            .setMessage("Aplikasi membutuhkan beberapa izin agar dapat berjalan dengan baik")
                            .setPositiveButton(
                                "Izinkan"
                            ) { _: DialogInterface?, _: Int -> requestMultiplePermissions() }
                            .setNegativeButton(
                                "Tidak"
                            ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                    return
                }
            }
            //all is good, continue flow
        }
    }
}
