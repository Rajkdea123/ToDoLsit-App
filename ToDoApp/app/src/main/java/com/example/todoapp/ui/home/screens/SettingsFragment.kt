package com.example.todoapp.ui.home.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.languagesAutoCompleteTv.setAdapter(ArrayAdapter(requireContext(), R.layout.drop_down_item, arrayOf("English","Arabic")))
        binding.modeAutoCompleteTv.setAdapter(ArrayAdapter(requireContext(), R.layout.drop_down_item, arrayOf("Light","Dark")))
        return binding.root
    }
}