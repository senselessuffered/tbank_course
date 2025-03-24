package com.example.library.com.example.library

import com.example.library.Book

class BookShop : Shop {
    override fun sale(): Book {
        return Book(101, true, "Программирование на Kotlin", 300, "Jet Brains")
    }
}