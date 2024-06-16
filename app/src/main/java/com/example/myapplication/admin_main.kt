package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class admin_main : AppCompatActivity() {
    lateinit var container: FragmentContainerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)
        container = findViewById(R.id.fragmentContainerView2)
        findViewById<BottomNavigationView>(R.id.bottomadmin).setOnItemSelectedListener{item->
            when(item.itemId){
                R.id.home_admin ->{
                    loadFragment(1)
                    true
                }
                R.id.l_user ->{
                    loadFragment(2)
                    true
                }
                R.id.l_artikel ->{
                    loadFragment(3)
                    true
                }
                R.id.acc_dokter ->{
                    loadFragment(4)
                    true
                }
                else -> false
            }
        }
    }
    private fun loadFragment(kode:Int) {
        if(kode == 1){
            container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_admin_home)
        }else if(kode == 2){
            container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_admin_list_user)
        }else if(kode == 3){
            container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_admin_list_artikel)
        }else {
            container.getFragment<Fragment>().findNavController()
                .navigate(R.id.action_global_admin_list_regis_dokter)
        }
    }
}