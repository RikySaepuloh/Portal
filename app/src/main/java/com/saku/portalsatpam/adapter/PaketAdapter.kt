package com.saku.portalsatpam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.saku.portalsatpam.R
import com.saku.portalsatpam.models.ModelPaket

class PaketAdapter(private val data: ArrayList<ModelPaket>) : RecyclerView.Adapter<PaketAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    private var arrData: ArrayList<ModelPaket>? = data
    private var clickable:Boolean = true
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_paket, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    fun add(mdata: ArrayList<ModelPaket>, clickable:Boolean){
        arrData=mdata
        this.clickable=clickable
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        val pos = position+1
        holder.no.text = pos.toString()
        holder.penghuni.text = arrData?.get(position)?.penghuni
        holder.tujuan.text = arrData?.get(position)?.noRumah
        holder.kode.text = arrData?.get(position)?.idPaket
        holder.card.setOnClickListener {
            if(this.clickable){
                val mydata = Intent("bottom_sheet_trigger")
                mydata.putExtra("trigger", true)
                mydata.putExtra("kode", arrData?.get(position)?.idPaket)
                mydata.putExtra("tujuan", arrData?.get(position)?.noRumah)
                mydata.putExtra("penghuni", arrData?.get(position)?.penghuni)
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
        return data.size
    }

    inner class NamaKelompokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: LinearLayout = itemView.findViewById<View>(R.id.data_layout) as LinearLayout
        val no: TextView = itemView.findViewById<View>(R.id.no) as TextView
        val kode: TextView = itemView.findViewById<View>(R.id.kode) as TextView
        val tujuan: TextView = itemView.findViewById<View>(R.id.tujuan) as TextView
        val penghuni: TextView = itemView.findViewById<View>(R.id.penghuni) as TextView
    }

}