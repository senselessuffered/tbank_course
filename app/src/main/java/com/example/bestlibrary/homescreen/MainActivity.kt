package com.example.bestlibrary.homescreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestlibrary.databinding.ActivityMainBinding
import com.example.bestlibrary.infoscreen.InfoActivity
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
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra("info", item.showInfo())
            intent.putExtra("id", item.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = libraryAdapter

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                libraryAdapter.removeItem(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }
}
