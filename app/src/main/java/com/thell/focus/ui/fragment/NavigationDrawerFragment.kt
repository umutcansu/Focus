package com.thell.focus.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.thell.mutenotification.model.NavigationDrawerItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.thell.focus.R
import com.thell.focus.adapter.NavigationDrawerAdapter
import com.thell.focus.databinding.FragmentContainerBinding
import com.thell.focus.databinding.FragmentNavigationDrawerBinding
import com.thell.focus.helper.global.GuiHelper
import com.thell.focus.helper.navigation.NavigationMenuHelper


class NavigationDrawerFragment() : Fragment()
{

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationDrawerRecyclerView: RecyclerView
    private lateinit var adapter: NavigationDrawerAdapter
    private lateinit var drawerLayout: DrawerLayout
    private  var binding: FragmentNavigationDrawerBinding? = null

    private val closeMenuOnClick = object :View.OnClickListener
    {

        override fun onClick(p0: View)
        {
            GuiHelper.startRotatingView(null,p0,::coreClick)
        }

        private fun coreClick()
        {
            GuiHelper.closeDrawerLayout(drawerLayout)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        binding = FragmentNavigationDrawerBinding.inflate(layoutInflater)
        navigationDrawerRecyclerView = binding!!.navigationDrawerRecyclerView
        binding!!.fragmentNavigationDrawerCloseButton.setOnClickListener(closeMenuOnClick)
        return binding?.root
    }

    fun setupDrawerToggle(drawerLayout: DrawerLayout, toolbar: Toolbar,
                          menuChangeListener : (menu:NavigationDrawerItem) -> Unit ={}) {

        this.drawerLayout = drawerLayout
        mDrawerToggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar,
            R.string.toolbar_open,
            R.string.toolbar_close
        )
        setupRecyclerView(menuChangeListener)

        drawerLayout.post()
        {
            //mDrawerToggle.syncState()
        }
    }



    fun setupRecyclerView(menuChangeListener : (menu:NavigationDrawerItem) -> Unit ={})
    {
        adapter = NavigationDrawerAdapter(
            navigationDrawerRecyclerView.context,
            NavigationMenuHelper.allMenuItem,
            menuChangeListener
        )
        navigationDrawerRecyclerView.layoutManager = LinearLayoutManager(
            navigationDrawerRecyclerView.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        navigationDrawerRecyclerView.adapter = adapter
    }


}
