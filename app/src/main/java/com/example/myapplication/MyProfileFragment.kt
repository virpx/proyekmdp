package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyProfileFragment : Fragment() {
    lateinit var container: FragmentContainerView
    private lateinit var logImage: ImageButton
    private lateinit var logouttext: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logImage = view.findViewById(R.id.imageButton3)
        logouttext = view.findViewById(R.id.textView28)

        logouttext.setOnClickListener(){
            container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_loginFragment)
        }
        logImage.setOnClickListener(){
            container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_loginFragment)
        }

        val navbuttom = view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navbuttom.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId){
                R.id.home -> {
                    // Handle home navigation
                    container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_homeFragment)
                    true
                }
                R.id.search -> {
                    // Handle dashboard navigation
                    container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_searchFragment)
                    true
                }
                R.id.profile -> {
                    // Handle notifications navigation
                    container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_myProfileFragment)
                    true
                }
                else -> false
            }
        }
    }
}