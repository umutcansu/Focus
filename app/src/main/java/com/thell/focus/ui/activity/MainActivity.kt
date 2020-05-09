package com.thell.focus.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.thell.focus.FocusApplication
import com.thell.focus.R
import com.thell.focus.databinding.ActivityMainBinding
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.focus.ui.fragment.ContainerFragment

class MainActivity : AppCompatActivity(),IFragmentCallback
{

    override fun changeHeader(header: String)
    {
        binding.mainActivityToolbar.toolbarNavigationHeaderTextView.text = header
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }




}