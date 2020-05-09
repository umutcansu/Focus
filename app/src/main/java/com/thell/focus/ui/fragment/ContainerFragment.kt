package com.thell.focus.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thell.focus.R
import com.thell.focus.databinding.FragmentContainerBinding
import com.thell.focus.databinding.FragmentMainBinding
import com.thell.focus.helper.navigation.IFragmentCallback


class ContainerFragment() : Fragment() {


    private lateinit var navController: NavController
    private  var binding: FragmentContainerBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContainerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        navigateMainFragment()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    fun navigateMainFragment()
    {
        val action = ContainerFragmentDirections.actionContainerFragmentToMainFragment()
        action.fragmentCallback = activity!! as IFragmentCallback
        navController.navigate(action)
    }

}