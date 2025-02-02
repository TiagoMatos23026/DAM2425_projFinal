package com.example.dam24_25_projfinal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dam24_25_projfinal.adapters.PaginaAdapter
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.PaginasResponse
import com.example.dam24_25_projfinal.models.Utilizador
import com.example.dam24_25_projfinal.models.Utilizadore
import com.example.dam24_25_projfinal.models.UtilizadoresResponse
import com.example.dam24_25_projfinal.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header

class ProfileFragment : Fragment() {

    private lateinit var txtUsername: TextView
    private lateinit var txtBiografia: TextView
    private lateinit var btnSair: Button
    private lateinit var btnEliminarConta: Button
    private lateinit var progressBar: View
    private lateinit var profileContent: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaginaAdapter

    private val paginasDoUtilizador = mutableListOf<Pagina>()

    /**
     * Funcao chamada ao criar a view para o fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        txtUsername = view.findViewById(R.id.txtUsername)
        txtBiografia = view.findViewById(R.id.txtBiografia)
        btnSair = view.findViewById(R.id.btnSair)
        btnEliminarConta = view.findViewById(R.id.btnEliminarConta)
        progressBar = view.findViewById(R.id.progressBar)
        profileContent = view.findViewById(R.id.profileContent)
        recyclerView = view.findViewById(R.id.recyclerViewPaginas)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val userId = Preferences.getUser(requireContext())?.toIntOrNull()
        userId?.let { fetchUserProfile(it) }

        btnSair.setOnClickListener { logOutUser() }
        btnEliminarConta.setOnClickListener { confirmDeleteUser() }

        getPaginas()

        return view
    }

    private fun fetchUserProfile(userId: Int) {
        progressBar.visibility = View.VISIBLE
        profileContent.visibility = View.GONE

        val token = Preferences.getToken(requireContext())
        val call = RetrofitInitializer().ApiConnections()

        call.getUserById("Bearer $token", userId).enqueue(object : Callback<Utilizadore?> {
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

    private fun getPaginas() {
        progressBar.visibility = View.VISIBLE

        val token = Preferences.getToken(requireContext())
        val userId = Preferences.getUser(requireContext())?.toIntOrNull() ?: return

        val retrofitData = RetrofitInitializer().ApiConnections().getAllPages("Bearer $token")

        retrofitData.enqueue(object : Callback<PaginasResponse?> {
            override fun onResponse(call: Call<PaginasResponse?>, response: Response<PaginasResponse?>) {
                progressBar.visibility = View.GONE

                val responseBody = response.body()
                if (responseBody != null) {
                    val paginasFiltradas = responseBody.paginas.filter { it.utilizador == userId }

                    paginasDoUtilizador.clear()
                    paginasDoUtilizador.addAll(paginasFiltradas) // Guardar páginas no array

                    if (paginasFiltradas.isNotEmpty()) {
                        adapter = PaginaAdapter(paginasFiltradas, requireActivity())
                        recyclerView.adapter = adapter
                    } else {
                        Log.d("ProfileFragment", "Nenhuma página encontrada para este utilizador")
                    }
                } else {
                    Log.d("ProfileFragment", "Resposta vazia ou erro ao buscar páginas")
                }
            }

            override fun onFailure(call: Call<PaginasResponse?>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.d("ProfileFragment", "Erro na API: ${t.message}")
            }
        })
    }

    private fun confirmDeleteUser() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Confirmar exclusão")
            setMessage("Tem a certeza de que deseja apagar a sua conta? Todas as suas páginas serão removidas permanentemente.")
            setPositiveButton("Sim") { _, _ -> deleteAllUserPages() }
            setNegativeButton("Não") { dialog, _ -> dialog.dismiss() }
            show()
        }
    }

    private fun deleteAllUserPages() {
        if (paginasDoUtilizador.isEmpty()) {
            deleteUser()
            return
        }

        progressBar.visibility = View.VISIBLE
        val token = Preferences.getToken(requireContext())
        val bearerToken = "Bearer $token"

        var pagesDeleted = 0
        paginasDoUtilizador.forEach { pagina ->
            val call = RetrofitInitializer().ApiConnections().deletePagina(bearerToken, pagina.id ?: return@forEach)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        pagesDeleted++
                        Log.d("ProfileFragment", "Página ${pagina.id} excluída.")
                        if (pagesDeleted == paginasDoUtilizador.size) {
                            deleteUser()
                        }
                    } else {
                        Log.e("ProfileFragment", "Erro ao excluir página ${pagina.id}: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("ProfileFragment", "Erro na API ao excluir página ${pagina.id}: ${t.message}")
                }
            })
        }
    }

    private fun deleteUser() {
        val userId = Preferences.getUser(requireContext())?.toIntOrNull() ?: return
        val token = Preferences.getToken(requireContext())
        val bearerToken = "Bearer $token"

        val call = RetrofitInitializer().ApiConnections().deleteUser(bearerToken, userId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    Log.d("ProfileFragment", "Conta excluída com sucesso.")
                    Preferences.clearToken(requireContext())
                    Preferences.setUser(requireContext(), "")
                    logOutUser()
                } else {
                    Log.e("ProfileFragment", "Erro ao excluir a conta: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Erro ao excluir conta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("ProfileFragment", "Erro na API: ${t.message}")
                Toast.makeText(requireContext(), "Falha ao conectar à API", Toast.LENGTH_SHORT).show()
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
