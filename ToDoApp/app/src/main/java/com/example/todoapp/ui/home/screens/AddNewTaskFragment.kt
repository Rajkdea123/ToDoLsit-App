package com.example.todoapp.ui.home.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.todoapp.R
import com.example.todoapp.clearExcess
import com.example.todoapp.database.database.ToDoDatabase
import com.example.todoapp.database.model.ToDo
import com.example.todoapp.databinding.FragmentAddNewTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar
import kotlin.Boolean

class AddNewTaskFragment(val onAdd: () -> Unit) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddNewTaskBinding
    var selectedDate = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        binding.timeTv.text =
            "${selectedDate.get(Calendar.DAY_OF_MONTH)} / ${selectedDate.get(Calendar.MONTH) + 1} / ${
                selectedDate.get(Calendar.YEAR)
            }"
    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {
        binding.addFab.setOnClickListener {
            if (validateInputs()) {
                Toast.makeText(requireContext(), "Task Added", Toast.LENGTH_SHORT).show()
                dismiss()
                selectedDate.clearExcess()
                ToDoDatabase.getInstance(requireContext()).todoDao().addTask(
                    ToDo(
                        title = binding.taskTil.editText!!.text.toString(),
                        description = binding.descriptionTil.editText!!.text.toString(),
                        time = selectedDate.timeInMillis,
                        isDone = false
                    )
                )
                onAdd.invoke()
            } else {
                Toast.makeText(requireContext(), "Please Fill All The Fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.taskTil.editText!!.addTextChangedListener {
            validateInputs()
        }
        binding.descriptionTil.editText!!.addTextChangedListener {
            validateInputs()
        }
        binding.timeTv.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireActivity(),
                R.style.TimePickerTheme,
                { picker, year, month, day ->
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, day)
                    binding.timeTv.text =
                        "${selectedDate.get(Calendar.DAY_OF_MONTH)} / ${selectedDate.get(Calendar.MONTH) + 1} / ${
                            selectedDate.get(Calendar.YEAR)
                        }"
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
            datePicker.datePicker.minDate = selectedDate.timeInMillis
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE)
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        if (binding.taskTil.editText!!.text.isEmpty()) {
            binding.taskTil.error = "Please Enter Task Title"
            isValid = false
        } else {
            binding.taskTil.error = null
        }
        if (binding.descriptionTil.editText!!.text.isEmpty()) {
            binding.descriptionTil.error = "Please Enter Task Description"
            isValid = false
        } else {
            binding.descriptionTil.error = null
        }
        return isValid
    }

}