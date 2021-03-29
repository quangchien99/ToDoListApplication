package com.example.todolistapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolistapplication.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {


    abstract fun getTaskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //db operation
            val dao = database.get().getTaskDao()

            applicationScope.launch {
                dao.insert(Task("Wash the dishes", isCompleted = true))
                dao.insert(Task("Go to school", isCompleted = true))
                dao.insert(Task("Go to supermarket", isImportant = true))
                dao.insert(Task("Do the laundry", isImportant = true, isCompleted = true))
                dao.insert(Task("Do the laundry", isImportant = true, isCompleted = true))
                dao.insert(Task("Visit friend", isCompleted = true))
                dao.insert(Task("Fix the heart"))
                dao.insert(Task("Repair bike", isImportant = true))
                dao.insert(Task("Call mom"))
            }
        }
    }
}