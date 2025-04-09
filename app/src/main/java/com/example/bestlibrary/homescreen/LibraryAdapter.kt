package com.example.bestlibrary.homescreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bestlibrary.R
import com.example.library.Library

class LibraryAdapter(
    private val items: MutableList<Library>,
    private val onItemClick: (Library) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.object_item, parent, false)
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClick)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = items.size

    class LibraryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.item_title)
        private val idText: TextView = view.findViewById(R.id.item_id)
        private val image: ImageView = view.findViewById(R.id.item_image)

        fun bind(item: Library, onItemClick: (Library) -> Unit) {
            title.text = item.name
            idText.text = "ID: ${item.id}"

            val resourceId = itemView.context.resources.getIdentifier(
                "o${item.id}", "drawable", itemView.context.packageName
            )
            if (resourceId != 0) {
                image.setImageResource(resourceId)
            } else {
                image.setImageResource(R.drawable.ic_launcher_foreground) // или запасная картинка
            }

            itemView.alpha = if (item.isAvailable) 1.0f else 0.3f
            itemView.elevation = if (item.isAvailable) 8f else 2f

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
