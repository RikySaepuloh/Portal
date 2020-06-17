package com.saku.portalsatpam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saku.portalsatpam.adapter.TamuAdapter
import com.saku.portalsatpam.models.ModelTamu
import kotlinx.android.synthetic.main.activity_pantau.*

class PantauActivity : AppCompatActivity() {
    private var data : ArrayList<ModelTamu> = ArrayList()
    private lateinit var myadapter : TamuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantau)
        data.add(ModelTamu("15","D4/7","Tamu","120 Menit"))
        data.add(ModelTamu("73","B5/1","Teknisi","15 Menit"))
        data.add(ModelTamu("12","A3/3","OJOL","150 Menit"))
        data.add(ModelTamu("86","C5/2","Makanan","5 Menit"))
        myadapter = TamuAdapter(data)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = myadapter
        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            super.onBackPressed()
        }
    }
}
