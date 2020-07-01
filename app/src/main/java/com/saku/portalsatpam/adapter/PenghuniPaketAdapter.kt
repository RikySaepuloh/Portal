package com.saku.portalsatpam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.saku.portalsatpam.R
import com.saku.portalsatpam.models.ModelPenghuni
import java.util.*
import kotlin.collections.ArrayList

class PenghuniPaketAdapter(private val rawData: ArrayList<ModelPenghuni>) : RecyclerView.Adapter<PenghuniPaketAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    private var mFilteredList: ArrayList<ModelPenghuni>? = rawData
    private var mArrayList: ArrayList<ModelPenghuni>? = rawData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_rumah, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        holder.noRumah.text = mFilteredList?.get(position)!!.nama
        holder.card.setOnClickListener {
            val mymFilteredList = Intent("image_intent")
            mymFilteredList.putExtra("nama", mFilteredList?.get(position)!!.nama)
            mymFilteredList.putExtra("nik", mFilteredList?.get(position)!!.nik)
            LocalBroadcastManager.getInstance(ctx!!).sendBroadcast(mymFilteredList)
        }
        
    }

    override fun getItemCount(): Int {
        return mFilteredList!!.size
    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString().toLowerCase(Locale.ROOT)
                mFilteredList = if (charString.isEmpty()) {
                    mArrayList
                } else {
                    val filteredList: ArrayList<ModelPenghuni> = ArrayList()
                    for (dataRumah in mArrayList!!) {
                        if (
                            dataRumah.nama?.toLowerCase(Locale.ROOT)?.contains(charString)!!
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
                mFilteredList = filterResults.values as ArrayList<ModelPenghuni>?
                notifyDataSetChanged()

            }
        }
    }


    inner class NamaKelompokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: LinearLayout = itemView.findViewById<View>(R.id.card_layout) as LinearLayout
        val noRumah: TextView = itemView.findViewById<View>(R.id.no_rumah) as TextView
    }

}