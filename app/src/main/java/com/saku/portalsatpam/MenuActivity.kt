package com.saku.portalsatpam

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.saku.portalsatpam.adapter.MenuAdapter
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {
    var adapter: MenuAdapter? = null

    var menus = arrayListOf(
        "Makanan","Paket","OJOL","Tamu","Jualan","Teknisi"
    )
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)
        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            super.onBackPressed() }
        adapter = MenuAdapter(menus)
        recyclerview.layoutManager = GridLayoutManager(this,3)
        recyclerview.adapter = adapter
    }
}
