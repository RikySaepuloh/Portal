package com.saku.portalsatpam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saku.portalsatpam.R
import com.saku.portalsatpam.models.ModelTamu

class TamuAdapter(private val data: ArrayList<ModelTamu>) : RecyclerView.Adapter<TamuAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_tamu, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
//        val pos = position+1
//        holder.no.text = pos.toString()
//        if(data[position].keperluan=="MKKN"){
//            holder.keperluan.text = "MAKANAN"
//        }else{
//            holder.keperluan.text = data[position].keperluan
//        }
//        holder.tujuan.text = data[position].rumah
//        holder.kode.text = data[position].idTamu
//        holder.durasi.text = data[position].selisih
//        holder.card.setOnClickListener {
////            ctx?.//vibrate(longArrayOf(0, 350))
////            Toast.makeText(ctx!!,"Sentuh icon search diatas untuk memunculkan Menu",Toast.LENGTH_LONG).show()
////            ctx?.//vibrate(longArrayOf(0, 350))
////            val intent = Intent(ctx, IdentitasActivity::class.java)
////            ctx?.startActivity(intent)
//        }
        
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class NamaKelompokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val card: LinearLayout = itemView.findViewById<View>(R.id.data_layout) as LinearLayout
////        val no: TextView = itemView.findViewById<View>(R.id.no) as TextView
//        val kode: TextView = itemView.findViewById<View>(R.id.kode) as TextView
//        val durasi: TextView = itemView.findViewById<View>(R.id.durasi) as TextView
//        val tujuan: TextView = itemView.findViewById<View>(R.id.bs_tujuan) as TextView
//        val keperluan: TextView = itemView.findViewById<View>(R.id.bs_keperluan) as TextView
    }

}