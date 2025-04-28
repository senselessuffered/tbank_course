package com.example.bestlibrary.librariesclass.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.bestlibrary.databinding.FragmentLibraryDetailBinding
import com.example.bestlibrary.librariesclass.data.LibraryEntity
import com.example.bestlibrary.librariesclass.data.LibraryRepository
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class LibraryDetailFragment : Fragment() {
    private var _vb: FragmentLibraryDetailBinding? = null
    private val vb get() = _vb!!
    private var item: LibraryEntity? = null
    private lateinit var repo: LibraryRepository

    companion object {
        private const val ARG = "arg_item"
        fun newInstance(item: LibraryEntity?) =
            LibraryDetailFragment().apply {
                arguments = Bundle().apply { putSerializable(ARG, item) }
            }
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?) =
        FragmentLibraryDetailBinding.inflate(i, c, false).also { _vb = it }.root

    override fun onViewCreated(v: View, s: Bundle?) {
        repo = LibraryRepository(requireContext())
        item = arguments?.getSerializable(ARG) as? LibraryEntity
        if (item != null) showDetails() else showForm()
    }

    @SuppressLint("SetTextI18n")
    private fun showDetails() {
        vb.groupDetails.visibility = View.VISIBLE
        vb.groupAddForm.visibility = View.GONE
        vb.textViewDetail.text =
            "${item!!.type}: ${item!!.name} — ${if (item!!.isAvailable) "Доступно" else "Занято"}"
    }

    private fun showForm() {
        vb.groupDetails.visibility = View.GONE
        vb.groupAddForm.visibility = View.VISIBLE
        val types = listOf("Книга", "Диск", "Газета")
        vb.spinnerType.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
                .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        vb.buttonAdd.setOnClickListener {
            val name = vb.editName.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Введите название", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val type = vb.spinnerType.selectedItem as String
            val id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
            val now = System.currentTimeMillis()
            val extra = when (type) {
                "Книга" -> "pages=100;author=Автор"
                "Диск" -> "diskType=CD"
                else -> "month=1;papperNumber=1"
            }
            val newItem = LibraryEntity(id, type, name, extra, now, true)
            lifecycleScope.launch {
                repo.add(newItem)
                parentFragmentManager.setFragmentResult("item_added", Bundle())
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}
