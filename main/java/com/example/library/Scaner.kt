package com.example.library.com.example.library

import com.example.library.Book
import com.example.library.Disk
import com.example.library.Library
import com.example.library.Papper

class Scaner(private val items: MutableList<Library>) {
    fun toCd(item: Library) {
        if (item is Book || item is Papper) {
            val digitalCd = Disk(
                id = item.id,
                isAvailible = true,
                name = "Оцифрованная копия: ${item.name}",
                typeDisk = "CD",
            )

            items.add(digitalCd)
            println("Оцифровка '${item.name}' на CD-диск завершена.")
        } else {
            println("Невозможно оцифровать этот тип объекта.")
        }
    }
}