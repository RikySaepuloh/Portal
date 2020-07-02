package com.saku.portalsatpam

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.saku.portalsatpam.apihelper.UtilsApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_content_main.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    var preferences  = Preferences()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        preferences.setPreferences(this)

        overridePendingTransition(R.anim.slide_from_right
            , R.anim.slide_to_left
        );
        initData()
        menu_masuk.animate()
    menu_masuk.setOnClickListener {
            val intent = Intent(this,NeinActivity::class.java)
//            intent.putExtra("menu","empty")
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_from_left,
//                R.anim.slide_to_right);
        }
        menu_keluar.setOnClickListener {
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

    private fun initData() {
        val apiservice = UtilsApi().getAPIService(this@MainActivity)
        apiservice?.satpamAktif()?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val data = JSONArray(obj.optString("user"))
                            for (counter in 0 until data.length()) {
                                val jsonObj: JSONObject = data.getJSONObject(counter)
                                val mydata = jsonObj.optString("nama")
                                val foto = jsonObj.optString("foto")
                                profile_name.text = mydata
                                Glide.with(this@MainActivity).load(foto).into(profile_image)
                            }
                        } catch (e: Exception) {

                        }
                    }else{
                        Toast.makeText(this@MainActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@MainActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@MainActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@MainActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(this@MainActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.nav_tunggu -> {
//                val intent = Intent(this,TungguActivity::class.java)
//                startActivity(intent)
//            }
            R.id.nav_paket -> {
                val intent = Intent(this,Paket2Activity::class.java)
                startActivity(intent)
            }
            R.id.nav_pantau -> {
                val intent = Intent(this,TamuActivity::class.java)
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