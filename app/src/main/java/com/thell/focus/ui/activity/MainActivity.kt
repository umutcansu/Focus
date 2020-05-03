package com.thell.focus.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thell.focus.FocusApplication
import com.thell.focus.R
import com.thell.focus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        FocusApplication.getDatabase()
    }
}