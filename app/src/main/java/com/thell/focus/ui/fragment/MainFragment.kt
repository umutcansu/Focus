package com.thell.focus.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thell.focus.R
import com.thell.focus.broadcastreceiver.NotificationServiceBroadcastReceiver
import com.thell.focus.databinding.FragmentMainBinding
import com.thell.focus.helper.bootreceiver.BroadcastReceiverHelper
import com.thell.focus.helper.global.GuiHelper
import com.thell.focus.helper.mutestate.IMuteStateAction
import com.thell.focus.helper.mutestate.MuteStateActionHelper
import com.thell.focus.helper.mutestate.MuteStateSharedPrefAction
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.focus.helper.navigation.NavigationMenuHelper


class MainFragment : Fragment() {

    private lateinit var navController: NavController
    private  var binding: FragmentMainBinding? = null
    private lateinit var muteStateAction : IMuteStateAction


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
       binding = FragmentMainBinding.inflate(layoutInflater)
       initUI()
       init()
       return binding?.root
    }

    override fun onResume() {
        super.onResume()
        setStateInit()
    }

    private fun initUI()
    {
        GuiHelper.setTextViewPatternBackground(resources,R.drawable.pattern,binding!!.mainFragmentHeaderTextView)
    }

    private fun init()
    {
        val filter = IntentFilter(BroadcastReceiverHelper.NotificationServiceBroadcastReceiver)
        context?.registerReceiver(receiver, filter)
        muteStateAction = MuteStateActionHelper.getMuteStateAction(requireContext())
    }

    private val switchChange = object : CompoundButton.OnCheckedChangeListener
    {
        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean)
        {
            if (::muteStateAction.isInitialized)
                muteStateAction.switchMuteState()
        }
    }

    private val receiver = object : NotificationServiceBroadcastReceiver()
    {
        override fun onReceive(p0: Context?, p1: Intent?)
        {
            super.onReceive(p0, p1)
            val state = p1!!.getBooleanExtra(MuteStateSharedPrefAction.MUTE_STATE_KEY,false)
            checkNotificationState(state)
        }
    }

    private fun checkNotificationState(state:Boolean)
    {
        binding!!.mainFragmentMuteSwitch.setOnCheckedChangeListener(null)
        binding!!.mainFragmentMuteSwitch.isChecked = state
        binding!!.mainFragmentMuteSwitch.setOnCheckedChangeListener(switchChange)
        setState(state)
    }

    private  fun setStateInit()
    {
        val state = MuteStateActionHelper.getMuteStateAction(requireContext()).getMuteState()
        checkNotificationState(state)
    }

    private  fun setState(state:Boolean)
    {
        binding!!.mainFragmentMuteStateTextView.apply {
            if(state)
            {
                text = getString(R.string.mute)
                setTextColor(ContextCompat.getColor(context, R.color.colorMuteSetState))
            }
            else
            {
                text = getString(R.string.notification)
                setTextColor(ContextCompat.getColor(context, R.color.colorNotificationSetState))
            }

        }

        binding!!.mainFragmentMuteStateExpTextView.apply {

            val sp : Spanned = when(state)
            {
                true -> HtmlCompat.fromHtml(getString(R.string.muteExp), HtmlCompat.FROM_HTML_MODE_LEGACY)
                else -> HtmlCompat.fromHtml(getString(R.string.notificationExp), HtmlCompat.FROM_HTML_MODE_LEGACY)
            }

            text = sp

        }

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