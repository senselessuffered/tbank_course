package com.example.bestlibrary.homescreen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestlibrary.databinding.ActivityMainBinding
import com.example.library.Book
import com.example.library.Disk
import com.example.library.Papper
import com.example.library.com.example.library.Months

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var libraryAdapter: LibraryAdapter

    private val items = mutableListOf(
        Book(11, false, "Java: руководство для начинающих", 234, "Герберт Шилдт"),
        Book(12, false, "Язык программирования Си", 1001, "Брайан Керниган и Деннис Ритчи"),
        Book(13, true, "Триумфальная арка", 591, "Эрих Мария Ремарк"),
        Papper(21, false, "Любовь", Months.JANUARY, 314),
        Papper(22, true, "Смерть", Months.MAY, 404),
        Papper(23, true, "Роботы", Months.AUGUST, 505),
        Disk(31, true, "Город астероидов", "DVD"),
        Disk(32, true, "Мистер робот", "CD"),
        Disk(33, false, "Ледниковый период 3", "CD")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        libraryAdapter = LibraryAdapter(items) { item ->
            item.isAvailable = !item.isAvailable
            Toast.makeText(this, "Элемент с id: ${item.id}", Toast.LENGTH_SHORT).show()
            libraryAdapter.notifyDataSetChanged()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = libraryAdapter
    }
}