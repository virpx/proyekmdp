package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class admin_home : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ioScope.launch {
            var hasil = repository.admingetdashboard()
            requireActivity().runOnUiThread {
                view.findViewById<TextView>(R.id.textView30).text = hasil.jumlahuser.toString()
                view.findViewById<TextView>(R.id.textView33).text = hasil.jumlahartikel.toString()
                var xvalues = arrayListOf("January","February","March","April","May","June","July","August","September","October","November","December")
                val barChart: BarChart = view.findViewById(R.id.chart)
                barChart.axisRight.setDrawLabels(false)
                val entries = ArrayList<BarEntry>().apply {
                    add(BarEntry(0f, hasil.userperbulan[0].toFloat()))
                    add(BarEntry(1f, hasil.userperbulan[1].toFloat()))
                    add(BarEntry(2f, hasil.userperbulan[2].toFloat()))
                    add(BarEntry(3f, hasil.userperbulan[3].toFloat()))
                    add(BarEntry(4f, hasil.userperbulan[4].toFloat()))
                    add(BarEntry(5f, hasil.userperbulan[5].toFloat()))
                    add(BarEntry(6f, hasil.userperbulan[6].toFloat()))
                    add(BarEntry(7f, hasil.userperbulan[7].toFloat()))
                    add(BarEntry(8f, hasil.userperbulan[8].toFloat()))
                    add(BarEntry(9f, hasil.userperbulan[9].toFloat()))
                    add(BarEntry(10f, hasil.userperbulan[10].toFloat()))
                    add(BarEntry(11f, hasil.userperbulan[11].toFloat()))
                }
                val dataSet:BarDataSet = BarDataSet(entries,"")
                val barData:BarData = BarData(dataSet);
                barChart.setData(barData);
                barChart.xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(xvalues)
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    isGranularityEnabled = true
                }
            }
        }

        view.findViewById<ImageView>(R.id.imageView11).setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

    }
}