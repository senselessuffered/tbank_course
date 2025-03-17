package com.example.library

// Класс быблыотека где хранятся книги, диски и газеты (используется наследование из лекции)
open class Library(
    val id: Int,
    var available: Boolean,
    val name: String
) {
    open fun showShortInfo() {
        println("$name доступна: ${if (available) "Да" else "Нет"}")
    }

    open fun showDetailedInfo() {}
}

// Класс диски (не от колес)
class Disk(
    id: Int,
    availible: Boolean,
    name: String,
    val disk_type: String,
): Library(id, availible, name){
    override fun showShortInfo() {
        println("$disk_type $name доступен: ${if (available) "Да" else "Нет"}")
    }

    override fun showDetailedInfo() {
        println("$disk_type $name доступен: ${if (available) "Да" else "Нет"}")
    }
}

// Класс газеты или бумажки
class Papper(
    id: Int,
    available: Boolean,
    name: String,
    val papper_number: Int
): Library(id, available, name){
    override fun showShortInfo() {
        println("$name доступна: ${if (available) "Да" else "Нет"}")
    }

    override fun showDetailedInfo() {
        println("выпуск: $papper_number газеты $name с id: $id доступен: ${if (available) "Да" else "Нет"}")
    }
}

// Класс книги
class Book(
    id: Int,
    available: Boolean,
    name: String,
    val book_pages: Int,
    val book_author: String
): Library(id, available, name) {
    override fun showShortInfo() {
        println("$name доступна: ${if (available) "Да" else "Нет"}")
    }

    override fun showDetailedInfo() {
        println("книга: $name (${book_pages} стр.) автора: ${book_author}author с id: $id доступна: ${if (available) "Да" else "Нет"}")
    }
}

// Класс который содержит функции меню
class console_tools(private val items: List<Library>) {

    // Основное меню
    fun show_main() {
        val greeting = """
        |Добро пожаловать в библиотеку.
        |Выберите действие:
        |   1 - Показать книги
        |   2 - Показать газеты
        |   3 - Показать диски
        |   0 - Выйти из библиотеки
        """.trimMargin("|")

        while (true) {
            println(greeting)
            val task = readlnOrNull()?.toIntOrNull()
            when (task) {
                1 -> show_books(items.filterIsInstance<Book>())
                2 -> show_papers(items.filterIsInstance<Papper>())
                3 -> show_disks(items.filterIsInstance<Disk>())
                0 -> return
                else -> println("Некорректный ввод, попробуйте снова.")
            }
        }
    }

    // Вывод объектов в зависимости от предпочтений пользователя
    private fun show_books(items: List<Library>) {
        show_items(items)
    }

    private fun show_papers(items: List<Library>) {
        show_items(items)
    }

    private fun show_disks(items: List<Library>) {
        show_items(items)
    }

    // Универсальный метод для показа списка объектов
    private fun show_items(items: List<Library>) {
        if (items.isEmpty()) {
            println("Нет доступных объектов.")
            return
        }

        items.forEachIndexed { index, item ->
            println("${index + 1}. ${item.name} (${if (item.available) "Да" else "Нет"})") // использую что-то типо enumerate из питона
        }

        select_item(items)
    }

    // Выбор из подборки
    private fun select_item(items: List<Library>) {
        println("Выберите объект по номеру (или 0 для выхода):")
        val index = readLine()?.toIntOrNull()?.minus(1)

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
        |  0 - Вернуться в главное меню
        """.trimMargin()

        while (true) {
            println(menu)
            val action = readLine()?.toIntOrNull() ?: return
            when (action) {
                1 -> {
                    take_home(item)
                    item.showShortInfo()
                }
                2 -> {
                    read_in_library(item)
                    item.showShortInfo()
                }
                3 -> item.showDetailedInfo()
                4 -> {
                    return_item(item)
                    item.showShortInfo()
                }
                0 -> return
                else -> println("Некорректный ввод, попробуйте снова.")
            }
        }
    }

    private fun take_home(item: Library) {
        if (item is Papper || item !is Book && item !is Disk || !item.available) {
            println("Невозможно выполнить действие")
        } else {
            item.available = false
            println("${item::class.simpleName} с id: ${item.id} взяли домой")
        }
    }

    private fun read_in_library(item: Library) {
        if (item is Disk || item !is Book && item !is Papper || !item.available) {
            println("Невозможно выполнить действие")
        } else {
            item.available = false
            println("${item::class.simpleName} с id: ${item.id} взяли почитать в читальном зале")
        }
    }

    private fun return_item(item: Library) {
        if (item.available) {
            println("Невозможно вернуть объект, он уже на базе")
        } else {
            item.available = true
            println("Вы вернули ${item::class.simpleName} с id: ${item.id} на базу")
        }
    }
}


fun main() {
    val items = listOf(
        Book(11, false, "Java: руководство для начинающих", 234, "Герберт Шилдт"),
        Book(12, false, "Язык программирования Си", 1001, "Брайан Керниган и Деннис Ритчи"),
        Book(13, true, "Триумфальная арка", 591, "Эрих Мария Ремарк"),
        Papper(21, false, "Любовь", 314),
        Papper(22, true, "Семерть", 404),
        Papper(23, true, "Роботы", 505),
        Disk(31, true, "Город астероидов", "DVD"),
        Disk(32, true, "Мистер робот", "CD"),
        Disk(33, false, "Ледниковый период 3", "CD")
    )

    val menu = console_tools(items)
    menu.show_main()
}
