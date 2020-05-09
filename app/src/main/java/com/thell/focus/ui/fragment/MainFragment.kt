package com.thell.focus.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thell.focus.R
import com.thell.focus.databinding.ActivityMainBinding
import com.thell.focus.databinding.FragmentContainerBinding
import com.thell.focus.databinding.FragmentMainBinding
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.focus.helper.navigation.NavigationMenuHelper


class MainFragment : Fragment() {

    private lateinit var navController: NavController
    private  var binding: FragmentMainBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
       binding = FragmentMainBinding.inflate(layoutInflater)
       return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if(arguments != null)
        {
            val args = MainFragmentArgs.fromBundle(requireArguments())
            val callback =  args.fragmentCallback as IFragmentCallback
            callback.changeHeader(NavigationMenuHelper.HOME)
        }

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


}