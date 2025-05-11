package com.example.bestlibrary.librariesclass.models

class Papper(
    id: Int,
    isAvailable: Boolean,
    name: String,
    private val month: Months,
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

    override fun showInfo(): String {
        return "выпуск: $papperNumber месяц: ${month.nameOfMonths} газеты $name с id: $id доступен: ${if (isAvailable) "Да" else "Нет"}"
    }
}