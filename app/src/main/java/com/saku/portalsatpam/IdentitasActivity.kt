package com.saku.portalsatpam

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_identitas.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class IdentitasActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identitas)
        val window = window
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.statusBarColor = Color.TRANSPARENT
        camera.setLifecycleOwner(this);
        back.setOnClickListener {
            vibrate(longArrayOf(0, 350))
            super.onBackPressed() }
//        camera.onStart()
//        simpan.setOnClickListener {
//            val intent = Intent(this,DilarangActivity::class.java)
//            startActivity(intent)
//        }

        camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                // Picture was taken!
                // If planning to show a Bitmap, we will take care of
                // EXIF rotation and background threading for you...
//                result.toBitmap(200, 200, callback)

                // If planning to save a file on a background thread,
                // just use toFile. Ensure you have permissions.
//                result.toFile(file, callback)

                // Access the raw data if needed.
                val data = result.data
                val filepath = getExternalFilesDir("/Portal Satpam/")
//                val dir =
//                    File(filepath.absolutePath.toString() + "/Portal Satpam/")
//                dir.mkdir()
                val file =
                    File(filepath, System.currentTimeMillis().toString() + ".jpg")
                try {
                    val outputStream =
                        FileOutputStream(file)
                    outputStream.write(data)
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val intent = Intent(this@IdentitasActivity,SelesaiMasukActivity::class.java)
                intent.putExtra("filepath",file.path)
                startActivity(intent)
//                Toast.makeText(this@IdentitasActivity,"YE berhasil",Toast.LENGTH_LONG).show()
            }
        })

        simpan.setOnClickListener {
            vibrate(longArrayOf(0, 350))
            camera.takePicture()
//            camera.captureImage { camera, capturedImage ->
//                val filepath = Environment.getExternalStorageDirectory()
//                val dir =
//                    File(filepath.absolutePath.toString() + "/Portal Satpam/")
//                dir.mkdir()
//                val file =
//                    File(dir, System.currentTimeMillis().toString() + ".jpg")
//                try {
//                    val outputStream =
//                        FileOutputStream(file)
//                    outputStream.write(capturedImage)
//                    outputStream.close()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//                val intent = Intent(this,SelesaiMasukActivity::class.java)
//                intent.putExtra("filepath",file.path)
//                startActivity(intent)
////                Toast.makeText(this, file.path,Toast.LENGTH_LONG).show()
//            }
        }

    }
//
//    override fun onResume() {
//        super.onResume()
//        camera.onStart()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        camera.onStop()
//    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        camera.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }

    //
//    override fun onResume() {
//        super.onResume()
//        camera.onResume()
//    }
//
//    override fun onPause() {
//        camera.onPause()
//        super.onPause()
//    }
//
//    override fun onStop() {
//        camera.onStop()
//        super.onStop()
//    }

//    fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>?,
//        grantResults: IntArray?
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults!!)
//        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }


}

