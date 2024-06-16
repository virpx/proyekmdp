package com.example.myapplication

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class admin_list_user : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var rvne: RecyclerView
    lateinit var mhsAdapter: admin
    lateinit var layoutManager: LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView38).setOnClickListener {
            view.findViewById<TextView>(R.id.textView38).setTypeface(null,Typeface.BOLD)
            view.findViewById<TextView>(R.id.textView37).setTypeface(null,Typeface.NORMAL)
        }
        view.findViewById<TextView>(R.id.textView37).setOnClickListener {
            view.findViewById<TextView>(R.id.textView38).setTypeface(null,Typeface.NORMAL)
            view.findViewById<TextView>(R.id.textView37).setTypeface(null,Typeface.BOLD)
        }
        rvne.findViewById<RecyclerView>(R.id.rvlistuser)
    }
}