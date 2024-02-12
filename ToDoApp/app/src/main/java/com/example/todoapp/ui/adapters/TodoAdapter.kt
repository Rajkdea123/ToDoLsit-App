package com.example.todoapp.ui.adapters

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.database.model.ToDo
import com.example.todoapp.databinding.TaskItemBinding

class TodoAdapter(var todos: List<ToDo>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    var onClick: OnClick? = null

    class TodoViewHolder(var binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = todos.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.taskTitleTv.text = todos[position].title
        holder.binding.taskDescriptionTv.text = todos[position].description
        holder.binding.checkBtn.setBackgroundDrawable(ContextCompat.getDrawable(holder.binding.checkBtn.context, R.drawable.rectangle))
        holder.binding.checkBtn.text = ""
        holder.binding.checkBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_check, 0, 0)
        holder.binding.checkBtn.setPadding(0, 20, 0, 0)

//        holder.binding.checkBtn.setOnClickListener {
//            onCheck.invoke(todos[position])
//        }

        holder.binding.leftItem.setOnClickListener {
            onClick?.onDeleteClick(todos[position], position)
        }
        holder.binding.checkBtn.setOnClickListener {
            onClick?.onCheckClick(todos[position], position)
                holder.binding.checkBtn.setBackgroundColor(Color.TRANSPARENT)
                holder.binding.checkBtn.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    null,
                    null
                )
                holder.binding.checkBtn.text = "Done!"
                holder.binding.checkBtn.setPadding(0, 0, 0, 0)
        }
    }

    fun updateTodos(todos: List<ToDo>) {
        this.todos = todos
        notifyDataSetChanged()
    }

    interface OnClick {
        fun onDeleteClick(toDo: ToDo, position: Int)
        fun onCheckClick(toDo: ToDo, position: Int)
    }
}