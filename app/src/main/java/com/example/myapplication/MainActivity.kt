package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.myapplication.Database.AppDatabase
import com.example.myapplication.Database.DefaultRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository(baseContext)
        setContentView(R.layout.activity_main)
    }

    companion object{
        lateinit var Repository: DefaultRepository

        fun initRepository(context: Context){
////            context.deleteDatabase("mdpinf20232m10")
            val roomDb = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "proyek_mdp"
            ).fallbackToDestructiveMigration().build()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(MoshiConverterFactory.create(moshi))
//                .baseUrl("http://10.0.2.2:3000")
//                .build()

            Repository = DefaultRepository(
                roomDb,
//                retrofit.create(MdpService::class.java)
            )
        }
    }
}