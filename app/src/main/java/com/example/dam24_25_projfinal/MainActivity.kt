package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //Verifica se os termos foram aceites
        /*
        if (!SharedPreferencesHelper.hasAcceptedTerms(this)) {
            val intent = Intent(this, PrivacyPolicyAgreement::class.java)
            startActivity(intent)
            finish()
            return
        }

         */








        //implementacao de view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.add_page -> replaceFragment(CreatePageFragment())

                else ->{

                }
            }
            true
        }

    }
    /*
    Metodo para trocar de página
    */
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}