package com.example.dam24_25_projfinal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dam24_25_projfinal.adapters.PaginaAdapter
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.PaginasResponse
import com.example.dam24_25_projfinal.utils.Preferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PaginaAdapter
    private lateinit var progressBar: View
    private var arrayPagina: Array<Pagina>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)  // Referência ao ProgressBar
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getPaginas()

        return view
    }

    private fun getPaginas() {
        progressBar.visibility = View.VISIBLE  // Mostra o loading

        val token = Preferences.getToken(requireContext())
        val retrofitData = RetrofitInitializer().ApiConnections().getAllPages("Bearer $token")

        retrofitData.enqueue(object : Callback<PaginasResponse?> {
            override fun onResponse(call: Call<PaginasResponse?>, response: Response<PaginasResponse?>) {
                progressBar.visibility = View.GONE  // Esconde o loading

                val responseBody = response.body()
                if (responseBody != null) {
                    arrayPagina = responseBody.paginas

                    adapter = PaginaAdapter(arrayPagina!!.toList(), requireActivity())
                    recyclerView.adapter = adapter
                } else {
                    Log.d("HomeFragment", "Resposta vazia ou erro ao buscar páginas")
                }
            }

            override fun onFailure(call: Call<PaginasResponse?>, t: Throwable) {
                progressBar.visibility = View.GONE  // Esconde o loading mesmo em caso de erro
                Log.d("HomeFragment", "Erro na API: ${t.message}")
            }
        })
    }
}

