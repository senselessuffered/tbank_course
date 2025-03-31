package com.example.library

import com.example.library.com.example.library.Readable
import com.example.library.com.example.library.Takable

class Book(
    id: Int,
    isAvailable: Boolean,
    name: String,
    private val countPages: Int,
    private val nameAuthor: String
) : Library(id, isAvailable, name), Takable, Readable {

    override fun takeHome() {
        if (isAvailable) {
            isAvailable = false
            println("Вы взяли домой $name с id: $id взяли домой")
        } else {
            println("$name с id: $id уже забрали. Невозможно выполнить действие.")
        }
    }

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
        println("книга: $name (${countPages} стр.) автора: ${nameAuthor}author с id: $id доступна: ${if (isAvailable) "Да" else "Нет"}")
    }
}
