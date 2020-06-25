package com.saku.portalsatpam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
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
    var imgPath : String? = null
    var stat = 1
    var mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            when {
                intent.hasExtra("penghuni") -> {
                    val name = intent.getStringExtra("penghuni")
                    //                    Toast.makeText(this@NeinActivity, name, Toast.LENGTH_SHORT).show()
                    penghuni = name
                    tv_penghuni.text = penghuni
                    resetBG()
                    card_identitas.background = ContextCompat.getDrawable(this@NeinActivity,R.drawable.content_menu_selected)
                    n4.setImageResource(R.drawable.n4_true)
                    stat++
                }
                intent.hasExtra("imagefile") -> {
                    val image = intent.getStringExtra("imagefile")
                    imgPath = image
                    Toast.makeText(this@NeinActivity, image, Toast.LENGTH_SHORT).show()
                    Glide.with(this@NeinActivity).load(image).into(identitas)
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        FragmentSelesai()
                    ).commitAllowingStateLoss()
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
                    stat--
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@NeinActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nein)
        overridePendingTransition(R.anim.slide_from_right
                 , R.anim.slide_to_left
                     );
//        val fragment : FragmentKeperluan = FragmentKeperluan()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, FragmentKeperluan()).commit()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("message_subject_intent"))
//        val mesrec: BroadcastReceiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent) {
//                if(intent.hasExtra(""))
//                val name = intent.getStringExtra("imagefile")
//                Toast.makeText(this@NeinActivity, name, Toast.LENGTH_SHORT).show()
////                penghuni = name
////                tv_penghuni.text = penghuni
////                resetBG()
////                card_identitas.background = ContextCompat.getDrawable(this@NeinActivity,R.drawable.content_menu_selected)
////                n4.setImageResource(R.drawable.n4_true)
////                stat++
//            }
//        }
//        LocalBroadcastManager.getInstance(this)
//            .registerReceiver(mesrec, IntentFilter("imagefile_intent"))

//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
//        supportFragmentManager.beginTransaction().replace(R.id.viewpager, FragmentKeperluan.newInstance()).commit();

//        val fragments: ArrayList<Fragment> =
//            arrayListOf(
//                FragmentKeperluan()
////                SearchFragment(),
////                AddFragment(),
////                NotificationsFragment(),
////                ProfileFragment()
//            )
//        viewpager.adapter = FragmentViewPagerAdapter(supportFragmentManager, fragments)
////        viewpager.offscreenPageLimit = 5
//        viewpager.currentItem = 0


//        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//
//            }
//
//            override fun onPageSelected(position: Int) {
//                when(position){
//                    0 -> navigation.selectedItemId = position
//                    1 -> navigation.selectedItemId = position
//                    2 -> navigation.selectedItemId = position
//                    3 -> navigation.selectedItemId = position
//                    4 -> navigation.selectedItemId = position
//
//                }
//
//
//            }
//        })
//        navigation.setOnNavigationItemSelectedListener { it
//            it.let {
//                when(it.itemId){
//                    R.id.nav_home -> {
//                        binding.viewpager.currentItem = 0
//                        return@setOnNavigationItemSelectedListener true
//                    }
//                    R.id.nav_search-> {
//                        binding.viewpager.currentItem = 1
//                        return@setOnNavigationItemSelectedListener true
//                    }
//                    R.id.nav_add -> {
//                        binding.viewpager.currentItem = 2
//                        return@setOnNavigationItemSelectedListener true
//                    }
//                    R.id.nav_notifications -> {
//                        binding.viewpager.currentItem = 3
//                        return@setOnNavigationItemSelectedListener true
//                    }
//                    R.id.nav_profile -> {
//                        binding.viewpager.currentItem = 4
//                        return@setOnNavigationItemSelectedListener true
//                    }
//                    else -> {
//                        binding.viewpager.currentItem = 0
//                        return@setOnNavigationItemSelectedListener true
//                    }
//                }
//            }


        card_keperluan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
        card_keperluan.setOnClickListener{
            resetBG()
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, FragmentKeperluan()).commit()
            card_keperluan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
            n1.setImageResource(R.drawable.n1_true)
        }
        card_tujuan.setOnClickListener{
            if(keperluan=="empty"){
                Toast.makeText(this,"Isi menu ini terlebih dahulu",Toast.LENGTH_LONG).show()
            }else{
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                    FragmentTujuan()
                ).commit()
                resetBG()
                card_tujuan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
                n2.setImageResource(R.drawable.n2_true)
                stat++
            }
        }
        card_penghuni.setOnClickListener{
            if(stat>1){
                resetBG()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                    FragmentPenghuni()
                ).commit()
                card_penghuni.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
                n3.setImageResource(R.drawable.n3_true)
                stat++
            }else{
                Toast.makeText(this,"Isi menu ini terlebih dahulu",Toast.LENGTH_LONG).show()
            }
        }
        card_identitas.setOnClickListener{
            if(stat>2){
                resetBG()
                card_identitas.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
                n4.setImageResource(R.drawable.n4_true)
            }else{
                Toast.makeText(this,"Isi menu ini terlebih dahulu",Toast.LENGTH_LONG).show()
            }
        }
        back.setOnClickListener {
            val intent = Intent(this@NeinActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
//            //vibrate(longArrayOf(0, 350))
//            val intent = Intent(this@NeinActivity,MainActivity::class.java)
//            startActivity(intent)
        }

    }


    fun changeFragment(fragmentClass: Fragment) {
        var fragment: Fragment? = null
        try {
            fragment = fragmentClass
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Insert the fragment by replacing any existing fragment
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



//    override fun onBackPressed() {
////        val intent = Intent(this@NeinActivity,MainActivity::class.java)
////        startActivity(intent)
//        super.onBackPressed()
//    }

    override fun onDataPasserKeperluan(data: String, untuk: String) {
        if(untuk=="keperluan"){
            keperluan = data
            tv_keperluan.text = keperluan
            resetBG()
            card_tujuan.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
            n2.setImageResource(R.drawable.n2_true)
            stat++
        }else if (untuk=="tujuan"){
            tujuan = data
            tv_tujuan.text = tujuan
            resetBG()
            card_penghuni.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
            n3.setImageResource(R.drawable.n3_true)
            stat++
        }
//        else if (untuk=="penghuni"){
//            penghuni = data
//            tv_penghuni.text = penghuni
////            card_penghuni.background = ContextCompat.getDrawable(this,R.drawable.content_menu_selected)
////            n3.setImageResource(R.drawable.n3_true)
////            stat++
//        }else{
//            tujuan = data
//            tv_tujuan.text = tujuan
//        }
    }
}
