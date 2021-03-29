package com.example.todolistapplication.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.todolistapplication.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class TasksViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val sortOrder = MutableStateFlow(SortOrder.BY_DATE)

    val hideCompleted = MutableStateFlow(false)

    private val taskFlow = combine(
        searchQuery,
        sortOrder,
        hideCompleted
    ) { query, sortOder, hiderCompleted ->
        Triple(query, sortOder, hiderCompleted)
    }.flatMapLatest { (query, sortOrder, hideCompleted) ->
        taskDao.getTasks(query, sortOrder, hideCompleted)
    }


    val tasks = taskFlow.asLiveData()
}

enum class SortOrder {
    BY_NAME,
    BY_DATE
}