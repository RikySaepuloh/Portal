package com.saku.portalsatpam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saku.portalsatpam.KeperluanActivity
import com.saku.portalsatpam.R
import com.saku.portalsatpam.vibrate


class MenuAdapter(private val menus: ArrayList<String>) : RecyclerView.Adapter<MenuAdapter.NamaKelompokViewHolder>() {
    private var ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamaKelompokViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.gridview_items, parent, false)
        ctx = parent.context
        return NamaKelompokViewHolder(view)
    }

    override fun onBindViewHolder(holder: NamaKelompokViewHolder, position: Int) {
        holder.menu.text = menus[position]
        holder.menu.setOnClickListener {
            //ctx?.//vibrate(longArrayOf(0, 350))
            val intent = Intent(ctx, KeperluanActivity::class.java)
            intent.putExtra("menu",menus[position])
            ctx?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    inner class NamaKelompokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menu: TextView = itemView.findViewById<View>(R.id.judulmenu) as TextView
    }

}