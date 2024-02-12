package com.example.todoapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.todoapp.Constants
import com.example.todoapp.R
import com.example.todoapp.ui.home.screens.SettingsFragment
import com.example.todoapp.ui.home.screens.ToDoListFragment
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.ui.home.screens.AddNewTaskFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val toDoListFragment = ToDoListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCurrentFragment(toDoListFragment)
        initListeners()
    }

    private fun setCurrentFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
        if (fragment is ToDoListFragment) {
            binding.title.text = Constants.TO_DO_LIST
        } else if (fragment is SettingsFragment) {
            binding.title.text = Constants.SETTINGS
        }
        return true
    }

    private fun initListeners() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.list -> setCurrentFragment(toDoListFragment)
                R.id.settings -> setCurrentFragment(SettingsFragment())
                else -> false
            }
        }

        binding.addTaskFab.setOnClickListener {
            val addNewTaskFragment = AddNewTaskFragment {
                toDoListFragment.refreshTodos()
            }
            addNewTaskFragment.show(supportFragmentManager, "")
        }
    }

}