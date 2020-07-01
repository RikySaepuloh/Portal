package com.saku.portalsatpam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.saku.portalsatpam.fragments.FragmentKeperluan
import com.saku.portalsatpam.fragments.FragmentPenghuni
import com.saku.portalsatpam.fragments.FragmentSelesai
import com.saku.portalsatpam.fragments.FragmentTujuan
import kotlinx.android.synthetic.main.activity_nein.*


class NeinActivity : AppCompatActivity(), DataPasserKeperluan {
//    private var mAdapter: FragmentAdapter? = null
    var keperluan : String? = null
    var tujuan : String? = null
    var penghuni : String? = null
    var nikpenghuni : String? = null
    var imgPath : String? = null
    var backStatus : Boolean = true
    private var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when {
                intent.hasExtra("penghuni") -> {
                    penghuni = intent.getStringExtra("penghuni")
                    nikpenghuni = intent.getStringExtra("nik")
                    //                    Toast.makeText(this@NeinActivity, name, Toast.LENGTH_SHORT).show()
//                    penghuni = name
                    tv_penghuni.text = penghuni
                    resetBG()
                    card_identitas.background = ContextCompat.getDrawable(this@NeinActivity,R.drawable.content_menu_selected)
                    n4.setImageResource(R.drawable.n4_true)
                }
                intent.hasExtra("imagefile") -> {
                    val image = intent.getStringExtra("imagefile")
                    imgPath = image
                    Toast.makeText(this@NeinActivity, image, Toast.LENGTH_SHORT).show()
                    Glide.with(this@NeinActivity).load(image).into(identitas)
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        FragmentSelesai()
                    ).commitAllowingStateLoss()
                    backStatus = false
                    back.visibility = View.INVISIBLE
                    card_keperluan.isEnabled = false
                    card_tujuan.isEnabled = false
                    card_penghuni.isEnabled = false
                    card_identitas.isEnabled = false
                }
                intent.hasExtra("back") -> {
                    resetBG()
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        FragmentPenghuni()
                    ).commitAllowingStateLoss()
                    card_penghuni.background = ContextCompat.getDrawable(this@NeinActivity,R.drawable.content_menu_selected)
                    n4.setImageResource(R.drawable.n4_false)
                }
            }
        }
    }

    override fun onBackPressed() {
        if(backStatus){
            super.onBackPressed()
            val intent = Intent(this@NeinActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nein)
        overridePendingTransition(R.anim.slide_from_left
            , R.anim.slide_to_right
        )
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, FragmentKeperluan()).commit()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("message_subject_intent"))
        card_keperluan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
        card_keperluan.setOnClickListener{
            resetBG()
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentKeperluan()).commit()
            card_keperluan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
            n1.setImageResource(R.drawable.n1_true)
        }
        card_tujuan.setOnClickListener{
            if(tv_keperluan.text=="-"){
                Toast.makeText(this,"Isi menu ini terlebih dahulu",Toast.LENGTH_LONG).show()
            }else{
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                    FragmentTujuan()
                ).commit()
                resetBG()
                card_tujuan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
                n2.setImageResource(R.drawable.n2_true)
            }
        }
        card_penghuni.setOnClickListener{
            if(tv_tujuan.text=="-"){
                Toast.makeText(this,"Isi menu ini terlebih dahulu",Toast.LENGTH_LONG).show()
            }else{
                resetBG()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                    FragmentPenghuni()
                ).commit()
                card_penghuni.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
                n3.setImageResource(R.drawable.n3_true)
            }
        }
//        card_identitas.setOnClickListener{
//            if(stat>2){
//                resetBG()
//                card_identitas.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
//                n4.setImageResource(R.drawable.n4_true)
//            }else{
//                Toast.makeText(this,"Isi menu ini terlebih dahulu",Toast.LENGTH_LONG).show()
//            }
//        }
        back.setOnClickListener {
            val intent = Intent(this@NeinActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    fun changeFragment(fragmentClass: Fragment) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                .commit()
        }
    }

    fun resetBG(){
        card_keperluan.setBackgroundResource(0)
        card_penghuni.setBackgroundResource(0)
        card_tujuan.setBackgroundResource(0)
        card_identitas.setBackgroundResource(0)
    }

    override fun onDataPasserKeperluan(data: String, untuk: String) {
        if(untuk=="keperluan"){
            keperluan = data
            tv_keperluan.text = keperluan
            resetBG()
            card_tujuan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
            n2.setImageResource(R.drawable.n2_true)
        }else if (untuk=="tujuan"){
            tujuan = data
            tv_tujuan.text = tujuan
            resetBG()
            card_penghuni.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
            n3.setImageResource(R.drawable.n3_true)
        }
    }
}
