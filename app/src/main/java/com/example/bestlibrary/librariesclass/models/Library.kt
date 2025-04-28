package com.example.bestlibrary.librariesclass.models

import java.io.Serializable

abstract class Library(
    val id: Int,
    var isAvailable: Boolean,
    val name: String,
) : Serializable {
    abstract fun showShortInfo()

    abstract fun showInfo(): String
}
