package com.example.library.com.example.library
import com.example.library.Papper

class PapperShop:Shop {
    override fun sale(): Papper {
        return Papper(28, true, "Роботы", "Январь", 505)
    }
}