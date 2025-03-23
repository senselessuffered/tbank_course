package com.example.library.com.example.library
import com.example.library.Disk

class DiskShop:Shop {
    override fun sale(): Disk {
        return Disk(35, true, "Ледниковый период 4", "CD")
    }
}