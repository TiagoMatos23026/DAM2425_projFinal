package com.example.dam24_25_projfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dam24_25_projfinal.models.Pagina

class PaginaDetalheFragment : Fragment() {

    /**
     * Funcao chamada ao criar a view para o fragmento
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pagina_detalhe, container, false)

        val tituloView: TextView = view.findViewById(R.id.txtTituloDetalhe)
        val textoView: TextView = view.findViewById(R.id.txtTextoDetalhe)

        val titulo = arguments?.getString("titulo") ?: "Sem título"
        val texto = arguments?.getString("texto") ?: "Sem conteúdo"

        tituloView.text = titulo
        textoView.text = texto

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
}