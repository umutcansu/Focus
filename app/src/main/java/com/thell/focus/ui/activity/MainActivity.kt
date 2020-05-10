package com.thell.focus.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.drawerlayout.widget.DrawerLayout
import com.thell.focus.R
import com.thell.focus.databinding.ActivityMainBinding
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.mutenotification.model.NavigationDrawerItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thell.focus.FocusApplication
import com.thell.focus.helper.navigation.NavigationMenuHelper
import com.thell.focus.ui.fragment.*

class MainActivity : AppCompatActivity(),IFragmentCallback
{

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navFrag : NavigationDrawerFragment
    private lateinit var navController: NavController

    override fun changeHeader(header: String)
    {
        binding.mainActivityToolbar.toolbarNavigationHeaderTextView.apply {
            text = header
            visibility = View.VISIBLE
        }
        if(header.isNotEmpty())
        {
            for (menu in NavigationMenuHelper.allMenuItem)
            {
                menu.selected = menu.title == header
            }
        }

        navFrag.setupRecyclerView(this@MainActivity::menuChangeListener)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setupFullScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()
        init()
    }

    private fun initUI()
    {
        //GuiHelper.setTextViewPatternBackground(resources,R.drawable.pattern,fragment_navigation_drawer_header_textView)
    }
    private fun init()
    {
        initToolbarAndDrawerLayout()
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        openMainFragment()
    }

    private fun setupFullScreen()
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun initToolbarAndDrawerLayout()
    {
        drawerLayout = binding.mainActivityDrawerLayout
        toolbar = binding.mainActivityToolbar.root
        setupNavigationDrawer()
    }
    private fun setupNavigationDrawer()
    {
        navFrag = supportFragmentManager.findFragmentById(R.id.mainActivityDrawerLayoutFragment) as NavigationDrawerFragment
        navFrag.setupDrawerToggle(drawerLayout,toolbar ,::menuChangeListener)
    }

    private fun menuChangeListener(menu: NavigationDrawerItem)
    {
        when(menu.title)
        {
            NavigationMenuHelper.HOME -> openMainFragment()
            NavigationMenuHelper.HISTORY -> openHistoryFragment()
            NavigationMenuHelper.SETTING -> openSettingsFragment()
            NavigationMenuHelper.TIMER -> openTimerFragment()
        }
        closeDrawerLayout()
    }

    private fun changeFragment(fragment: Int,args:Bundle)
    {
        navController.navigate(fragment,args)
    }

    private fun openMainFragment()
    {
        val args = MainFragmentArgs.Builder()
        args.fragmentCallback = this
        changeFragment(R.id.mainFragment,args.build().toBundle())
    }


    private fun openHistoryFragment()
    {
        val args = HistoryFragmentArgs.Builder()
        args.fragmentCallback = this
        changeFragment(R.id.historyFragment,args.build().toBundle())
    }

    private fun openSettingsFragment()
    {
        val args = SettingsFragmentArgs.Builder()
        args.fragmentCallback = this
        changeFragment(R.id.settingsFragment,args.build().toBundle())
    }

    private fun openTimerFragment()
    {
        val args = TimerFragmentArgs.Builder()
        args.fragmentCallback = this
        changeFragment(R.id.timerFragment,args.build().toBundle())
    }

    @SuppressLint("WrongConstant")
    fun closeDrawerLayout()
    {
        if(::drawerLayout.isInitialized)
            drawerLayout.post {
                drawerLayout.closeDrawer(Gravity.START, true)
            }
    }

    @SuppressLint("WrongConstant")
    fun openDrawerLayout()
    {
        if(::drawerLayout.isInitialized)
            drawerLayout.post {
                drawerLayout.openDrawer(Gravity.START, true)
            }

    }

}