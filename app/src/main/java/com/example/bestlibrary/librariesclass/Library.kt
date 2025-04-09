package com.example.library

abstract class Library(
    val id: Int,
    var isAvailable: Boolean,
    val name: String,
) {
    abstract fun showShortInfo()

    abstract fun showInfo(): String
}
