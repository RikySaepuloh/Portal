package com.saku.portalsatpam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_selesai_masuk.view.*
import kotlinx.android.synthetic.main.fragment_tujuan.*
import kotlinx.android.synthetic.main.fragment_tujuan.view.*
import kotlinx.android.synthetic.main.fragment_tujuan.view.delete
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_0
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_1
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_2
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_3
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_4
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_5
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_6
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_7
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_8
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_9
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_a
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_b
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_c
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_d
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_e
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_slash
import kotlinx.android.synthetic.main.fragment_tujuan.view.tujuan

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTujuan.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSelesai : Fragment() {
//    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var myview: View
    lateinit var dataPasser: DataPasserKeperluan

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_selesai_masuk, container, false)
        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myview.selesai.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
            activity?.finishAffinity()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentTujuan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentTujuan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
