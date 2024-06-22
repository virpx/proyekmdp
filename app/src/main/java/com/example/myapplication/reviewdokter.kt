package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.Database.MockDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class reviewdokter : Fragment() {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val repository = main_user.Repository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviewdokter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(MockDB.currenthchat)
        view.findViewById<Button>(R.id.button6).setOnClickListener {
            var reviewuser = view.findViewById<TextView>(R.id.editTextText3).text.toString()
            var ratinguser =
                view.findViewById<TextView>(R.id.editTextNumberDecimal2).text.toString()
            if (reviewuser != "" && ratinguser != "") {
                var ratinguserr =
                    view.findViewById<TextView>(R.id.editTextNumberDecimal2).text.toString()
                        .toFloat()
                if (ratinguserr >= 1 && ratinguserr <= 5) {
                    ioScope.launch {
                        repository.reviewdoktere(
                            MockDB.usernamechatopen, MockDB.usernamelogin,
                            MockDB.currenthchat.id,
                            Sendreviewdokter(reviewuser, ratinguserr)
                        )
                        requireActivity().runOnUiThread {
                            findNavController().navigate(R.id.action_global_historyKesimpulanFragment)
                        }
                    }
                }
            }
        }
    }
}