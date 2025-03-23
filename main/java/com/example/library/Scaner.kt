package com.example.library.com.example.library

import com.example.library.Book
import com.example.library.Disk
import com.example.library.Library
import com.example.library.Papper

class Scaner(private val items: MutableList<Library>) {
    fun toCd(item: Library) {
        when (item) {
            is Book -> bookToCd(item)
            is Papper -> papperToCd(item)
        }
    }

    private fun bookToCd(book: Book) {
        val digitalCd = Disk(book.id,true,"Оцифрованная копия: ${book.name}", "CD")

        items.add(digitalCd)
        println("Оцифровка книги '${book.name}' на CD-диск завершена.")
    }

    private fun papperToCd(papper: Papper) {
        val digitalCd = Disk(papper.id, true,  "Оцифрованная копия: ${papper.name}", "CD")
        items.add(digitalCd)
        println("Оцифровка газеты '${papper.name}' на CD-диск завершена.")
    }
}
