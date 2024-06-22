package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myapplication.Database.AppDatabase
import com.example.myapplication.Database.DefaultRepository
import com.example.myapplication.Database.MdpService
import com.example.myapplication.Database.MockDB
import com.example.myapplication.Doctor.main_dokter
import com.example.myapplication.viewmodel.LoginViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private val ioScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository(baseContext)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        ioScope.launch {
            var hasil = Repository.getlogindb()
            if(hasil.size != 0){
                viewModel.loginUser(hasil[0].username, hasil[0].password) { result ->
                    when (result) {
                        is LoginViewModel.LoginResult.Success -> {
                            navigateToMainScreen(result.role, hasil[0].username)
                        }
                        is LoginViewModel.LoginResult.Error -> {

                        }
                    }
                }
            }
        }
        setContentView(R.layout.activity_main)
//        val intent = Intent(this, main_dokter::class.java)
//        startActivity(intent)
//        this.deleteDatabase("proyek_mdp")
    }
    private fun navigateToMainScreen(role: String, username: String) {
        MockDB.usernamelogin = username
        val intent = when (role) {
            "patient" ->Intent(this, main_user::class.java)
            "doctor" -> Intent(this, main_dokter::class.java)
            else -> null
        }
        when (role) {
            "patient" ->{
                MockDB.userloginrole = 0
            }
            "doctor" -> {
                MockDB.userloginrole = 1
            }
            else -> null
        }
        intent?.putExtra("username", username)
        startActivity(intent!!)
        this?.finish()
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
                .baseUrl("https://test-qgvgl.run.goorm.site/")
                .build()

            Repository = DefaultRepository(
                roomDb,
                retrofit.create(MdpService::class.java)
            )
        }
    }
}