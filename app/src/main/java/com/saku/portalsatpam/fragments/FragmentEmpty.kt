package com.saku.portalsatpam.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.saku.portalsatpam.R
import kotlinx.android.synthetic.main.fragment_empty.view.*

class FragmentEmpty(val params:String) : Fragment() {
    private lateinit var myview: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_empty, container, false)
        if(params=="paket"){
            myview.empty.text = "Tidak ada paket yang dipilih"
        }else{
            myview.empty.text = "Tidak ada tamu yang dipilih"
        }
        return myview
    }

}
