package com.example.bestlibrary.librariesclass.models

class Disk(
    id: Int,
    isAvailible: Boolean,
    name: String,
    val typeDisk: String,
) : Library(id, isAvailible, name), Takable {

    override fun takeHome() {
        if (isAvailable) {
            isAvailable = false
            println("Вы взяли домой $name с id: $id взяли домой")
        } else {
            println("$name с id: $id уже забрали. Невозможно выполнить действие.")
        }
    }

    override fun showShortInfo() {
        println("$typeDisk $name доступен: ${if (isAvailable) "Да" else "Нет"}")
    }

    override fun showInfo(): String {
        return "$typeDisk $name доступен: ${if (isAvailable) "Да" else "Нет"}"
    }
}
