package com.saku.portalsatpam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.saku.portalsatpam.adapter.PaketAdapter
import com.saku.portalsatpam.models.ModelPaket
import kotlinx.android.synthetic.main.activity_paket.*
import kotlinx.android.synthetic.main.bottom_sheet_paket.*

class PaketActivity : AppCompatActivity() {
    lateinit var behavior : BottomSheetBehavior<View>
    private var data : ArrayList<ModelPaket> = ArrayList()
    private lateinit var myadapter : PaketAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket)
        data.add(ModelPaket("86","D4/7","Riky Saepuloh"))
        data.add(ModelPaket("22","B5/1","Tatang Miharja"))
        data.add(ModelPaket("19","A3/3","Laudya Chyntia Bella"))
        data.add(ModelPaket("54","C5/2","Roni"))
        myadapter = PaketAdapter(data)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = myadapter
        
        back.setOnClickListener {
            vibrate(longArrayOf(0, 350))
            super.onBackPressed()
        }
        behavior = BottomSheetBehavior.from(bottom_sheet_tunggu)
        tambah.setOnClickListener {
            vibrate(longArrayOf(0, 350))
            val intent = Intent(this,TambahPaketActivity::class.java)
            startActivity(intent)
//            if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
//                behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            }else{
//                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//            }
        }

        search.setOnClickListener {
            vibrate(longArrayOf(0, 350))
            if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }



        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
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

        terima.setOnClickListener {
//            TODO : ADD FUNCTION IN HERE
            vibrate(longArrayOf(0, 350))
            if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            Toast.makeText(this,"Barang telah diterima",Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        if(behavior.state == BottomSheetBehavior.STATE_EXPANDED){
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }else{
            super.onBackPressed()
        }
    }
}
