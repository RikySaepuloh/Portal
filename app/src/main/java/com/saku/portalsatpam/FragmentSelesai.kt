package com.saku.portalsatpam

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.ImagePrintable
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import kotlinx.android.synthetic.main.activity_selesai_masuk.view.selesai
import kotlinx.android.synthetic.main.fragment_selesai_masuk.*
import kotlinx.android.synthetic.main.fragment_selesai_masuk.view.*
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class FragmentSelesai : Fragment() {
//    private var param1: String? = null
//    private var param2: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        arguments?.let {
////            param1 = it.getString(ARG_PARAM1)
////            param2 = it.getString(ARG_PARAM2)
////        }
//    }

    private lateinit var myview: View
//    lateinit var dataPasser: DataPasserKeperluan
    private var printing : Printing? = null
    var penghunirumah : String? = null
    var tujuan : String? = null
    var keperluan : String? = null
    var bitmap : Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_selesai_masuk, container, false)
        Printooth.init(context!!)
        try {
            if (Printooth.hasPairedPrinter())
                printing = Printooth.printer()
        } catch (e: Exception) {
        }
        if((activity as NeinActivity).penghuni!="-"){
            penghunirumah = (activity as NeinActivity).penghuni.toString()
            tujuan = (activity as NeinActivity).tujuan.toString()
            keperluan = (activity as NeinActivity).keperluan.toString()
        }
        return myview
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scan_bluetooth.setOnClickListener {
            try {
                if (Printooth.hasPairedPrinter()) Printooth.removeCurrentPrinter()
                else startActivityForResult(Intent(context, ScanningActivity::class.java),
                    ScanningActivity.SCANNING_FOR_PRINTER)
            } catch (e: Exception) {
            }
        }

//        Glide.with(context!!).load(url).into(myview.qrcode)
//        val url =
//            "https://s3.amazonaws.com/koya-dev-videos/kindness/8da807aa-1e1e-413d-bf9b-5bb084646593/medialibrary/9456621508/videos/1eb78337-d569-41bd-95ad-153d9098de03.png"
//        val myurl="https://api.simkug.com/api/portal/storage/qrcode-5eec213899ffa.png"
//        val myr="https://devsai-s3.s3-ap-southeast-1.amazonaws.com/rtrw/qrcode-5eec21394782a.png"
//        val myr="https://image.freepik.com/free-photo/front-view-chair-room-with-plant-decoration_23-2148560899.jpg"
//        val mmm="https://cdn.sinarharapan.co/foto/2020/03/19/440-logo_tokopedia_unicorn_startup__tagar-800x450.jpg"
//        Glide.with(context!!).load(mrrr).placeholder(android.R.drawable.progress_indeterminate_horizontal).error(android.R.drawable.ic_dialog_alert).into(myview.qrcode)
//        val da = "https://boofcv.org/images/3/35/Example_rendered_qrcode.png"
        val da = " https://devsai-s3.s3-ap-southeast-1.amazonaws.com/rtrw/qrcode-5ef2cc969371c.png"
        DownloadImageTask((myview.qrcode))
            .execute("https://devsai-s3.s3-ap-southeast-1.amazonaws.com/rtrw/qrcode-5ef2cc969371c.png");

//        Glide.with(context!!)
//            .asBitmap()
//            .load(da)
//            .into(object : CustomTarget<Bitmap>(255,255){
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    myview.qrcode.setImageBitmap(resource)
//                    bitmap = getResizedBitmap(resource,255)
//                }
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // this is called when imageView is cleared on lifecycle call or for
//                    // some other reason.
//                    // if you are referencing the bitmap somewhere else too other than this imageView
//                    // clear it here as you can no longer have the bitmap
//                }
//            })

//        bitmap = (myview.qrcode.drawable as BitmapDrawable).bitmap


        myview.selesai.setOnClickListener {
             printSomePrintable()
        }

        printing?.printingCallback = object : PrintingCallback {
            override fun connectingWithPrinter() {
                Toast.makeText(context, "Connecting with printer", Toast.LENGTH_SHORT).show()
            }

            override fun printingOrderSentSuccessfully() {
                Toast.makeText(context, "Order sent to printer", Toast.LENGTH_SHORT).show()
                val intent = Intent(context,MainActivity::class.java)
                startActivity(intent)
                activity?.finishAffinity()
            }

            override fun connectionFailed(error: String) {
                Toast.makeText(context, "Failed to connect printer", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: String) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }

            override fun onMessage(message: String) {
                Toast.makeText(context, "Message: $message", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun printSomePrintable() {
        val printables = getSomePrintables()
        printing?.print(printables)
    }

    private fun getSomePrintables() = ArrayList<Printable>().apply {
        add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build()) // feed lines example in raw mode
        add(
            TextPrintable.Builder()
            .setText("Selamat Datang")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())

        add(
            TextPrintable.Builder()
            .setText("di")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("Pesona Bali Residence")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setEmphasizedMode(DefaultPrinter.EMPHASIZED_MODE_BOLD)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("==============================")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("No Pengunjung : 72")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("Keperluan : $keperluan")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("Tujuan : $tujuan")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(1)
            .build())
        add(
            TextPrintable.Builder()
            .setText("Penghuni : $penghunirumah")
            .setAlignment(DefaultPrinter.ALIGNMENT_LEFT)
            .setNewLinesAfter(2)
            .build())
        try {
            val mybitmap = getResizedBitmap((myview.qrcode.drawable as BitmapDrawable).bitmap,255)
            add(ImagePrintable.Builder(mybitmap!!).setAlignment(DefaultPrinter.ALIGNMENT_CENTER).setNewLinesAfter(1).build())
        } catch (e: Exception) {
        }
        add(
            TextPrintable.Builder()
                .setText("TM-0001")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(2)
                .build())
        val currentDate: String =
            SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(Date())
        add(
            TextPrintable.Builder()
                .setText(currentDate)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())

//        val stream = ByteArrayOutputStream()
//        getBitmapFromURL("https://api.simkug.com/api/portal/storage/qrcode-5eec213899ffa.png")?.compress(Bitmap.CompressFormat.PNG, 90, stream)
//        val images = stream.toByteArray()
        add(
            TextPrintable.Builder()
                .setText("==============================")
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(3)
                .build())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK)
            printSomePrintable()
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment FragmentTujuan.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            FragmentTujuan().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

//    fun getBitmapFromURL(src: String?): Bitmap? {
//        return try {
//            val url = URL(src)
//            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//            connection.doInput = true
//            connection.connect()
//            val input: InputStream = connection.inputStream
//            BitmapFactory.decodeStream(input)
//        } catch (e: IOException) {
//            e.printStackTrace()
//            null
//        }
//    }
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }
}

private class DownloadImageTask(bmImage: ImageView) :
    AsyncTask<String?, Void?, Bitmap?>() {
    var bmImage: ImageView = bmImage
    override fun doInBackground(vararg params: String?): Bitmap? {
        val urldisplay = params[0]
        var mIcon11: Bitmap? = null
        try {
            val my: InputStream = URL(urldisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(my)
        } catch (e: java.lang.Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }
        return mIcon11
    }

    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }

}
