package com.example.dam24_25_projfinal

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.api.ApiConnections
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.Paginae
import com.example.dam24_25_projfinal.models.Utilizador
import com.example.dam24_25_projfinal.models.Utilizadore
import com.example.dam24_25_projfinal.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreatePageFragment : Fragment() {

    private lateinit var tituloEditText: EditText
    private lateinit var textoEditText: EditText

    /**
     * Funcao chamada ao criar a view para o fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_page, container, false)

        tituloEditText = view.findViewById(R.id.editTextTitulo)
        textoEditText = view.findViewById(R.id.editTextTexto)

        val userId = Preferences.getUser(requireContext())?.toIntOrNull()

        view.findViewById<View>(R.id.buttonCreatePage).setOnClickListener {
            userId?.let { createPage(it) }
        }

        return view
    }

    /**
     * Funcao para criar uma nova pagina
     * Recebe um userId que e o id do utilizador que esta logged in
     */
    private fun createPage(userId: Int) {
        val titulo = tituloEditText.text.toString()
        val texto = textoEditText.text.toString()

        if (TextUtils.isEmpty(titulo)) {
            Toast.makeText(requireContext(), "Título é obrigatório", Toast.LENGTH_SHORT).show()
            return
        }

        val token = Preferences.getToken(requireContext())
        val bearerToken = "Bearer $token"

        val pagina = Pagina(
            titulo = titulo,
            texto = texto,
            utilizador = userId,
            id = null
        )

        val paginae = Paginae(pagina)

        val call = RetrofitInitializer().ApiConnections().createPage(bearerToken, paginae)

        call.enqueue(object : Callback<Paginae> {
            override fun onResponse(call: Call<Paginae>, response: Response<Paginae>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Página criada com sucesso!", Toast.LENGTH_SHORT).show()
                    val fragmentTransaction = parentFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout, ProfileFragment())
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                } else {
                    Toast.makeText(requireContext(), "Erro ao criar página", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Paginae>, t: Throwable) {
                Toast.makeText(requireContext(), "Falha na conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
