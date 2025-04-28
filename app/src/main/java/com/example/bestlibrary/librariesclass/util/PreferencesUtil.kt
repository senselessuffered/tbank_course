package com.example.bestlibrary.librariesclass.util

import android.content.Context
import androidx.core.content.edit

object PreferencesUtil {
    private const val PREFS = "sort_prefs"
    private const val KEY_SORT = "sort_order"

    fun saveSort(context: Context, sort: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit() {
                putString(KEY_SORT, sort)
            }
    }

    fun loadSort(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_SORT, "date") ?: "date"
}
