package com.saku.portalsatpam.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saku.portalsatpam.IdentitasActivity
import com.saku.portalsatpam.R
import com.saku.portalsatpam.vibrate

class PenghuniAdapter(private val menus: ArrayList<String>, private val gambar:ArrayList<String>) : RecyclerView.Adapter<PenghuniAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.penghuni_items, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        holder.name.text = menus[position]
        Glide.with(ctx!!).load(gambar[position]).into(holder.image)
        holder.card.setOnClickListener {
            ctx?.vibrate(longArrayOf(0, 350))
            val intent = Intent(ctx, IdentitasActivity::class.java)
            ctx?.startActivity(intent)
        }
        
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    inner class NamaKelompokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: LinearLayout = itemView.findViewById<View>(R.id.profile_card) as LinearLayout
        val name: TextView = itemView.findViewById<View>(R.id.profile_name) as TextView
        val image: ImageView = itemView.findViewById<View>(R.id.profile_image) as ImageView
    }

}