package com.example.bestlibrary.librariesclass.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [LibraryEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "best_library.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch(Dispatchers.IO) {
                                val now = System.currentTimeMillis()
                                val initialData = listOf(
                                    LibraryEntity(
                                        11,
                                        "Книга",
                                        "Java: руководство для начинающих",
                                        "pages=234;author=Герберт Шилдт",
                                        now,
                                        false
                                    ),
                                    LibraryEntity(
                                        12,
                                        "Книга",
                                        "Язык программирования Си",
                                        "pages=1001;author=Керниган и Ритчи",
                                        now,
                                        false
                                    ),
                                    LibraryEntity(
                                        13,
                                        "Книга",
                                        "Триумфальная арка",
                                        "pages=591;author=Эрих Мария Ремарк",
                                        now,
                                        true
                                    ),
                                    LibraryEntity(
                                        21,
                                        "Газета",
                                        "Любовь",
                                        "month=1;papperNumber=314",
                                        now,
                                        false
                                    ),
                                    LibraryEntity(
                                        22,
                                        "Газета",
                                        "Смерть",
                                        "month=5;papperNumber=404",
                                        now,
                                        true
                                    ),
                                    LibraryEntity(
                                        23,
                                        "Газета",
                                        "Роботы",
                                        "month=8;papperNumber=505",
                                        now,
                                        true
                                    ),
                                    LibraryEntity(
                                        31,
                                        "Диск",
                                        "Город астероидов",
                                        "diskType=DVD",
                                        now,
                                        false
                                    ),
                                    LibraryEntity(
                                        32,
                                        "Диск",
                                        "Мистер робот",
                                        "diskType=CD",
                                        now,
                                        true
                                    ),
                                    LibraryEntity(
                                        33,
                                        "Диск",
                                        "Ледниковый период 3",
                                        "diskType=CD",
                                        now,
                                        false
                                    )
                                )
                                getInstance(context.applicationContext).libraryDao()
                                    .insertAll(initialData)
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
