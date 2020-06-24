package com.saku.portalsatpam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.saku.portalsatpam.adapter.TungguAdapter
import com.saku.portalsatpam.models.ModelTunggu
import kotlinx.android.synthetic.main.activity_tunggu.*
import kotlinx.android.synthetic.main.bottom_sheet_konfirmasi.*
import kotlinx.android.synthetic.main.bottom_sheet_tunggu.*

class TungguActivity : AppCompatActivity() {
    private lateinit var behaviorTunggu : BottomSheetBehavior<View>
    private lateinit var behaviorKonfirmasi : BottomSheetBehavior<View>
    private var data : ArrayList<ModelTunggu> = ArrayList()
    private lateinit var myadapter : TungguAdapter
    var datatujuan :String? = null
    var datakeperluan:String? = null
    var datapenghuni:String? = null

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.hasExtra("trigger")){
                datatujuan = ": "+intent.getStringExtra("tujuan")
                datakeperluan = ": "+intent.getStringExtra("keperluan")
                datapenghuni = ": "+intent.getStringExtra("penghuni")
                bs_tujuan.text = datatujuan
                bs_keperluan.text = datakeperluan
                bs_penghuni.text = datapenghuni
                if(behaviorTunggu.state != BottomSheetBehavior.STATE_EXPANDED){
                    behaviorTunggu.state = BottomSheetBehavior.STATE_EXPANDED
                    overlay.visibility = View.VISIBLE
                }else{
                    behaviorTunggu.state = BottomSheetBehavior.STATE_COLLAPSED
                    overlay.visibility = View.GONE
                    bs_tujuan.text = ": -"
                    bs_keperluan.text = ": -"
                    bs_penghuni.text = ": -"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tunggu)
        data.add(ModelTunggu("D4/7","Riky Saepuloh","Tamu"))
        data.add(ModelTunggu("B5/1","Tatang Miharja","Teknisi"))
        data.add(ModelTunggu("A3/3","Laudya Chyntia Bella","OJOL"))
        data.add(ModelTunggu("C5/2","Roni","Makanan"))
        myadapter = TungguAdapter(data)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = myadapter
        behaviorTunggu = BottomSheetBehavior.from(bottom_sheet_tunggu)
        behaviorKonfirmasi = BottomSheetBehavior.from(bottom_sheet_konfirmasi)
        search.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
//            if(behaviorTunggu.state != BottomSheetBehavior.STATE_EXPANDED){
//                behaviorTunggu.state = BottomSheetBehavior.STATE_EXPANDED
//                overlay.visibility = View.VISIBLE
//            }else{
//                behaviorTunggu.state = BottomSheetBehavior.STATE_COLLAPSED
//                overlay.visibility = View.GONE
//            }
        }

        lanjut.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this@TungguActivity,IdentitasActivity::class.java)
            intent.putExtra("tunggu",true)
            startActivity(intent)
        }

        proses.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this@TungguActivity,BatalActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        pergi.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorKonfirmasi.state != BottomSheetBehavior.STATE_EXPANDED){
                behaviorKonfirmasi.state = BottomSheetBehavior.STATE_EXPANDED
                behaviorTunggu.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.VISIBLE
            }else{
                behaviorKonfirmasi.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }
        }

        batal.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorTunggu.state != BottomSheetBehavior.STATE_EXPANDED){
                behaviorTunggu.state = BottomSheetBehavior.STATE_EXPANDED
                behaviorKonfirmasi.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.VISIBLE
            }else{
                behaviorKonfirmasi.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }
        }
        behaviorTunggu.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        if (behaviorKonfirmasi.state == BottomSheetBehavior.STATE_COLLAPSED){
                            overlay.visibility = View.GONE
                        }
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

        behaviorKonfirmasi.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        if (behaviorTunggu.state == BottomSheetBehavior.STATE_COLLAPSED){
                            overlay.visibility = View.GONE
                        }
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


        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("bottom_sheet_trigger"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onBackPressed() {
        if(behaviorTunggu.state == BottomSheetBehavior.STATE_EXPANDED){
            behaviorTunggu.state = BottomSheetBehavior.STATE_COLLAPSED
        }else{
            super.onBackPressed()
        }
    }
}
