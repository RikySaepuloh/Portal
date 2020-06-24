package com.saku.portalsatpam.print

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.converter.ArabicConverter
import com.mazenrashed.printooth.data.printable.ImagePrintable
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import com.saku.portalsatpam.R
import kotlinx.android.synthetic.main.activity_print.*

class PrintActivity : AppCompatActivity() {
    private var printing : Printing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_print)
        Printooth.init(this)
        if (Printooth.hasPairedPrinter())
            printing = Printooth.printer()
        initViews()
        initListeners()
    }

    private fun initViews() {
        btnPiarUnpair.text = if (Printooth.hasPairedPrinter()) "Un-pair ${Printooth.getPairedPrinter()?.name}" else "Pair with printer"
    }

    private fun initListeners() {
        btnPrint.setOnClickListener {
            if (!Printooth.hasPairedPrinter()) startActivityForResult(
                Intent(this,
                ScanningActivity::class.java),
                ScanningActivity.SCANNING_FOR_PRINTER)
            else printSomePrintable()
        }

        btnPrintImages.setOnClickListener {
            if (!Printooth.hasPairedPrinter()) startActivityForResult(Intent(this,
                ScanningActivity::class.java),
                ScanningActivity.SCANNING_FOR_PRINTER)
            else printSomeImages()
        }

        btnPiarUnpair.setOnClickListener {
            if (Printooth.hasPairedPrinter()) Printooth.removeCurrentPrinter()
            else startActivityForResult(Intent(this, ScanningActivity::class.java),
                ScanningActivity.SCANNING_FOR_PRINTER)
            initViews()
        }

//        btnCustomPrinter.setOnClickListener {
//            startActivity(Intent(this, WoosimActivity::class.java))
//        }

        printing?.printingCallback = object : PrintingCallback {
            override fun connectingWithPrinter() {
                Toast.makeText(this@PrintActivity, "Connecting with printer", Toast.LENGTH_SHORT).show()
            }

            override fun printingOrderSentSuccessfully() {
                Toast.makeText(this@PrintActivity, "Order sent to printer", Toast.LENGTH_SHORT).show()
            }

            override fun connectionFailed(error: String) {
                Toast.makeText(this@PrintActivity, "Failed to connect printer", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String) {
                Toast.makeText(this@PrintActivity, error, Toast.LENGTH_SHORT).show()
            }

            override fun onMessage(message: String) {
                Toast.makeText(this@PrintActivity, "Message: $message", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun printSomePrintable() {
        val printables = getSomePrintables()
        printing?.print(printables)
    }

    private fun printSomeImages() {
        val printables = ArrayList<Printable>().apply {
            add(ImagePrintable.Builder(R.drawable.default_masuk, resources).setNewLinesAfter(5).build())
//            add(ImagePrintable.Builder(R.drawable.ic_hamburger, resources).build())
//            add(ImagePrintable.Builder(R.drawable.ic_hamburger, resources).build())
        }
        printing?.print(printables)
    }

    private fun getSomePrintables() = ArrayList<Printable>().apply {
        add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build()) // feed lines example in raw mode
//
//        add(
//            TextPrintable.Builder()
//            .setText(" Hello World : été è à '€' içi Bò Xào Coi Xanh\n\n")
//            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
//            .setNewLinesAfter(1)
//            .build())
//
//        add(TextPrintable.Builder()
//            .setText("Hello World : été è à €")
//            .setCharacterCode(DefaultPrinter.CHARCODE_PC1252)
//            .setNewLinesAfter(1)
//            .build())
//
//        add(TextPrintable.Builder()
//            .setText("Hello World")
//            .setLineSpacing(DefaultPrinter.LINE_SPACING_60)
//            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
//            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
//            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
//            .setNewLinesAfter(1)
//            .build())

        add(TextPrintable.Builder()
            .setText("Selamat Datang")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())

        add(TextPrintable.Builder()
            .setText("di")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("Pesona Bali Residence")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("==============================")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("No Pengunjung : 72")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("Keperluan : Makanan")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("Tujuan : D4-7")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(1)
            .build())
        add(TextPrintable.Builder()
            .setText("Penghuni : Dadang Koswara")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(3)
            .build())
//            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
//            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)


//        add(TextPrintable.Builder()
//            .setText("اختبار العربية")
//            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
//            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
//            .setFontSize(DefaultPrinter.FONT_SIZE_NORMAL)
//            .setUnderlined(DefaultPrinter.UNDERLINED_MODE_ON)
//            .setCharacterCode(DefaultPrinter.CHARCODE_ARABIC_FARISI)
//            .setNewLinesAfter(1)
//            .setCustomConverter(ArabicConverter()) // change only the converter for this one
//            .build())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK)
            printSomePrintable()
        initViews()
    }
}