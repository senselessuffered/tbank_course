package com.example.bestlibrary.librariesclass.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bestlibrary.databinding.ItemLibraryBinding
import com.example.library.Library
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class LibraryAdapter(
    internal val items: List<Library>,
    private val onClick: (Library) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    inner class LibraryViewHolder(val binding: ItemLibraryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding = ItemLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = items[position]
        holder.binding.imageViewIcon.setImageResource(
            holder.itemView.context.resources.getIdentifier(
                item::class.simpleName?.lowercase(),
                "drawable",
                holder.itemView.context.packageName
            )
        )
        holder.binding.textViewName.text = item.name
        holder.binding.textViewAvailable.text =
            "Id: ${item.id} - ${if (item.isAvailable) "Доступно" else "Занято"}"
        holder.binding.root.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                delay((500..1500).random().toLong())
                val err = Random.nextInt(4) == 0

                if (err) {
                    Toast.makeText(
                        holder.itemView.context,
                        "Ошибка при открытии",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    onClick(item)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}
