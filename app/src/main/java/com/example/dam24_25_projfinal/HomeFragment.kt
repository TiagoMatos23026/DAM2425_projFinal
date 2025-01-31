package com.example.dam24_25_projfinal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.dam24_25_projfinal.api.RetrofitInitializer
import com.example.dam24_25_projfinal.models.Pagina
import com.example.dam24_25_projfinal.models.PaginasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//private const val ARG_PAGINAS = "paginas"
var arrayPagina: Array<Pagina>? = null

class HomeFragment : Fragment() {

    //private var paginas: Array<Pagina>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayPagina = getPaginas()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val txt: TextView = view.findViewById(R.id.txtpages)

        txt.text = "A Carregar..."
        val oi = arrayPagina

        /*
        if (arrayPagina != null) {
            val pageTitles = paginas!!.joinToString("\n") { it.titulo ?: "" }
            txt.text = pageTitles
        } else {
            txt.text = "No pages received"
        }
        */


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(paginas: Array<Pagina>) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    //putParcelableArray(ARG_PAGINAS, paginas)
                }
            }
    }

    private fun getPaginas():Array<Pagina>? {
        val retrofitData = RetrofitInitializer().ApiConnections().getAllPages()
        var arrayPagina: Array<Pagina>? = null
        retrofitData.enqueue(object : Callback<PaginasResponse?> {
            //se tiver resposta da api
            override fun onResponse(call: Call<PaginasResponse?>, response: Response<PaginasResponse?>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    // Quando a resposta chega, atualizamos a UI
                    val arrayPagina = responseBody.paginas

                    // Agora, atualizamos a UI com os dados recebidos
                    view?.findViewById<TextView>(R.id.txtpages)?.text =
                        arrayPagina.joinToString("\n") { it.texto ?: "No Title" }

                    /**
                     * Chamar func getUtilizadorById
                     */

                } else {
                    // Se não houver resposta válida, mostrar mensagem de erro
                    view?.findViewById<TextView>(R.id.txtpages)?.text = "No pages received"
                }

            }
            //se não tiver resposta da api
            override fun onFailure(call: Call<PaginasResponse?>, t: Throwable) {
                Log.d("HomeFragment", "onFailure: " +t.message)
            }
        })

        return arrayPagina
    }


}