package com.saku.portalsatpam

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_selesai_masuk.*

class SelesaiMasukActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selesai_masuk)
        val data = intent
        val filepath : String? = data.getStringExtra("filepath")
        Toast.makeText(this,filepath,Toast.LENGTH_LONG).show()
        selesai.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

    }
    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
//        super.onBackPressed()
    }
}
