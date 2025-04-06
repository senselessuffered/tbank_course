package com.example.library

open class Library(
    val id: Int,
    var isAvailable: Boolean,
    val name: String,
) {
    open fun showShortInfo() {
        println("$name доступна: ${if (isAvailable) "Да" else "Нет"}")
    }

    open fun showDetailedInfo() {}
}
