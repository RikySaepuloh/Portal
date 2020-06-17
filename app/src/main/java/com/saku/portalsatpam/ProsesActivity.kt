package com.saku.portalsatpam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.saku.portalsatpam.adapter.PenghuniAdapter
import kotlinx.android.synthetic.main.activity_proses.*

class ProsesActivity : AppCompatActivity() {
    var adapter: PenghuniAdapter? = null

    var penghuni = arrayListOf(
        "Iko Uwais","Raditya Dhika","Imam Darto","Dimas Danang"
    )
    var gambar = arrayListOf(
        "https://m.media-amazon.com/images/M/MV5BMTM5OTMwODI0M15BMl5BanBnXkFtZTcwNzg2ODIyOA@@._V1_SY1000_CR0,0,698,1000_AL_.jpg",
        "https://www.portonews.com/wp-content/uploads/2020/03/photo-by-muvila-ede2bc180256494c6a09a73791e8cbf9_750x500.jpg",
        "https://upload.wikimedia.org/wikipedia/id/b/b6/Darto2.jpg",
        "https://greatmind.id/uploads/contributor-detail/b791411fd7fc6bb0d3c4752fb7c7d02c2cbc72e6.jpg"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proses)
        setSupportActionBar(toolbar)
        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            super.onBackPressed() }
        adapter = PenghuniAdapter(penghuni,gambar,"")
        recyclerview.layoutManager = GridLayoutManager(this,4)
        recyclerview.adapter = adapter
    }
}
