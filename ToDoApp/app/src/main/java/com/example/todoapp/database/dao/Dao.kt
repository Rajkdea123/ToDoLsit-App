package com.example.todoapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.database.model.ToDo

@Dao
interface Dao {
    @Insert
    fun addTask(toDo: ToDo)

    @Delete
    fun deleteTask(toDo: ToDo)

    @Query("select * from ToDo")
    fun getAllTasks(): List<ToDo>

    @Query("select * from ToDo where time = :time")
    fun getAllTasksByDate(time: Long): List<ToDo>

    @Update
    fun updateTask(toDo: ToDo)
}