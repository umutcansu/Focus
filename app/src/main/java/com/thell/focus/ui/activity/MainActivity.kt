package com.thell.focus.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.thell.focus.R
import com.thell.focus.databinding.ActivityMainBinding
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.mutenotification.model.NavigationDrawerItem
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thell.focus.helper.bootreceiver.BootReceiverHelper
import com.thell.focus.helper.bootreceiver.BootReceiverPrefHelper
import com.thell.focus.helper.global.GlobalHelper
import com.thell.focus.helper.global.GuiHelper
import com.thell.focus.helper.navigation.NavigationMenuHelper
import com.thell.focus.helper.permission.PermissionHelper
import com.thell.focus.ui.fragment.*
import kotlinx.android.synthetic.main.fragment_navigation_drawer.view.*

class MainActivity : AppCompatActivity(),IFragmentCallback
{

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navFrag : NavigationDrawerFragment
    private lateinit var navController: NavController

    private val infoOnClick = object :View.OnClickListener
    {

        lateinit var dialog:AlertDialog
        lateinit var alertDialogBuilder:AlertDialog.Builder

        override fun onClick(p0: View)
        {
            GuiHelper.startRotatingView(null,p0,::coreClick)
        }

        private fun coreClick()
        {

            if(!::alertDialogBuilder.isInitialized)
            {
                val message =
                    "${HtmlCompat.fromHtml(getString(R.string.info),HtmlCompat.FROM_HTML_MODE_LEGACY)}" +
                            "Version: ${GlobalHelper.VERSION}"
                alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
                alertDialogBuilder.setTitle(R.string.app_name)
                alertDialogBuilder.setMessage(message)
                alertDialogBuilder.setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.dismiss()
                    })
            }
            if(!::dialog.isInitialized)
            {
                dialog = alertDialogBuilder.create()
            }

            dialog.show()

        }

    }

    private val menuOnClick = object :View.OnClickListener
    {

        override fun onClick(p0: View)
        {
            GuiHelper.startRotatingView(null,p0,::coreClick)
        }

        private fun coreClick()
        {
            GuiHelper.openDrawerLayout(drawerLayout)
        }

    }



    private fun initButtonClick()
    {
        binding.mainActivityToolbar.toolbarInfoButton.setOnClickListener(infoOnClick)
        binding.mainActivityToolbar.toolbarMenuButton.setOnClickListener(menuOnClick)
    }

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

    override fun onResume()
    {

        if(isPermission)
            checkAndRequestNotificationPermission()

        super.onResume()
    }

    private fun initUI()
    {
        initButtonClick()
    }

    private fun init()
    {
        initToolbarAndDrawerLayout()
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
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
        GuiHelper.closeDrawerLayout(drawerLayout)
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


    private fun start()
    {
        openMainFragment()
    }

//-------------------------------PERMISSION---------------------------------------------------------

    private lateinit var dialog:AlertDialog
    private var isPermission = true

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        if(requestCode == PermissionHelper.NOTIFICATION_PERMISSION_REQUEST_CODE)
        {
            checkAndRequestNotificationPermission()
        }
        else if(requestCode == PermissionHelper.BOOT_PERMISSION_REQUEST_CODE)
        {
            checkAndRequestBootReceiverPermission()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun notificationPermissionDialogListener(state:Boolean)
    {
        if(!state)
        {
            Toast.makeText(this,getString(R.string.permission_request_cancel_message), Toast.LENGTH_LONG).show()
            checkAndRequestNotificationPermission()
        }
    }

    private fun systemAlertPermissionDialogListener(state:Boolean)
    {
        if(!state)
        {
            Toast.makeText(this,getString(R.string.permission_request_cancel_message), Toast.LENGTH_LONG).show()
            checkAndRequestSystemAlertPermission()
        }
    }

    private fun checkAndRequestNotificationPermission()
    {
        try
        {
            if(!PermissionHelper.isNotificationServiceEnabled(this))
            {

                dialog = PermissionHelper.buildNotificationServiceAlertDialog(this,::notificationPermissionDialogListener)
                dialog.show()
            }
            else
            {
                checkAndRequestBootReceiverPermission()
            }
        }
        catch (e: Exception)
        {

        }
    }

    private fun checkAndRequestBootReceiverPermission()
    {
        try
        {

            if(BootReceiverHelper.getInstance().checkPrePermission())
            {
                if(!BootReceiverPrefHelper.readBoolean(this) )
                {
                    isPermission = false
                    PermissionHelper.buildBootReceiverAlertDialog(this)
                }
                else
                {
                    start()
                }
            }
            else
            {
                start()
            }

        }
        catch (e: Exception)
        {

        }
    }

    private fun checkAndRequestSystemAlertPermission()
    {
        try
        {

            if(PermissionHelper.checkSystemAlertPermission(this))
            {
                val dialog = PermissionHelper.buildSystemAlertPermissionAlertDialog(this,::systemAlertPermissionDialogListener)
                dialog.show()
            }
            else
            {
                //init()
            }

        }
        catch (e: Exception)
        {

        }
    }


//--------------------------------------------------------------------------------------------------
}