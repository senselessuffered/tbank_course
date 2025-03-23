package com.example.library

import com.example.library.com.example.library.Readable

class Papper (
    id: Int,
    isAvailable: Boolean,
    name: String,
    val month: String,
    val papper_number: Int
): Library(id, isAvailable, name), Readable{

    override fun readInLibrary() {
        if (isAvailable) {
            isAvailable = false
            println("Вы взяли почитать ${name} с id: ${id} в библиотеке")
        } else {
            println("${name} с id: ${id} уже забрали. Невозможно выполнить действие.")
        }

    }

    override fun showShortInfo() {
        println("$name доступна: ${if (isAvailable) "Да" else "Нет"}")
    }

    override fun showDetailedInfo() {
        println("выпуск: $papper_number месяц: $month газеты $name с id: $id доступен: ${if (isAvailable) "Да" else "Нет"}")
    }
}