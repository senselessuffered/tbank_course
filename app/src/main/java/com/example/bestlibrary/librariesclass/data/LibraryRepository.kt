package com.example.bestlibrary.librariesclass.data

import com.example.library.Book
import com.example.library.Disk
import com.example.library.Library
import com.example.library.Papper
import com.example.library.com.example.library.Months

object LibraryRepository {
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
    private var nextId = (items.maxOfOrNull { it.id } ?: 0) + 1

    fun getItems(): List<Library> = items

    fun addItem(item: Library) {
        items.add(item)
    }

    fun getNextId(): Int = nextId++
}
