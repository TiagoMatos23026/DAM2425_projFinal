package com.example.dam24_25_projfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dam24_25_projfinal.api.Client
import com.example.dam24_25_projfinal.models.PaginaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PagesAdapter  // Precisas de um adapter para a RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = PagesAdapter(emptyList()) // Inicializa com lista vazia
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPaginas()  // Chama a API quando a UI estiver pronta
    }

    private fun fetchPaginas() {
        Client.instance.getAllPages().enqueue(object : Callback<List<Page>> {
            override fun onResponse(call: Call<List<Page>>, response: Response<List<Page>>) {
                if (response.isSuccessful) {
                    response.body()?.let { pages ->
                        adapter.updateData(pages) // Atualiza a RecyclerView com os dados recebidos
                    }
                } else {
                    Toast.makeText(requireContext(), "Erro ao carregar páginas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Page>>, t: Throwable) {
                Toast.makeText(requireContext(), "Falha na conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
