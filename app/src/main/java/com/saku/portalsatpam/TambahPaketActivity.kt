package com.saku.portalsatpam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_tambah_paket.*
import kotlinx.android.synthetic.main.bottom_sheet_list_penghuni.*
import kotlinx.android.synthetic.main.bottom_sheet_list_tujuan.*

class TambahPaketActivity : AppCompatActivity() {
    lateinit var behaviorTujuan : BottomSheetBehavior<View>
    lateinit var behaviorPenghuni : BottomSheetBehavior<View>
    var namefile :String? = null
    var imagefile:String? = null

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.hasExtra("imagefile")){
                namefile = intent.getStringExtra("namefile")
                imagefile = intent.getStringExtra("imagefile")
                addimage.text = namefile
                Toast.makeText(this@TambahPaketActivity,imagefile,Toast.LENGTH_LONG).show()
                Glide.with(this@TambahPaketActivity).load(imagefile).into(image)
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        LocalBroadcastManager.getInstance(this@TambahPaketActivity)
//            .registerReceiver(mMessageReceiver, IntentFilter("image_intent"))
//    }
//
//    override fun onPause() {
//        super.onPause()
//        LocalBroadcastManager.getInstance(this@TambahPaketActivity)
//            .unregisterReceiver(mMessageReceiver)
//    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this@TambahPaketActivity)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_paket)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("image_intent"))
        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorTujuan.state == BottomSheetBehavior.STATE_EXPANDED||behaviorPenghuni.state == BottomSheetBehavior.STATE_EXPANDED){
                behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
                behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }else{
                super.onBackPressed()
            }
        }

        image.setOnClickListener {
            val intent = Intent(this@TambahPaketActivity,IdentitasActivity::class.java)
            intent.putExtra("paket",true)
            startActivity(intent)
        }

        behaviorTujuan = BottomSheetBehavior.from(bottom_sheet_tujuan)
        behaviorPenghuni = BottomSheetBehavior.from(bottom_sheet_penghuni)

        bs_tujuan.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorTujuan.state != BottomSheetBehavior.STATE_EXPANDED){
                behaviorTujuan.state = BottomSheetBehavior.STATE_EXPANDED
                overlay.visibility = View.VISIBLE
            }else{
                behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }
        }

        bs_penghuni.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorPenghuni.state != BottomSheetBehavior.STATE_EXPANDED){
                behaviorPenghuni.state = BottomSheetBehavior.STATE_EXPANDED
                overlay.visibility = View.VISIBLE
            }else{
                behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }
        }

        behaviorPenghuni.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        overlay.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })

        behaviorTujuan.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        overlay.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })

        simpan.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this,PaketSuccessActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        if(behaviorTujuan.state == BottomSheetBehavior.STATE_EXPANDED||behaviorPenghuni.state == BottomSheetBehavior.STATE_EXPANDED){
            behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
            behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
            overlay.visibility = View.GONE
        }else{
            super.onBackPressed()
        }
    }
}
