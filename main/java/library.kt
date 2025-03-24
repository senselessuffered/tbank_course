package com.example.library

fun main() {
    val items = mutableListOf(
        Book(11, false, "Java: руководство для начинающих", 234, "Герберт Шилдт"),
        Book(12, false, "Язык программирования Си", 1001, "Брайан Керниган и Деннис Ритчи"),
        Book(13, true, "Триумфальная арка", 591, "Эрих Мария Ремарк"),
        Papper(21, false, "Любовь", 4, 314),
        Papper(22, true, "Смерть", 6, 404),
        Papper(23, true, "Роботы", 8, 505),
        Disk(31, true, "Город астероидов", "DVD"),
        Disk(32, true, "Мистер робот", "CD"),
        Disk(33, false, "Ледниковый период 3", "CD")
    )

    val menu = ConsoleTools(items)
    menu.showMenu()
}
