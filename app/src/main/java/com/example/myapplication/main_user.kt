package com.example.myapplication

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class main_user : AppCompatActivity() {
    lateinit var container: FragmentContainerView
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository(baseContext)
        setContentView(R.layout.activity_main_user)
        val activeUser = intent.getStringExtra("username")
        Companion.activeUser = activeUser

        container = findViewById(R.id.fragmentContainerView4)
        container.getFragment<Fragment>().findNavController().navigate(R.id
            .action_global_homeFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationViewUser)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    container.getFragment<Fragment>().findNavController().navigate(R.id
                        .action_global_homeFragment)
                    true
                }
                R.id.search -> {
                    container.getFragment<Fragment>().findNavController().navigate(R.id
                        .action_global_searchFragment)
                    true
                }
                R.id.profile-> {
                    container.getFragment<Fragment>().findNavController().navigate(R.id
                        .action_global_myProfileFragment)
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
                .baseUrl("http://10.10.2.195:3000/")
                .build()

            Repository = DefaultRepository(
                roomDb,
                retrofit.create(MdpService::class.java)
            )
        }
    }
}