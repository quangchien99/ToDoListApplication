package com.example.todolistapplication.data

import androidx.room.*
import com.example.todolistapplication.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getTasksSortedByDate(query, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }

    @Query("SELECT * FROM task_table WHERE(isCompleted != :hideCompleted OR isCompleted = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY   isImportant DESC, name ")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE(isCompleted != :hideCompleted OR isCompleted = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY   isImportant DESC, created ")
    fun getTasksSortedByDate(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}