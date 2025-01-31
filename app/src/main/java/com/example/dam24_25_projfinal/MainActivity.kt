package com.example.dam24_25_projfinal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.databinding.ActivityMainBinding
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.PaginasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    lateinit var f1:HomeFragment



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

        replaceFragment(ProfileFragment(), "PROFILE_FRAGMENT")

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
    * Metodo para trocar de página
    */
    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, tag)
        fragmentTransaction.commit()
    }
/*
    private fun replaceFragmentHome(fragment: Fragment, tag: String) {
        getPaginas(HomeFragment(), "HOME_FRAGMENT")

    }

 */

    /**
     * Metodo para listar os dados da api
     */
    /*
    private fun getPaginas(fragment: Fragment, tag: String) {
        val retrofitData = RetrofitInitializer().ApiConnections().getAllPages()
        retrofitData.enqueue(object : Callback<PaginasResponse?> {

            override fun onResponse(call: Call<PaginasResponse?>, response: Response<PaginasResponse?>) {
                val responseBody = response.body()!!

                val arrayPaginas = responseBody.paginas


                f1 = HomeFragment.newInstance( paginas = arrayPaginas)

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.home, f1)
                fragmentTransaction.replace(R.id.frame_layout, fragment, tag)
                fragmentTransaction.commit()



            }


            override fun onFailure(call: Call<PaginasResponse?>, t: Throwable) {
                Log.d("HomeFragment", "onFailure: " +t.message)
            }


        })

    }

     */

}