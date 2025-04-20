package com.example.bestlibrary.librariesclass.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestlibrary.databinding.FragmentLibraryListBinding
import com.example.bestlibrary.librariesclass.adapters.LibraryAdapter
import com.example.bestlibrary.librariesclass.data.LibraryRepository
import com.example.library.Library
import kotlinx.coroutines.launch

class LibraryListFragment : Fragment() {

    private var _binding: FragmentLibraryListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LibraryAdapter
    private var listener: OnLibraryItemClickListener? = null
    private var scrollPosition = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnLibraryItemClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnLibraryItemClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val items = LibraryRepository.getItems()
                adapter = LibraryAdapter(items.toMutableList()) { item ->
                    listener?.onLibraryItemClick(item)
                }
                binding.recyclerView.adapter = adapter
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка при загрузке данных: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            listener?.onAddNewItem()
        }

        binding.recyclerView.scrollToPosition(scrollPosition)

        parentFragmentManager.setFragmentResultListener("item_added", viewLifecycleOwner) { _, _ ->
            addLastItemAndScroll()
        }
    }

    override fun onPause() {
        super.onPause()
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        scrollPosition = layoutManager.findFirstVisibleItemPosition()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun addLastItemAndScroll() {
        val position = adapter.itemCount - 1
        adapter.notifyItemInserted(position)
        binding.recyclerView.scrollToPosition(position)
    }

    interface OnLibraryItemClickListener {
        fun onLibraryItemClick(item: Library)
        fun onAddNewItem()
    }
}
