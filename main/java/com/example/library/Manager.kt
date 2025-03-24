package com.example.library

import com.example.library.com.example.library.Shop

class Manager {
    fun buy(shop: Shop): Library {
        return shop.sale()
    }
}
