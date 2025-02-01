package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.databinding.ActivityMainBinding
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.PaginasResponse
import com.example.dam24_25_projfinal.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    lateinit var f1:HomeFragment


    /**
     * Funcao chamada ao iniciar a atividade
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (!Preferences.termsAccepted(this)) {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val token = Preferences.getToken(this)
        if (token != "segredo") {

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return
            } else {

            }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment(), "HOME_FRAGMENT")

        binding.bottomNavbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment(), "HOME_FRAGMENT")
                R.id.profile -> replaceFragment(ProfileFragment(), "PROFILE_FRAGMENT")
                R.id.add_page -> replaceFragment(CreatePageFragment(), "CREATEPAGE_FRAGMENT")

                else ->{}
            }
            true
        }


    }
    /**
    * Metodo para trocar de pagina
    */
    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, tag)
        fragmentTransaction.commit()
    }
}