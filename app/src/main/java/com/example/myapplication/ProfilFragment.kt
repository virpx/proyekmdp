package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs


class ProfilFragment : Fragment() {

    private val args: ProfilFragmentArgs by navArgs()
    private lateinit var nameTextView: TextView
    private lateinit var fl: TextView
    private lateinit var sk: TextView
    private lateinit var lm: TextView
    private lateinit var sp: TextView
    private lateinit var em: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.textView67)
        fl = view.findViewById(R.id.textView73)
        sk = view.findViewById(R.id.textView74)
        lm = view.findViewById(R.id.textView75)
        sp = view.findViewById(R.id.textView76)
        em = view.findViewById(R.id.textView77)
        val username = args.username
        val fullname = args.fullname
        val sekolah = args.sekolah
        val email = args.email
        val specialist = args.specialist
        val lamaPraktik = args.lamaPraktik
        nameTextView.text = username
        fl.text = fullname
        sk.text = sekolah
        lm.text = lamaPraktik.toString()
        sp.text = specialist
        em.text = email
    }
}