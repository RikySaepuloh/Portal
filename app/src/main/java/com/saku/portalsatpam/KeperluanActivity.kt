package com.saku.portalsatpam

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_keperluan.*
import java.util.*


class KeperluanActivity : AppCompatActivity() {
    private lateinit var oldColors: ColorStateList
    private lateinit var selectedMenu : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keperluan)
        setSupportActionBar(toolbar)
        oldColors= paket.textColors
        bs_tujuan.requestFocus()
//        tujuan.setOnFocusChangeListener { v, hasFocus ->
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            if (hasFocus) {
//                imm.hideSoftInputFromWindow(v.windowToken, 0)
//            }
//        }
        bs_tujuan.setOnTouchListener { v, event ->
            v.onTouchEvent(event)
            val inputMethod: InputMethodManager = v.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            if (inputMethod != null) {
                inputMethod.hideSoftInputFromWindow(v.windowToken, 0)
//            }
            true
        }
        val data = intent
        val menu : String? = data.getStringExtra("menu")
//        tujuan.run {
//            tujuan.setSelection(tujuan.text.length)
//        }
        when {
            menu?.toLowerCase(Locale.ROOT) == "makanan" -> {
                defStyle()
                makanan.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
                makanan.setTextColor(ContextCompat.getColor(this,android.R.color.white))
            }
            menu?.toLowerCase(Locale.ROOT) == "paket" -> {
                defStyle()
                paket.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
                paket.setTextColor(ContextCompat.getColor(this,android.R.color.white))
            }
            menu?.toLowerCase(Locale.ROOT) == "ojol" -> {
                defStyle()
                ojol.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
                ojol.setTextColor(ContextCompat.getColor(this,android.R.color.white))
            }
            menu?.toLowerCase(Locale.ROOT) == "tamu" -> {
                defStyle()
                tamu.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
                tamu.setTextColor(ContextCompat.getColor(this,android.R.color.white))
            }
            menu?.toLowerCase(Locale.ROOT) == "jualan" -> {
                defStyle()
                jualan.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
                jualan.setTextColor(ContextCompat.getColor(this,android.R.color.white))
            }
            menu?.toLowerCase(Locale.ROOT) == "teknisi" -> {
                defStyle()
                teknisi.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
                teknisi.setTextColor(ContextCompat.getColor(this,android.R.color.white))
            }
//            else -> {
//                defStyle()
//                makanan.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
//                makanan.setTextColor(ContextCompat.getColor(this,android.R.color.white))
//            }
        }


        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            super.onBackPressed() }
        selanjutnya.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
//            if(menu==null||menu==""||menu.isEmpty()){
//                Toast.makeText(this,"Dimohon untuk memilih menu disamping terlebih dahulu", Toast.LENGTH_SHORT).show()
//            }else{
                val intent = Intent(this,ProsesActivity::class.java)
                startActivity(intent)
//            }

        }
        makanan.setOnClickListener {
            vibrate(longArrayOf(0, 150))
            defStyle()
            selectedMenu = "makanan"
            makanan.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
            makanan.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        }
        paket.setOnClickListener {
            vibrate(longArrayOf(0, 150))
            defStyle()
            selectedMenu = "paket"
            paket.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
            paket.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        }
        ojol.setOnClickListener {
            vibrate(longArrayOf(0, 150))
            defStyle()
            selectedMenu = "ojol"
            ojol.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
            ojol.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        }
        tamu.setOnClickListener {
            vibrate(longArrayOf(0, 150))
            defStyle()
            selectedMenu = "tamu"
            tamu.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
            tamu.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        }
        jualan.setOnClickListener {
            vibrate(longArrayOf(0, 150))
            defStyle()
            selectedMenu = "jualan"
            jualan.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
            jualan.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        }
        teknisi.setOnClickListener {
            vibrate(longArrayOf(0, 150))
            defStyle()
            selectedMenu = "teknisi"
            teknisi.background = ContextCompat.getDrawable(this,R.drawable.menu_keyboard_dark)
            teknisi.setTextColor(ContextCompat.getColor(this,android.R.color.white))
        }

        delete.setOnClickListener {
            bs_tujuan.text.clear()
            vibrate(longArrayOf(0, 150))
        }
        menu_0.setOnClickListener {
            bs_tujuan.text.append("0")
            vibrate(longArrayOf(0, 150))
        }
        menu_1.setOnClickListener {
            bs_tujuan.text.append("1")
            vibrate(longArrayOf(0, 150))
        }
        menu_2.setOnClickListener {
            bs_tujuan.text.append("2")
            vibrate(longArrayOf(0, 150))
        }
        menu_3.setOnClickListener {
            bs_tujuan.text.append("3")
            vibrate(longArrayOf(0, 150))
        }
        menu_4.setOnClickListener {
            bs_tujuan.text.append("4")
            vibrate(longArrayOf(0, 150))
        }
        menu_5.setOnClickListener {
            bs_tujuan.text.append("5")
            vibrate(longArrayOf(0, 150))
        }
        menu_6.setOnClickListener {
            bs_tujuan.text.append("6")
            vibrate(longArrayOf(0, 150))
        }
        menu_7.setOnClickListener {
            bs_tujuan.text.append("7")
            vibrate(longArrayOf(0, 150))
        }
        menu_8.setOnClickListener {
            bs_tujuan.text.append("8")
            vibrate(longArrayOf(0, 150))
        }
        menu_9.setOnClickListener {
            bs_tujuan.text.append("9")
            vibrate(longArrayOf(0, 150))
        }
        menu_slash.setOnClickListener {
            bs_tujuan.text.append("/")
            vibrate(longArrayOf(0, 150))
        }
        menu_a.setOnClickListener {
            bs_tujuan.text.append("A")
            vibrate(longArrayOf(0, 150))
        }
        menu_b.setOnClickListener {
            bs_tujuan.text.append("B")
            vibrate(longArrayOf(0, 150))
        }
        menu_c.setOnClickListener {
            bs_tujuan.text.append("C")
            vibrate(longArrayOf(0, 150))
        }
        menu_d.setOnClickListener {
            bs_tujuan.text.append("D")
            vibrate(longArrayOf(0, 150))
        }
        menu_e.setOnClickListener {
            bs_tujuan.text.append("E")
            vibrate(longArrayOf(0, 150))
        }
//        menu_f.setOnClickListener {
//            tujuan.text.append("F")
//            vibrate(longArrayOf(0, 150))
//        }
//        menu_g.setOnClickListener {
//            tujuan.text.append("G")
//            vibrate(longArrayOf(0, 150))
//        }
//        menu_h.setOnClickListener {
//            tujuan.text.append("H")
//            vibrate(longArrayOf(0, 150))
//        }
//        menu_i.setOnClickListener {
//            tujuan.text.append("I")
//            vibrate(longArrayOf(0, 150))
//        }
//        menu_j.setOnClickListener {
//            tujuan.text.append("J")
//            vibrate(longArrayOf(0, 150))
//        }
//        menu_k.setOnClickListener {
//            tujuan.text.append("K")
//            vibrate(longArrayOf(0, 150))
//        }
    }
    fun defStyle(){
        makanan.setBackgroundResource(0)
        paket.setBackgroundResource(0)
        ojol.setBackgroundResource(0)
        jualan.setBackgroundResource(0)
        tamu.setBackgroundResource(0)
        teknisi.setBackgroundResource(0)
        makanan.setTextColor(oldColors)
        paket.setTextColor(oldColors)
        ojol.setTextColor(oldColors)
        jualan.setTextColor(oldColors)
        tamu.setTextColor(oldColors)
        teknisi.setTextColor(oldColors)
//        makanan.typeface = Typeface.DEFAULT
//        paket.typeface = Typeface.DEFAULT
//        ojol.typeface = Typeface.DEFAULT
//        jualan.typeface = Typeface.DEFAULT
//        tamu.typeface = Typeface.DEFAULT
//        teknisi.typeface = Typeface.DEFAULT
    }


}
