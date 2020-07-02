package com.saku.portalsatpam.fragments

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
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
import com.saku.portalsatpam.*
import com.saku.portalsatpam.apihelper.UtilsApi
import kotlinx.android.synthetic.main.activity_selesai_masuk.view.selesai
import kotlinx.android.synthetic.main.fragment_selesai_masuk.*
import kotlinx.android.synthetic.main.fragment_selesai_masuk.view.*
import kotlinx.coroutines.Runnable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FragmentSelesai : Fragment() {

    var preferences  = Preferences()
    private lateinit var myview: View
    private var printing : Printing? = null
    private var penghunirumah : String? = null
    private var tujuan : String? = null
    private var keperluanSingkat : String? = null
    private var keperluan : String? = null
    private var blok:String? = null
    private var noPengunjung : String? = null
    private var noUrut : String? = null
    private var norumah :String? = null
    private var nik :String? = null
    private var image :String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myview = inflater.inflate(R.layout.fragment_selesai_masuk, container, false)
        Printooth.init(context!!)
        preferences.setPreferences(context!!)
        try {
            if (Printooth.hasPairedPrinter()){
                printing = Printooth.printer()
            }
        } catch (e: Exception) {
        }

        val handler = Handler()
        var runnable= Runnable { checkBluetoothDevicesConnection() }
        val delay = 1 * 1000

        handler.postDelayed(Runnable { //do something
            handler.postDelayed(runnable, delay.toLong())
        }.also { runnable = it }, delay.toLong())



        if((activity as NeinActivity).penghuni!="-"){
            penghunirumah = (activity as NeinActivity).penghuni.toString()
            image = (activity as NeinActivity).imgPath.toString()
            tujuan = (activity as NeinActivity).tujuan.toString()
            keperluan = (activity as NeinActivity).keperluan.toString()
            when ((activity as NeinActivity).keperluan.toString()){
                "Makanan" -> {
                    keperluanSingkat="MKN"
                }
                "OJOL" -> {
                    keperluanSingkat="OJL"
                }
                "Jualan" -> {
                    keperluanSingkat="JLN"
                }
                "Paket" -> {
                    keperluanSingkat="PKT"
                }
                "Teknisi" -> {
                    keperluanSingkat="TSK"
                }
                "Tamu" -> {
                    keperluanSingkat="TM"
                }
            }
            nik = (activity as NeinActivity).nikpenghuni.toString()
        }
        return myview
    }

    private fun checkBluetoothDevicesConnection(){
        val device: BluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(Printooth.getPairedPrinter()?.address)
        device.bondState
        if(device.bondState==BluetoothDevice.BOND_BONDED){
            myview.scan_bluetooth.text = getString(R.string.terhubung)
        }else{
            myview.scan_bluetooth.text = getString(R.string.tidak_terhubung)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scan_bluetooth.setOnClickListener {
            try {
                if (Printooth.hasPairedPrinter()) Printooth.removeCurrentPrinter()
                else
                    Toast.makeText(context,"Harap tunggu sampai scan selesai",Toast.LENGTH_LONG).show()
                    startActivityForResult(Intent(context, ScanningActivity::class.java),
                    ScanningActivity.SCANNING_FOR_PRINTER)
            } catch (e: Exception) {
            }
        }
        blok = tujuan?.substringBefore("-")
        norumah = tujuan?.substringAfter("-")
        val arrNoRumah : MutableList<RequestBody> = ArrayList()
        arrNoRumah.add(toRequestBody(norumah!!))
        val arrNama : MutableList<RequestBody> = ArrayList()
        arrNama.add(toRequestBody(penghunirumah!!))
        val arrNik: MutableList<RequestBody> = ArrayList()
        arrNik.add(toRequestBody(nik!!))
        val arrBlok: MutableList<RequestBody> = ArrayList()
        arrBlok.add(toRequestBody(blok!!))
        image?.let { initData(arrNik,arrBlok,arrNoRumah,arrNama, it) }

        myview.selesai.setOnClickListener {
             printSomePrintable()
        }

        printing?.printingCallback = object : PrintingCallback {
            override fun connectingWithPrinter() {
                Toast.makeText(context, "Connecting with printer", Toast.LENGTH_SHORT).show()
            }

            override fun printingOrderSentSuccessfully() {
                Toast.makeText(context, "Order sent to printer", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
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
        val currentDate: String =
            SimpleDateFormat("dd-MM-yyyy, HH:mm:ss", Locale.getDefault()).format(Date())
        add(
            TextPrintable.Builder()
                .setText(currentDate)
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(2)
                .build())
        add(
            TextPrintable.Builder()
            .setText("$tujuan / $keperluan / $penghunirumah")
            .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
            .setNewLinesAfter(2)
            .build())
        try {
            val mybitmap = 255.getResizedBitmap((myview.qrcode.drawable as BitmapDrawable).bitmap)
            add(ImagePrintable.Builder(mybitmap!!).setAlignment(DefaultPrinter.ALIGNMENT_CENTER).setNewLinesAfter(1).build())
        } catch (e: Exception) {
        }
        add(
            TextPrintable.Builder()
                .setText(noUrut.toString())
                .setAlignment(DefaultPrinter.ALIGNMENT_CENTER)
                .setNewLinesAfter(1)
                .build())
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
//            printSomePrintable()
        checkBluetoothDevicesConnection()

    }

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
private fun Int.getResizedBitmap(image: Bitmap): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = this
            height = (width / bitmapRatio).toInt()
        } else {
            height = this
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    private fun initData(
        nik: MutableList<RequestBody>,
        blok: MutableList<RequestBody>, norumah: MutableList<RequestBody>,
        penghuni: MutableList<RequestBody>,
        image:String) {

        val file = File(image)
        val fileReqBody: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val imagedata: MultipartBody.Part =
            MultipartBody.Part.createFormData("ktp", file.name, fileReqBody)
        val apiservice = context?.let { UtilsApi().getAPIService(it) }
        keperluanSingkat?.let {
            apiservice?.tamuMasuk(imagedata, it,penghuni,nik,norumah,blok)?.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            try {
                                val obj = JSONObject(response.body()!!.string())
                                noUrut = obj.optString("no_urut")
//                                myview.no_pengunjung.text = "$noUrut"
                                noPengunjung = obj.optString("no_tamu")
                                val message = obj.optString("message")
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                val qrcode = obj.optString("qrcode")
                                DownloadImageTask((myview.qrcode))
                                    .execute(qrcode)
//                                DownloadImageTask((myview.qrcode))
//                                    .execute("https://devsai-s3.s3-ap-southeast-1.amazonaws.com/rtrw/qrcode-5ef2cc969371c.png");
//                                val gson = Gson()
//                                val type: Type = object :
//                                    TypeToken<ArrayList<ModelPaket?>?>() {}.type
//                                val datapaket: ArrayList<ModelPaket> =
//                                    gson.fromJson(obj.optString("data"), type)

                            } catch (e: Exception) {

                            }
                        }else{
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    } else if(response.code() == 422) {
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 401){
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                        preferences.preferencesLogout()
                        activity?.finish()
                        Toast.makeText(context, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 403){
                        Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 404){
                        Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

}


private class DownloadImageTask(bmImage: ImageView) :
    AsyncTask<String?, Void?, Bitmap?>() {
    var mimage: ImageView = bmImage
    override fun doInBackground(vararg params: String?): Bitmap? {
        val urldisplay = params[0]
        var mIcon11: Bitmap? = null
        try {
            val my: InputStream = URL(urldisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(my)
        } catch (e: java.lang.Exception) {
            Log.e("Error", e.message!!)
            e.printStackTrace()
        }
        return mIcon11
    }

    override fun onPostExecute(result: Bitmap?) {
        mimage.setImageBitmap(result)
    }

}
