package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class endchat : Fragment() {
    private val repository = MainActivity.Repository
    private val ioScope = CoroutineScope(Dispatchers.IO)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_endchat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var idhcat = endchatArgs?.fromBundle(arguments as Bundle)?.idhchat!!
        view.findViewById<Button>(R.id.button).setOnClickListener {
            var reviewuser = view.findViewById<TextView>(R.id.editTextText).text.toString()
            var ratinguser = view.findViewById<TextView>(R.id.editTextNumberDecimal).text.toString()
            if(reviewuser != "" && ratinguser != ""){
                var ratinguserr = view.findViewById<TextView>(R.id.editTextNumberDecimal).text.toString().toFloat()
                if(ratinguserr >=1 && ratinguserr<=5){
                    ioScope.launch {
                        repository.endchatdokter(idhcat,MockDB.usernamechatopen,MockDB.usernamelogin,reviewuser,ratinguserr)
                        requireActivity().runOnUiThread {
                            findNavController().navigate(R.id.action_global_fragment_user_listchat)
                        }
                    }
                }
            }
        }
    }
}