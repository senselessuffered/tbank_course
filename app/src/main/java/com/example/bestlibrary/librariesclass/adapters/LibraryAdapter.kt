package com.example.bestlibrary.librariesclass.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bestlibrary.databinding.ItemLibraryBinding
import com.example.library.Library

class LibraryAdapter(
    private val items: List<Library>,
    private val onClick: (Library) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    inner class LibraryViewHolder(val binding: ItemLibraryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding = ItemLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textViewName.text = item.name
        holder.binding.textViewAvailable.text = if (item.isAvailable) "Доступно" else "Занято"
        holder.binding.root.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}
