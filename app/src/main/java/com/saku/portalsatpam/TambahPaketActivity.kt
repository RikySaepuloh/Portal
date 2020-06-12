package com.saku.portalsatpam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_tambah_paket.*
import kotlinx.android.synthetic.main.bottom_sheet_list_penghuni.*
import kotlinx.android.synthetic.main.bottom_sheet_list_tujuan.*

class TambahPaketActivity : AppCompatActivity() {
    lateinit var behaviorTujuan : BottomSheetBehavior<View>
    lateinit var behaviorPenghuni : BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_paket)
        back.setOnClickListener {
            vibrate(longArrayOf(0, 350))
            if(behaviorTujuan.state == BottomSheetBehavior.STATE_EXPANDED||behaviorPenghuni.state == BottomSheetBehavior.STATE_EXPANDED){
                behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
                behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }else{
                super.onBackPressed()
            }
        }

        behaviorTujuan = BottomSheetBehavior.from(bottom_sheet_tujuan)
        behaviorPenghuni = BottomSheetBehavior.from(bottom_sheet_penghuni)

        tujuan.setOnClickListener {
            vibrate(longArrayOf(0, 350))
            if(behaviorTujuan.state != BottomSheetBehavior.STATE_EXPANDED){
                behaviorTujuan.state = BottomSheetBehavior.STATE_EXPANDED
                overlay.visibility = View.VISIBLE
            }else{
                behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }
        }

        penghuni.setOnClickListener {
            vibrate(longArrayOf(0, 350))
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
            vibrate(longArrayOf(0, 350))
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
