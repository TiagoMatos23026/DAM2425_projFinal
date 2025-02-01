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
    private lateinit var txtPaginas: TextView
    private lateinit var btnSair: Button

    val token = Preferences.getToken(requireContext())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        txtUsername = view.findViewById(R.id.txtUsername)
        txtBiografia = view.findViewById(R.id.txtBiografia)
        btnSair = view.findViewById(R.id.btnSair)

        // Obter o userId das SharedPreferences
        val userId = Preferences.getUser(requireContext())?.toIntOrNull()

        // Chamar a API para obter os detalhes do utilizador, se o userId for válido
        userId?.let { fetchUserProfile(it) }

        // Configurar o botão "Sair" para limpar o token e redirecionar
        btnSair.setOnClickListener {
            logOutUser()
        }

        return view
    }

    private fun fetchUserProfile(userId: Int) {
        val token = "Bearer $token"
        val apiService = RetrofitInitializer().ApiConnections()

        apiService.getUserById(token, userId).enqueue(object : Callback<Utilizadore?> {
            override fun onResponse(call: Call<Utilizadore?>, response: Response<Utilizadore?>) {
                val user = response.body()?.utilizadore
                if (user != null) {
                    txtUsername.text = user.user
                    txtBiografia.text = user.biografia
                } else {
                    Log.e("ProfileFragment", "Erro ao buscar perfil do utilizador")
                }
            }

            override fun onFailure(call: Call<Utilizadore?>, t: Throwable) {
                Log.e("ProfileFragment", "Erro na chamada à API: ${t.message}")
            }
        })
    }

    // Função para limpar o token e fazer logout
    private fun logOutUser() {
        Preferences.clearToken(requireContext()) // Limpar o token das preferências
        Preferences.setUser(requireContext(), "") // Limpar o userId

        // Redirecionar para a MainActivity (ou LoginActivity, caso queira)
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)

        // Finalizar a atividade atual (opcional)
        activity?.finish()

        Log.d("ProfileFragment", "Usuário deslogado com sucesso.")
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
