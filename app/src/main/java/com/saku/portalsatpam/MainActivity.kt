package com.saku.portalsatpam

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_content_main.*


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    menu_masuk.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this,NeinActivity::class.java)
//            intent.putExtra("menu","empty")
            startActivity(intent)
        }
        menu_keluar.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this,KeluarActivity::class.java)
            startActivity(intent)
        }
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, 0, 0
        )


//        toggle.setHomeAsUpIndicator(R.drawable.ic_hamburger)
//        toggle.isDrawerIndicatorEnabled = true;
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_logout.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_tunggu -> {
                val intent = Intent(this,TungguActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_paket -> {
                val intent = Intent(this,PaketActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_pantau -> {
                val intent = Intent(this,PantauActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount == 0) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Yakin ingin keluar?")
            builder.setCancelable(true)
            builder.setPositiveButton("Ya") { _, _ -> super.onBackPressed() }
            builder.setNegativeButton("Tidak") { _, _ ->

            }
            val dialog = builder.create()
            dialog.show()
        } else {
            super.onBackPressed()
        }
    }


}

fun Context.vibrate(pattern: LongArray) {
    val vibrator =
        applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator? ?: return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE)
        )

    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(pattern, -1)
    }
}