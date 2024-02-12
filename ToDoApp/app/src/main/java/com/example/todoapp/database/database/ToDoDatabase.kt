package com.example.todoapp.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.database.dao.Dao
import com.example.todoapp.database.model.ToDo

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun todoDao(): Dao

    companion object{
        private var toDoDatabase: ToDoDatabase? = null

        fun getInstance(context: Context): ToDoDatabase {
            if (toDoDatabase == null){
                toDoDatabase = Room.databaseBuilder(context, ToDoDatabase::class.java, "todo database").allowMainThreadQueries().build()
            }
            return toDoDatabase!!
        }
    }
}