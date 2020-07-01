package com.saku.portalsatpam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_paket_search.*

class PaketSearchActivity : AppCompatActivity() {
    var params = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket_search)
        try {
            params = intent.getStringExtra("params")!!
            if (params=="paket"){
                judul.text = "Cari Paket"
            }else{
                judul.text = "Cari Tamu"
            }
        } catch (e: Exception) {
        }
        back.setOnClickListener { super.onBackPressed() }
        initView()
    }
    
    fun initView(){
        delete.setOnClickListener {
            bs_tujuan.text.clear()
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_0.setOnClickListener {
            bs_tujuan.text.append("0")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_1.setOnClickListener {
            bs_tujuan.text.append("1")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_2.setOnClickListener {
            bs_tujuan.text.append("2")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_3.setOnClickListener {
            bs_tujuan.text.append("3")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_4.setOnClickListener {
            bs_tujuan.text.append("4")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_5.setOnClickListener {
            bs_tujuan.text.append("5")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_6.setOnClickListener {
            bs_tujuan.text.append("6")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_7.setOnClickListener {
            bs_tujuan.text.append("7")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_8.setOnClickListener {
            bs_tujuan.text.append("8")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_9.setOnClickListener {
            bs_tujuan.text.append("9")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_slash.setOnClickListener {
            bs_tujuan.text.append("-")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_a.setOnClickListener {
            bs_tujuan.text.append("A")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_b.setOnClickListener {
            bs_tujuan.text.append("B")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_c.setOnClickListener {
            bs_tujuan.text.append("C")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_d.setOnClickListener {
            bs_tujuan.text.append("D")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        menu_e.setOnClickListener {
            bs_tujuan.text.append("E")
            this@PaketSearchActivity.vibrate(longArrayOf(0, 150))
        }
        btn_search.setOnClickListener {
            if(params=="paket"){
                val mydata = Intent("paket_trigger")
                mydata.putExtra("search", bs_tujuan.text.toString())
                LocalBroadcastManager.getInstance(this@PaketSearchActivity).sendBroadcast(mydata)
                finish()
            }else{
                val mydata = Intent("tamu_trigger")
                mydata.putExtra("search", bs_tujuan.text.toString())
                LocalBroadcastManager.getInstance(this@PaketSearchActivity).sendBroadcast(mydata)
                finish()
            }
        }
    }
}
