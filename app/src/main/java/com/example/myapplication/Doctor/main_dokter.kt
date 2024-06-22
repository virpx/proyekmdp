package com.example.myapplication.Doctor

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.myapplication.Database.AppDatabase
import com.example.myapplication.Database.DefaultRepository
import com.example.myapplication.Database.MdpService
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class main_dokter : AppCompatActivity() {
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    lateinit var container: FragmentContainerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository(baseContext)
        setContentView(R.layout.activity_main_dokter)
        val activeUser = intent.getStringExtra("username")
        Companion.activeUser = activeUser

        container = findViewById(R.id.fragmentContainerView3)
        container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_homeDocterFragment2)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView3)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dokter_home -> {
                    container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_homeDocterFragment2)
                    true
                }
                R.id.dokter_profile -> {
                    container.getFragment<Fragment>().findNavController().navigate(R.id.action_global_profileDocterFragment)
                    true
                }
                else -> false
            }
        }
    }

    companion object{
        lateinit var Repository: DefaultRepository
        var activeUser: String? = null
        fun initRepository(context: Context){
////            context.deleteDatabase("proyek_mdp")
            val roomDb = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "proyek_mdp"
            ).build()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://test-qgvgl.run.goorm.site/")
                .build()

            Repository = DefaultRepository(
                roomDb,
                retrofit.create(MdpService::class.java)
            )
        }
    }
}