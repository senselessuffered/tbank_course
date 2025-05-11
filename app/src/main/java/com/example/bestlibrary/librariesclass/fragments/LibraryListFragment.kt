package com.example.bestlibrary.librariesclass.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestlibrary.databinding.FragmentLibraryListBinding
import com.example.bestlibrary.librariesclass.adapters.LibraryAdapter
import com.example.bestlibrary.librariesclass.data.LibraryEntity
import com.example.bestlibrary.librariesclass.data.LibraryRepository
import com.example.bestlibrary.librariesclass.util.PreferencesUtil
import kotlinx.coroutines.launch

class LibraryListFragment : Fragment() {
    private var _vb: FragmentLibraryListBinding? = null
    private val vb get() = _vb!!

    private lateinit var repo: LibraryRepository
    lateinit var adapter: LibraryAdapter

    private var isLoading = false
    private val pageSize = 30
    private var offset = 0
    private var sortBy = "date"
    private var listener: OnLibraryItemClickListener? = null

    interface OnLibraryItemClickListener {
        fun onLibraryItemClick(item: LibraryEntity)
        fun onAddNewItem()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnLibraryItemClickListener
            ?: throw RuntimeException("$context must implement OnLibraryItemClickListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentLibraryListBinding.inflate(inflater, container, false).also { _vb = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repo = LibraryRepository(requireContext())
        sortBy = PreferencesUtil.loadSort(requireContext())
        setupSort()
        setupRecycler()
        observeAddResult()
        showShimmerThenLoad()
    }

    private fun setupSort() {
        val opts = listOf("date", "name")
        vb.spinnerSort.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opts)
                .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        vb.spinnerSort.setSelection(opts.indexOf(sortBy), false)
        vb.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (view == null) return
                sortBy = opts[pos]
                PreferencesUtil.saveSort(requireContext(), sortBy)
                resetAndLoad()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun setupRecycler() {
        adapter = LibraryAdapter(mutableListOf()) { listener?.onLibraryItemClick(it) }
        val layoutManager = LinearLayoutManager(requireContext())
        vb.recyclerView.layoutManager = layoutManager
        vb.recyclerView.adapter = adapter

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                vb.recyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        })

        val touchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                tgt: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val pos = vh.adapterPosition
                val item = adapter.items[pos]
                lifecycleScope.launch {
                    repo.delete(item)
                    adapter.removeItem(pos)
                }
            }
        })
        touchHelper.attachToRecyclerView(vb.recyclerView)

        vb.fabAdd.setOnClickListener { listener?.onAddNewItem() }
    }

    private fun observeAddResult() {
        parentFragmentManager.setFragmentResultListener("item_added", viewLifecycleOwner) { _, _ ->
            resetAndLoad()
        }
    }

    private fun showShimmerThenLoad() {
        vb.shimmerView.isVisible = true
        vb.recyclerView.isVisible = false
        Handler(Looper.getMainLooper()).postDelayed({
            vb.shimmerView.stopShimmer()
            vb.shimmerView.isVisible = false
            vb.recyclerView.isVisible = true
            resetAndLoad()
        }, 1000)
    }

    private fun resetAndLoad() {
        offset = 0
        adapter.clear()
        loadNext()
    }

    private fun loadNext() {
        isLoading = true
        lifecycleScope.launch {
            val items = repo.getPage(sortBy, pageSize, offset)
            adapter.addAll(items)
            offset += items.size
            isLoading = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }
}
