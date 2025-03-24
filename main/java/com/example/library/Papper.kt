package com.example.library

import com.example.library.com.example.library.Months
import com.example.library.com.example.library.Readable

class Papper(
    id: Int,
    isAvailable: Boolean,
    name: String,
    private val month: Int,
    private val papperNumber: Int
) : Library(id, isAvailable, name), Readable {

    override fun readInLibrary() {
        if (isAvailable) {
            isAvailable = false
            println("Вы взяли почитать $name с id: $id в библиотеке")
        } else {
            println("$name с id: $id уже забрали. Невозможно выполнить действие.")
        }

    }

    override fun showShortInfo() {
        println("$name доступна: ${if (isAvailable) "Да" else "Нет"}")
    }

    override fun showDetailedInfo() {
        val monthName = Months.entries.firstOrNull { it.numberOfMonth == month }?.nameOfMonths
        println("выпуск: $papperNumber месяц: $monthName газеты $name с id: $id доступен: ${if (isAvailable) "Да" else "Нет"}")
    }
}