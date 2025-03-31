package com.example.bestlibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.library.Library

class LibraryAdapter(
    private val items: MutableList<Library>,
    private val onItemClick: (Library) -> Unit
) :
    RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.object_item, parent, false)
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class LibraryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.item_title)
        private val idText: TextView = view.findViewById(R.id.item_id)
        private val image: ImageView = view.findViewById(R.id.item_image)

        fun bind(item: Library) {
            title.text = item.name
            idText.text = "ID: ${item.id}"

            val resourceId = itemView.context.resources.getIdentifier(
                "o${item.id}", "drawable", itemView.context.packageName
            )
            image.setImageResource(resourceId)

            val alphaValue = if (item.isAvailable) 1.0f else 0.3f
            itemView.alpha = alphaValue
            itemView.elevation = if (item.isAvailable) 10f else 1f

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}

