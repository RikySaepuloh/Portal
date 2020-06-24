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
import com.saku.portalsatpam.models.ModelTunggu

class TungguAdapter(private val data: ArrayList<ModelTunggu>) : RecyclerView.Adapter<TungguAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_tunggu, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        val pos = position+1
        holder.no.text = pos.toString()
        holder.keperluan.text = data[position].keperluan
        holder.tujuan.text = data[position].tujuan
        holder.penghuni.text = data[position].penghuni
        holder.card.setOnClickListener {
            val mydata = Intent("bottom_sheet_trigger")
            mydata.putExtra("trigger", true)
            mydata.putExtra("tujuan",data[position].tujuan)
            mydata.putExtra("keperluan",data[position].keperluan)
            mydata.putExtra("penghuni",data[position].penghuni)
            LocalBroadcastManager.getInstance(ctx!!).sendBroadcast(mydata)
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
        val penghuni: TextView = itemView.findViewById<View>(R.id.penghuni) as TextView
        val tujuan: TextView = itemView.findViewById<View>(R.id.tujuan) as TextView
        val keperluan: TextView = itemView.findViewById<View>(R.id.keperluan) as TextView
    }

}