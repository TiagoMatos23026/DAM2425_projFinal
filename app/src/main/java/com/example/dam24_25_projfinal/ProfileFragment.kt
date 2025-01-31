package com.example.dam24_25_projfinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Utilizador
import com.example.dam24_25_projfinal.utils.Preferences
import okhttp3.Callback
import okhttp3.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    class ProfileFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_profile, container, false)
            carregarPerfil(view)
            return view
        }

        private fun carregarPerfil(view: View) {
            val userIdString = Preferences.getUser(requireContext())
            val userId = userIdString?.toIntOrNull()

            if (userId == null) {
                Toast.makeText(requireContext(), "Erro ao obter o ID do utilizador!", Toast.LENGTH_SHORT).show()
                return
            }

            val retrofitData = RetrofitInitializer().ApiConnections().getUserById(userId)

            retrofitData.enqueue(object : Callback<Utilizador> {
                override fun onResponse(call: Call<Utilizador>, response: Response<Utilizador>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            val usernameTextView = view.findViewById<TextView>(R.id.usernameTextView)
                            usernameTextView.text = user.user
                        } else {
                            mostrarErro("Erro: Utilizador não encontrado!")
                        }
                    } else {
                        Log.e("ProfileFragment", "Erro na resposta da API: ${response.errorBody()?.string()}")
                        mostrarErro("Erro ao carregar perfil!")
                    }
                }

                override fun onFailure(call: Call<Utilizador>, t: Throwable) {
                    Log.e("ProfileFragment", "Erro na API: ${t.message}")
                    mostrarErro("Erro de conexão com o servidor!")
                }
            })
        }

        private fun mostrarErro(mensagem: String) {
            Toast.makeText(requireContext(), mensagem, Toast.LENGTH_SHORT).show()
        }
    }
}