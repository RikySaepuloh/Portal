package com.saku.portalsatpam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.saku.portalsatpam.R
import com.saku.portalsatpam.models.ModelPaket
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PaketAdapter(private val data: ArrayList<ModelPaket>, val idpaket:String) : RecyclerView.Adapter<PaketAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    private var mFilteredList: ArrayList<ModelPaket>? = data
    private var mArrayList:ArrayList<ModelPaket>?=data
    private var clickable:Boolean = true
    private var selectedPos = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_paket, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

//    fun add(mdata: ArrayList<ModelPaket>, clickable:Boolean){
//        mFilteredList=mdata
//        this.clickable=clickable
//        notifyDataSetChanged()
//    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString().toLowerCase(Locale.ROOT)
                mFilteredList = if (charString.isEmpty()) {
                    mArrayList
                } else {
                    val filteredList: ArrayList<ModelPaket> = ArrayList()
                    for (dataRumah in mArrayList!!) {
                        if (
                            dataRumah.kode_rumah?.toLowerCase(Locale.ROOT)?.contains(charString)!!
                        ) {
                            filteredList.add(dataRumah)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                mFilteredList = filterResults.values as ArrayList<ModelPaket>?
                notifyDataSetChanged()

            }
        }
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        holder.card.isSelected = selectedPos==position
        holder.penghuni.text = mFilteredList?.get(position)?.penghuni
        holder.tujuan.text = mFilteredList?.get(position)?.kode_rumah
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date1: Date = SimpleDateFormat("dd MMM yyyy").parse(mFilteredList?.get(position)?.tanggal.toString())
        holder.tanggal.text = sdf.format(date1)
        holder.card.setOnClickListener {
            if(this.clickable){
                notifyItemChanged(selectedPos)
                selectedPos = position
                notifyItemChanged(selectedPos)
                val mydata = Intent("paket_trigger")
                mydata.putExtra("id_paket", mFilteredList?.get(position)?.idPaket)
                mydata.putExtra("id_satpam", mFilteredList?.get(position)?.idSatpam)
                mydata.putExtra("nama_satpam", mFilteredList?.get(position)?.namaSatpam)
                mydata.putExtra("tujuan", mFilteredList?.get(position)?.kode_rumah)
                mydata.putExtra("penghuni", mFilteredList?.get(position)?.penghuni)
                mydata.putExtra("image", mFilteredList?.get(position)?.foto)
                LocalBroadcastManager.getInstance(ctx!!).sendBroadcast(mydata)
            }
//            ctx?.//vibrate(longArrayOf(0, 350))
//            Toast.makeText(ctx!!,"Sentuh icon search diatas untuk memunculkan Menu",Toast.LENGTH_LONG).show()
//            ctx?.//vibrate(longArrayOf(0, 350))
//            val intent = Intent(ctx, IdentitasActivity::class.java)
//            ctx?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mFilteredList!!.size
    }

    inner class NamaKelompokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: LinearLayout = itemView.findViewById<View>(R.id.data_layout) as LinearLayout
//        val no: TextView = itemView.findViewById<View>(R.id.no) as TextView
//        val kode: TextView = itemView.findViewById<View>(R.id.kode) as TextView
        val tujuan: TextView = itemView.findViewById<View>(R.id.tujuan) as TextView
        val penghuni: TextView = itemView.findViewById<View>(R.id.penghuni) as TextView
        val tanggal: TextView = itemView.findViewById<View>(R.id.tanggal) as TextView
    }

}