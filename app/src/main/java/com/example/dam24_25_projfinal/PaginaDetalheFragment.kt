package com.example.dam24_25_projfinal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.Utilizadore
import com.example.dam24_25_projfinal.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaginaDetalheFragment : Fragment() {

    val token = Preferences.getToken(requireContext())
    private lateinit var txtAutor: TextView // Declare a TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pagina_detalhe, container, false)

        val tituloView: TextView = view.findViewById(R.id.txtTituloDetalhe)
        val textoView: TextView = view.findViewById(R.id.txtTextoDetalhe)
        txtAutor = view.findViewById(R.id.txtAutor) // Initialize txtAutor

        // Obter os argumentos passados
        val titulo = arguments?.getString("titulo") ?: "Sem título"
        val texto = arguments?.getString("texto") ?: "Sem conteúdo"

        // Definir os valores nas Views
        tituloView.text = titulo
        textoView.text = texto

        // Suponha que você tenha um userId que pode ser passado ou obtido de alguma outra forma
        //val userId = 123 // Exemplo de userId, substitua com o valor real
        //fetchUser(userId)

        return view
    }

    companion object {
        fun newInstance(pagina: Pagina): PaginaDetalheFragment {
            val fragment = PaginaDetalheFragment()
            val args = Bundle()
            args.putString("titulo", pagina.titulo)
            args.putString("texto", pagina.texto)
            fragment.arguments = args
            return fragment
        }
    }

    /*
    private fun fetchUser(userId: Int) {
        val token = "Bearer $token"
        val apiService = RetrofitInitializer().ApiConnections()

        apiService.getUserById(token, userId).enqueue(object : Callback<Utilizadore?> {
            override fun onResponse(call: Call<Utilizadore?>, response: Response<Utilizadore?>) {
                val user = response.body()?.utilizadore
                if (user != null) {
                    txtAutor.text = user.user // Atualize o texto da TextView
                } else {
                    Log.e("PaginaDetalheFragment", "Erro ao buscar autor")
                }
            }

            override fun onFailure(call: Call<Utilizadore?>, t: Throwable) {
                Log.e("PaginaDetalheFragment", "Erro na chamada à API: ${t.message}")
            }
        })
    }

     */
}

