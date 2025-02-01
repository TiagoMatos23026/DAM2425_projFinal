package com.example.dam24_25_projfinal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.FragmentActivity
import com.example.dam24_25_projfinal.PaginaDetalheFragment
import com.example.dam24_25_projfinal.R
import com.example.dam24_25_projfinal.models.Pagina

class PaginaAdapter(private val paginas: List<Pagina>, private val activity: FragmentActivity) :
    RecyclerView.Adapter<PaginaAdapter.PaginaViewHolder>() {

    class PaginaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.txtTitulo)
        val texto: TextView = itemView.findViewById(R.id.txtTexto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaginaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pagina, parent, false)
        return PaginaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaginaViewHolder, position: Int) {
        val pagina = paginas[position]
        holder.titulo.text = pagina.titulo ?: "Sem título"
        holder.texto.text = pagina.texto ?: "Sem texto"

        // Evento de clique para abrir os detalhes
        holder.itemView.setOnClickListener {
            val fragment = PaginaDetalheFragment.newInstance(pagina)

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment) // Substituir o fragmento atual
                .addToBackStack(null) // Permite voltar ao anterior
                .commit()
        }
    }

    override fun getItemCount(): Int = paginas.size
}