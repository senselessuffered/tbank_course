package com.example.bestlibrary.librariesclass.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestlibrary.databinding.FragmentLibraryListBinding
import com.example.bestlibrary.librariesclass.adapters.LibraryAdapter
import com.example.bestlibrary.librariesclass.data.LibraryRepository
import com.example.library.Library


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

        adapter = LibraryAdapter(LibraryRepository.getItems()) { item ->
            listener?.onLibraryItemClick(item)
        }

        binding.recyclerView.adapter = adapter

        // Кнопка "добавить"
        binding.fabAdd.setOnClickListener {
            listener?.onAddNewItem()
        }

        // Восстановим позицию скролла
        binding.recyclerView.scrollToPosition(scrollPosition)
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

    interface OnLibraryItemClickListener {
        fun onLibraryItemClick(item: Library)
        fun onAddNewItem()
    }
}
