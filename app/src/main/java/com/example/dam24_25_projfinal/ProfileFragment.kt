package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Utilizador
import com.example.dam24_25_projfinal.models.Utilizadore
import com.example.dam24_25_projfinal.models.UtilizadoresResponse
import com.example.dam24_25_projfinal.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var txtUsername: TextView
    private lateinit var txtBiografia: TextView
    private lateinit var btnSair: Button
    private lateinit var progressBar: View
    private lateinit var profileContent: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        txtUsername = view.findViewById(R.id.txtUsername)
        txtBiografia = view.findViewById(R.id.txtBiografia)
        btnSair = view.findViewById(R.id.btnSair)
        progressBar = view.findViewById(R.id.progressBar)
        profileContent = view.findViewById(R.id.profileContent)

        val userId = Preferences.getUser(requireContext())?.toIntOrNull()
        userId?.let { fetchUserProfile(it) }

        btnSair.setOnClickListener {
            logOutUser()
        }

        return view
    }

    private fun fetchUserProfile(userId: Int) {
        progressBar.visibility = View.VISIBLE
        profileContent.visibility = View.GONE

        val token = Preferences.getToken(requireContext())
        val apiService = RetrofitInitializer().ApiConnections()

        apiService.getUserById("Bearer $token", userId).enqueue(object : Callback<Utilizadore?> {
            override fun onResponse(call: Call<Utilizadore?>, response: Response<Utilizadore?>) {
                progressBar.visibility = View.GONE
                profileContent.visibility = View.VISIBLE

                val user = response.body()?.utilizadore
                if (user != null) {
                    txtUsername.text = user.user
                    txtBiografia.text = user.biografia
                } else {
                    Log.e("ProfileFragment", "Erro ao buscar perfil do utilizador")
                }
            }

            override fun onFailure(call: Call<Utilizadore?>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("ProfileFragment", "Erro na chamada à API: ${t.message}")
            }
        })
    }

    private fun logOutUser() {
        Preferences.clearToken(requireContext())
        Preferences.setUser(requireContext(), "")

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()

        Log.d("ProfileFragment", "Utilizador saiu.")
    }
}


