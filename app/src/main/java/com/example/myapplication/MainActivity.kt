package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.myapplication.Database.AppDatabase
import com.example.myapplication.Database.DefaultRepository
import com.example.myapplication.Database.MdpService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository(baseContext)
        setContentView(R.layout.activity_main)
//        val intent = Intent(this, main_dokter::class.java)
//        startActivity(intent)
    }

    companion object{
        lateinit var Repository: DefaultRepository
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
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("http://10.0.2.2:3000/")
                .build()

            Repository = DefaultRepository(
                roomDb,
                retrofit.create(MdpService::class.java)
            )
        }
    }
}