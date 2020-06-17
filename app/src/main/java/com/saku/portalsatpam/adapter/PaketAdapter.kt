package com.saku.portalsatpam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.saku.portalsatpam.IdentitasActivity
import com.saku.portalsatpam.R
import com.saku.portalsatpam.models.ModelPaket
import com.saku.portalsatpam.models.ModelTamu
import com.saku.portalsatpam.vibrate

class PaketAdapter(private val data: ArrayList<ModelPaket>) : RecyclerView.Adapter<PaketAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_paket, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        val pos = position+1
        holder.no.text = pos.toString()
        holder.penghuni.text = data[position].penghuni
        holder.tujuan.text = data[position].tujuan
        holder.kode.text = data[position].kode
        holder.card.setOnClickListener {
//            ctx?.//vibrate(longArrayOf(0, 350))
            Toast.makeText(ctx!!,"Sentuh icon search diatas untuk memunculkan Menu",Toast.LENGTH_LONG).show()
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