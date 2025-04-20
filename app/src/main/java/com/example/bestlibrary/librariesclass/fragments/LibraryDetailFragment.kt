package com.example.bestlibrary.librariesclass.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.bestlibrary.R
import com.example.bestlibrary.databinding.FragmentLibraryDetailBinding
import com.example.bestlibrary.librariesclass.data.LibraryRepository
import com.example.library.Book
import com.example.library.Disk
import com.example.library.Library
import com.example.library.Papper
import com.example.library.com.example.library.Months


@Suppress("DEPRECATION")
class LibraryDetailFragment : Fragment() {

    private var _binding: FragmentLibraryDetailBinding? = null
    private val binding get() = _binding!!

    private var item: Library? = null

    companion object {
        private const val ARG_ITEM = "arg_item"

        fun newInstance(libraryItem: Library?): LibraryDetailFragment {
            val fragment = LibraryDetailFragment()
            val args = Bundle()
            args.putSerializable(ARG_ITEM, libraryItem)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getSerializable(ARG_ITEM) as? Library
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (item != null) {
            showItemDetails()
        } else {
            showAddForm()
        }
    }

    private fun showItemDetails() {
        binding.groupDetails.visibility = View.VISIBLE
        binding.groupAddForm.visibility = View.GONE

        binding.textViewDetail.text = item!!.showInfo()
    }

    private fun showAddForm() {
        binding.groupDetails.visibility = View.GONE
        binding.groupAddForm.visibility = View.VISIBLE

        val types = resources.getStringArray(R.array.types_array)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter

        binding.buttonAdd.setOnClickListener {
            val name = binding.editName.text.toString()
            val type = binding.spinnerType.selectedItem.toString()

            val newItem: Library = when (type) {
                "Книга" -> Book(
                    id = LibraryRepository.getNextId(),
                    isAvailable = true,
                    name = name,
                    countPages = 100,
                    nameAuthor = "Автор"
                )

                "Диск" -> Disk(
                    id = LibraryRepository.getNextId(),
                    isAvailible = true,
                    name = name,
                    typeDisk = "CD"
                )

                else -> Papper(
                    id = LibraryRepository.getNextId(),
                    isAvailable = true,
                    name = name,
                    month = Months.JANUARY,
                    papperNumber = 1
                )
            }

            LibraryRepository.addItem(newItem)
            parentFragmentManager.setFragmentResult("item_added", Bundle())
            requireActivity().supportFragmentManager.popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
