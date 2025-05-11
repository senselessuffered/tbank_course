package com.example.bestlibrary.librariesclass.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bestlibrary.R
import com.example.bestlibrary.databinding.ItemLibraryBinding
import com.example.bestlibrary.librariesclass.data.LibraryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibraryAdapter(
    internal val items: MutableList<LibraryEntity>,
    private val onClick: (LibraryEntity) -> Unit
) : RecyclerView.Adapter<LibraryAdapter.VH>() {

    inner class VH(val vb: ItemLibraryBinding) : RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vb = ItemLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(vb)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.vb) {
            root.alpha = if (item.isAvailable) 1f else 0.3f
            textViewName.text = item.name
            textViewAvailable.text =
                "Id: ${item.id} — ${if (item.isAvailable) "Доступно" else "Занято"}"

            val iconRes = when (item.type) {
                "Книга" -> R.drawable.book
                "Диск" -> R.drawable.disk
                "Газета" -> R.drawable.papper
                else -> R.drawable.ic_launcher_foreground
            }
            imageViewIcon.setImageResource(iconRes)

            root.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    onClick(item)
                }
            }
        }
    }

    override fun getItemCount() = items.size

    fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addAll(list: List<LibraryEntity>) {
        val s = items.size
        items.addAll(list)
        notifyItemRangeInserted(s, list.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }
}
