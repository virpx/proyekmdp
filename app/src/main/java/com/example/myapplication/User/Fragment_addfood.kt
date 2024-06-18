package com.example.myapplication.User

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Bodyaddfoodtrack
import com.example.myapplication.Classuniversal_hasilgizi
import com.example.myapplication.Database.MockDB
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.adminadapter_lartikel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.round

class fragment_addfood : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var adminadapter: adminadapter_lartikel
    lateinit var layoutManager: RecyclerView.LayoutManager
    var datagizi = Classuniversal_hasilgizi(0f,0f,0f,0f,0f,0f,0f)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addfood, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button8).setOnClickListener {
            var namamakanan = view.findViewById<TextView>(R.id.editTextText7).text.toString()
            var jumlahmakananstr = view.findViewById<TextView>(R.id.editTextNumber).text.toString()
            if(namamakanan != "" && jumlahmakananstr != ""){
                view.findViewById<Button>(R.id.button8).isVisible = false
                ioScope.launch {
                    Log.d("coba",namamakanan)
                    datagizi = repository.getgizidata(namamakanan)
                    requireActivity().runOnUiThread {
                        var jumlahmakanan = view.findViewById<TextView>(R.id.editTextNumber).text.toString().toFloat()
                        Toast.makeText(requireContext(), (datagizi.calories).toString(), Toast.LENGTH_SHORT).show()
                        view.findViewById<TextView>(R.id.editTextNumber7).text = (round(datagizi.calories*jumlahmakanan)).toString()
                        view.findViewById<TextView>(R.id.editTextNumber8).text = (round(datagizi.protein*jumlahmakanan)).toString()
                        view.findViewById<TextView>(R.id.editTextNumber9).text = (round(datagizi.sugar*jumlahmakanan)).toString()
                        view.findViewById<TextView>(R.id.editTextNumber10).text = (round(datagizi.carbs*jumlahmakanan)).toString()
                        view.findViewById<TextView>(R.id.editTextNumber11).text = (round(datagizi.fat*jumlahmakanan)).toString()
                        view.findViewById<TextView>(R.id.editTextNumber12).text = (round(datagizi.cholesterol*jumlahmakanan)).toString()
                        view.findViewById<TextView>(R.id.editTextNumber13).text = (round(datagizi.sodium*jumlahmakanan)).toString()
                        view.findViewById<Button>(R.id.button8).isVisible =true
                    }
                }
            }
        }
        view.findViewById<ImageView>(R.id.imageView).setOnClickListener {
            var namamakanan = view.findViewById<TextView>(R.id.editTextText7).text.toString()
            var jumlahmakananstr = view.findViewById<TextView>(R.id.editTextNumber).text.toString()
            if(jumlahmakananstr != "" && namamakanan != "" && view.findViewById<TextView>(R.id.editTextNumber7).text != "" && view.findViewById<TextView>(R.id.editTextNumber8).text != "" && view.findViewById<TextView>(R.id.editTextNumber9).text != "" && view.findViewById<TextView>(R.id.editTextNumber10).text != "" && view.findViewById<TextView>(R.id.editTextNumber11).text != "" && view.findViewById<TextView>(R.id.editTextNumber12).text != "" && view.findViewById<TextView>(R.id.editTextNumber13).text != ""){
                var jumlahmakanan = view.findViewById<TextView>(R.id.editTextNumber).text.toString().toInt()
                var kirim = Bodyaddfoodtrack(
                    namamakanan,
                    jumlahmakanan,
                    view.findViewById<TextView>(R.id.editTextNumber7).text.toString().toFloat().toInt(),
                    view.findViewById<TextView>(R.id.editTextNumber8).text.toString().toFloat().toInt(),
                    view.findViewById<TextView>(R.id.editTextNumber9).text.toString().toFloat().toInt(),
                    view.findViewById<TextView>(R.id.editTextNumber10).text.toString().toFloat().toInt(),
                    view.findViewById<TextView>(R.id.editTextNumber11).text.toString().toFloat().toInt(),
                    view.findViewById<TextView>(R.id.editTextNumber12).text.toString().toFloat().toInt(),
                    view.findViewById<TextView>(R.id.editTextNumber13).text.toString().toFloat().toInt()
                )
                ioScope.launch {
                    repository.tambahfoodtrack(MockDB.usernamelogin,kirim)
                    requireActivity().runOnUiThread {
                        view.findViewById<TextView>(R.id.editTextText7).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber7).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber8).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber9).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber10).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber11).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber12).text = ""
                        view.findViewById<TextView>(R.id.editTextNumber13).text = ""
                    }
                }
            }
        }
    }
}