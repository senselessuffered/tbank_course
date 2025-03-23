package com.example.library

import com.example.library.com.example.library.Takable

class Disk(
    id: Int,
    isAvailible: Boolean,
    name: String,
    val typeDisk: String,
): Library(id, isAvailible, name), Takable {

    override fun takeHome() {
        if (isAvailable) {
            isAvailable = false
            println("Вы взяли домой ${name} с id: ${id} взяли домой")
        } else {
            println("${name} с id: ${id} уже забрали. Невозможно выполнить действие.")
        }
    }

    override fun showShortInfo() {
        println("$typeDisk $name доступен: ${if (isAvailable) "Да" else "Нет"}")
    }

    override fun showDetailedInfo() {
        println("$typeDisk $name доступен: ${if (isAvailable) "Да" else "Нет"}")
    }
}
