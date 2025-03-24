package com.example.library

import Manager
import com.example.library.com.example.library.Takable
import com.example.library.com.example.library.Readable
import com.example.library.com.example.library.BookShop
import com.example.library.com.example.library.DiskShop
import com.example.library.com.example.library.PapperShop
import com.example.library.com.example.library.Scaner

class ConsoleTools(private val items: MutableList<Library>) {
    private inline fun <reified T> filterList(list: List<Library>): List<T> {
        return list.filter { it is T }.map { it as T }
    }

    // Основное меню
    fun showMenu() {
        val greeting = """
        |Добро пожаловать в библиотеку.
        |Выберите действие:
        |   1 - Показать книги
        |   2 - Показать газеты
        |   3 - Показать диски
        |   4 - Купить в магазине
        |   0 - Выйти из библиотеки
        """.trimMargin("|")

        while (true) {
            println(greeting)
            val task = readlnOrNull()?.toIntOrNull()
            when (task) {
                1 -> showList(filterList<Book>(items))
                2 -> showList(filterList<Papper>(items))
                3 -> showList(filterList<Disk>(items))
                4 -> showShops()
                0 -> return
                else -> println("Некорректный ввод, попробуйте снова.")
            }
        }
    }

    private fun showShops() {
        val shops = """
            |1 - Магазин книг
            |2 - Магазин газет
            |3 - Магазин дисков
            |0 - Вернуться в главное меню
        """.trimMargin()
        val bookShop = BookShop()
        val paperShop = PapperShop()
        val diskShop = DiskShop()
        val manager = Manager()
        var product: Library

        while (true) {
            println(shops)
            val task = readlnOrNull()?.toIntOrNull()
            product = when (task) {
                1 -> manager.buyBook(bookShop)
                2 -> manager.buyPapper(paperShop)
                3 -> manager.buyDisk(diskShop)
                0 -> return
                else -> {
                    println("Некорректный ввод, попробуйте снова.")
                    continue
                }
            }
            println("Вы купили ${product.name} с id: ${product.id}")
            }
    }

    // Универсальный метод для показа списка объектов
    private fun showList(items: List<Library>) {
        if (items.isEmpty()) {
            println("Нет доступных объектов.")
            return
        }

        items.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name} (${if (item.isAvailable) "Да" else "Нет"})") // использую что-то типо enumerate из питона
        }

        selectObject(items)
    }

    // Выбор из подборки
    private fun selectObject(items: List<Library>) {
        println("Выберите объект по номеру (или 0 для выхода):")
        val index = readlnOrNull()?.toIntOrNull()?.minus(1)

        if (index == null || index == -1) return

        if (index in items.indices) { // странно выводит эту штуку как 0...число
            action(items[index])
        } else {
            println("Неверный индекс, попробуйте снова.")
        }
    }

    // Действия с объектом
    private fun action(item: Library) {
        val menu = """
        |Что вы хотите сделать?
        |  1 - Взять домой
        |  2 - Читать в читальном зале
        |  3 - Показать подробную информацию
        |  4 - Вернуть
        |  5 - Оцифровать
        |  0 - Вернуться в главное меню
        """.trimMargin()

        while (true) {
            println(menu)
            val action = readlnOrNull()?.toIntOrNull() ?: return
            when (action) {
                1 -> {
                    if (item is Takable) {
                        item.takeHome()
                        item.showShortInfo()
                    } else {
                        println("Невозможно выполнить действие.")
                    }
                }
                2 -> {
                    if (item is Readable) {
                        item.readInLibrary()
                        item.showShortInfo()
                    } else {
                        println("Невозможно выполнить действие")
                    }
                }
                3 -> item.showDetailedInfo()
                4 -> {
                    returnObject(item)
                    item.showShortInfo()
                }
                5 -> {
                    if ((item is Papper || item is Book) && item.isAvailable) {
                        val scaner = Scaner(items)
                        scaner.toCd(item)
                    } else {
                        println("Невозможно выполнить действие")
                    }
                }
                0 -> return
                else -> println("Некорректный ввод, попробуйте снова.")
            }
        }
    }

    private fun returnObject(item: Library) {
        if (item.isAvailable) {
            println("Невозможно вернуть объект, он уже на базе")
        } else {
            item.isAvailable = true
            println("Вы вернули ${item::class.simpleName} с id: ${item.id} на базу")
        }
    }
}