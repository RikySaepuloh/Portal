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
import com.saku.portalsatpam.models.ModelTamu
import java.util.*
import kotlin.collections.ArrayList

class Tamu2Adapter(private val data: ArrayList<ModelTamu>) : RecyclerView.Adapter<Tamu2Adapter.NamaKelompokViewHolder>(), View.OnClickListener {
    private var ctx: Context? = null
    private var mFilteredList: ArrayList<ModelTamu>? = data
    private var mArrayList:ArrayList<ModelTamu>?=data
    private var selectedPos = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_tamu, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    fun sortDescending(){
        mFilteredList?.sortByDescending { it.tgljamIn }
        notifyDataSetChanged()
    }

    fun sortAscending(){
        mFilteredList?.sortBy { it.tgljamIn }
        notifyDataSetChanged()
    }

//    fun add(mdata: ArrayList<ModelTamu>, clickable:Boolean){
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
                    val filteredList: ArrayList<ModelTamu> = ArrayList()
                    for (dataRumah in mArrayList!!) {
                        if (
                            dataRumah.rumah?.toLowerCase(Locale.ROOT)?.contains(charString)!!
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
                mFilteredList = filterResults.values as ArrayList<ModelTamu>?
                notifyDataSetChanged()

            }
        }
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        holder.card.isSelected = selectedPos==position
        holder.keperluan.text = mFilteredList?.get(position)?.keperluan
        holder.tujuan.text = mFilteredList?.get(position)?.rumah
//        val sdf = SimpleDateFormat("dd/MM/yyyy")
//        val date1: Date = SimpleDateFormat("dd MMM yyyy").parse(mFilteredList?.get(position)?.tgljamIn.toString())
//        holder.durasi.text = sdf.format(date1)
        holder.durasi.text = mFilteredList?.get(position)?.selisih
        holder.card.setOnClickListener {
            notifyItemChanged(selectedPos)
            selectedPos = position
            notifyItemChanged(selectedPos)
            val mydata = Intent("tamu_trigger")
            mydata.putExtra("id_tamu", mFilteredList?.get(position)?.idTamu)
            mydata.putExtra("nama", mFilteredList?.get(position)?.nama)
            mydata.putExtra("nik", mFilteredList?.get(position)?.nik)
            mydata.putExtra("keperluan", mFilteredList?.get(position)?.keperluan)
            mydata.putExtra("rumah", mFilteredList?.get(position)?.rumah)
            mydata.putExtra("ktp", mFilteredList?.get(position)?.ktp)
            mydata.putExtra("tanggal_masuk", mFilteredList?.get(position)?.tgljamIn)
            LocalBroadcastManager.getInstance(ctx!!).sendBroadcast(mydata)
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
        val durasi: TextView = itemView.findViewById<View>(R.id.durasi) as TextView
        val keperluan: TextView = itemView.findViewById<View>(R.id.keperluan) as TextView
    }

    override fun onClick(v: View?) {

    }

}