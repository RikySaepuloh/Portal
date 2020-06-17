package com.saku.portalsatpam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_batal.*

class BatalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batal)
        selesai.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this@BatalActivity,MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this@BatalActivity,MainActivity::class.java)
        startActivity(intent)
//        super.onBackPressed()
    }
}
