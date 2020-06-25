package com.saku.portalsatpam.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.saku.portalsatpam.DataPasserKeperluan
import com.saku.portalsatpam.NeinActivity
import com.saku.portalsatpam.R
import kotlinx.android.synthetic.main.fragment_menu_keperluan.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentKeperluan.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentKeperluan : Fragment() {
    private lateinit var myview: View
    private lateinit var mycontext : Context
    lateinit var dataPasser: DataPasserKeperluan

//    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_menu_keperluan, container, false)
        mycontext = context!!
        return myview
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as DataPasserKeperluan
    }

    fun passData(data: String){
        dataPasser.onDataPasserKeperluan(data,"keperluan")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if((activity as NeinActivity).keperluan!="-"){
            when((activity as NeinActivity).keperluan){
                "Paket" ->{
                    myview.card_paket.isPressed = true
                }
                "Makanan" ->{
                    myview.card_makanan.isPressed = true
                }
                "OJOL" ->{
                    myview.card_ojek.isPressed = true
                }
                "Tamu" ->{
                    myview.card_tamu.isPressed = true
                }
                "Jualan" ->{
                    myview.card_jualan.isPressed = true
                }
                "Teknisi" ->{
                    myview.card_teknisi.isPressed = true
                }
            }
//            myview..setText((activity as NeinActivity).tujuan)
        }
        myview.card_makanan.setOnClickListener {
            passData("Makanan")
            (activity as NeinActivity?)?.changeFragment(FragmentTujuan())
        }
        myview.card_jualan.setOnClickListener {
            passData("Jualan")
            (activity as NeinActivity?)?.changeFragment(FragmentTujuan())
        }
        myview.card_ojek.setOnClickListener {
            passData("OJOL")
            (activity as NeinActivity?)?.changeFragment(FragmentTujuan())
        }
        myview.card_paket.setOnClickListener {
            passData("Paket")
            (activity as NeinActivity?)?.changeFragment(FragmentTujuan())

        }
        myview.card_tamu.setOnClickListener {
            passData("Tamu")
            (activity as NeinActivity?)?.changeFragment(FragmentTujuan())
        }
        myview.card_teknisi.setOnClickListener {
            passData("Teknisi")
            (activity as NeinActivity?)?.changeFragment(FragmentTujuan())
        }

    }

        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentKeperluan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentKeperluan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
