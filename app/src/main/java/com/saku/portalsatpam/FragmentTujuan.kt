package com.saku.portalsatpam

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_tujuan.view.bs_tujuan

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTujuan.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTujuan : Fragment() {
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
        myview = inflater.inflate(R.layout.fragment_tujuan, container, false)
        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myview.bs_tujuan.setOnTouchListener { v, event ->
            v.onTouchEvent(event)
            val inputMethod: InputMethodManager = v.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            if (inputMethod != null) {
            inputMethod.hideSoftInputFromWindow(v.windowToken, 0)
//            }
            true
        }
        if((activity as NeinActivity).tujuan!="-"){
            myview.bs_tujuan.setText((activity as NeinActivity).tujuan)
        }
        myview.delete.setOnClickListener {
            myview.bs_tujuan.text.clear()
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_0.setOnClickListener {
            myview.bs_tujuan.text.append("0")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_1.setOnClickListener {
            myview.bs_tujuan.text.append("1")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_2.setOnClickListener {
            myview.bs_tujuan.text.append("2")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_3.setOnClickListener {
            myview.bs_tujuan.text.append("3")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_4.setOnClickListener {
            myview.bs_tujuan.text.append("4")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_5.setOnClickListener {
            myview.bs_tujuan.text.append("5")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_6.setOnClickListener {
            myview.bs_tujuan.text.append("6")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_7.setOnClickListener {
            myview.bs_tujuan.text.append("7")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_8.setOnClickListener {
            myview.bs_tujuan.text.append("8")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_9.setOnClickListener {
            myview.bs_tujuan.text.append("9")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_slash.setOnClickListener {
            myview.bs_tujuan.text.append("-")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_a.setOnClickListener {
            myview.bs_tujuan.text.append("A")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_b.setOnClickListener {
            myview.bs_tujuan.text.append("B")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_c.setOnClickListener {
            myview.bs_tujuan.text.append("C")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_d.setOnClickListener {
            myview.bs_tujuan.text.append("D")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_e.setOnClickListener {
            myview.bs_tujuan.text.append("E")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.selanjutnya.setOnClickListener {
            if(myview.bs_tujuan.text.toString()==""||myview.bs_tujuan.text.toString()==null||myview.bs_tujuan.text.toString().isEmpty()||myview.bs_tujuan.text.toString().length<4){
                Toast.makeText(context,"Masukkan tujuan terlebih dahulu!",Toast.LENGTH_LONG).show()
            }else{
                passData(myview.bs_tujuan.text.toString())
                (activity as NeinActivity?)?.changeFragment(FragmentPenghuni())
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as DataPasserKeperluan
    }

    fun passData(data: String){
        dataPasser.onDataPasserKeperluan(data,"tujuan")
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
