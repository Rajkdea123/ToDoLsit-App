package com.example.todoapp.ui.home.screens

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.database.database.ToDoDatabase
import com.example.todoapp.database.model.ToDo
import com.example.todoapp.databinding.FragmentToDoListBinding
import com.example.todoapp.timeInMillis
import com.example.todoapp.ui.adapters.TodoAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class ToDoListFragment : Fragment() {
    lateinit var binding: FragmentToDoListBinding
    lateinit var todos: List<ToDo>
    var todoAdapter = TodoAdapter(emptyList())
    var calendarDay = CalendarDay.today()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshTodos()
        binding.todosRV.adapter = todoAdapter
        binding.calendar.selectedDate = calendarDay
        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            calendarDay = date
            refreshTodos()
        }
        todoAdapter.onClick = object : TodoAdapter.OnClick {
            override fun onDeleteClick(toDo: ToDo, position: Int) {
                deleteTask(toDo)
            }

            override fun onCheckClick(toDo: ToDo, position: Int) {
                Handler(Looper.getMainLooper()).postDelayed({
                    ToDoDatabase.getInstance(requireContext()).todoDao().deleteTask(toDo)
                    refreshTodos()
                }, 1500)
            }
        }
    }

    private fun deleteTask(toDo: ToDo) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to Delete This Task?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                ToDoDatabase.getInstance(requireContext()).todoDao().deleteTask(toDo)
                refreshTodos()
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun handleEmptyList() {
        if (todos.isEmpty()) {
            binding.emptyTasksImv.visibility = View.VISIBLE
            binding.emptyTasksTv.visibility = View.VISIBLE
        } else {
            binding.emptyTasksImv.visibility = View.GONE
            binding.emptyTasksTv.visibility = View.GONE
        }
    }

    fun refreshTodos() {
        todos = ToDoDatabase.getInstance(requireContext()).todoDao()
            .getAllTasksByDate(calendarDay.timeInMillis())
        todoAdapter.updateTodos(todos)
        handleEmptyList()
    }
}