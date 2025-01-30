package com.example.dam24_25_projfinal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dam24_25_projfinal.R
import com.example.dam24_25_projfinal.models.Paginas

class PagesAdapter(private var pages: List<Paginas>) : RecyclerView.Adapter<PagesAdapter.PageViewHolder>() {

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvPageTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val page = pages[position]
        holder.tvTitle.text = page.titulo
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    fun updateData(newPages: List<Paginas>) {
        pages = newPages
        notifyDataSetChanged()  // Atualiza a lista na UI
    }
}
