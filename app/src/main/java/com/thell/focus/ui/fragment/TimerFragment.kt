package com.thell.focus.ui.fragment

import android.os.Bundle
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thell.focus.R
import com.thell.focus.databinding.FragmentHistoryBinding
import com.thell.focus.databinding.FragmentMainBinding
import com.thell.focus.databinding.FragmentTimerBinding
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.focus.helper.navigation.NavigationMenuHelper
import com.thell.focus.helper.timer.TimerHelper
import kotlinx.android.synthetic.main.fragment_timer.view.*


class TimerFragment : Fragment()
{

    private val formatter = NumberPicker.Formatter { value -> String.format("%02d", value) }

    private val formatterSecond =  NumberPicker.Formatter() { value -> String.format("%02d", value* TimerHelper.PERIOD) }


    private lateinit var hourNumberPicker:NumberPicker
    private lateinit var minuteNumberPicker:NumberPicker
    private lateinit var secondNumberPicker:NumberPicker

    private lateinit var timerFragmentMuteSwitch : ToggleButton

    private lateinit var timerFragmentMuteStateTextView : TextView
    private lateinit var timerFragmentMuteStateExpTextView : TextView

    private lateinit var timerFragmentStartTimerButton : ImageView
    private lateinit var timerFragmentStopTimerButton : ImageView

    private lateinit var timerFragmentNumberPickerLinearLayout: LinearLayout
    private lateinit var timerFragmentTimerCountDownLinearLayout: LinearLayout

    private lateinit var timerFragmentHourTextView : TextView
    private lateinit var timerFragmentMinuteTextView : TextView
    private lateinit var timerFragmentSecondTextView : TextView

    private var isValid  = false

    private val switchChange = object : CompoundButton.OnCheckedChangeListener
    {
        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean)
        {
            setState(p1)
        }
    }

    private val startTimerOnClick = object :View.OnClickListener
    {

        override fun onClick(p0: View)
        {

        }

    }

    private val stopTimerOnClick = object :View.OnClickListener
    {

        override fun onClick(p0: View)
        {

        }

    }

    private val numberPickerValueChange = object :NumberPicker.OnValueChangeListener
    {
        override fun onValueChange(p0: NumberPicker?, p1: Int, p2: Int)
        {
            setButtonState()
        }
    }

    private  fun setState(state:Boolean)
    {
        if(context == null)
            return

        timerFragmentMuteStateTextView.apply {
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

        timerFragmentMuteStateExpTextView.apply {

            val sp : Spanned = when(state)
            {
                true -> HtmlCompat.fromHtml(getString(R.string.muteExp), HtmlCompat.FROM_HTML_MODE_LEGACY)
                else -> HtmlCompat.fromHtml(getString(R.string.notificationExp), HtmlCompat.FROM_HTML_MODE_LEGACY)
            }

            text = sp

        }

    }


    private fun setButtonState()
    {
        if(context == null)
            return
        if(hourNumberPicker.value == 0 && minuteNumberPicker.value == 0 && secondNumberPicker.value == 0)
        {
            isValid = false
            timerFragmentStartTimerButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorNotificationSetState))
        }
        else
        {
            isValid = true
            timerFragmentStartTimerButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorMuteSetState))
        }
    }


    private lateinit var navController: NavController
    private  var binding: FragmentTimerBinding? = null

    private  fun initUI()
    {


        hourNumberPicker = binding!!.fragmentTimerHourNumberPicker
        minuteNumberPicker = binding!!.fragmentTimerMinuteNumberPicker
        secondNumberPicker = binding!!.fragmentTimerSecondNumberPicker

        hourNumberPicker.setOnValueChangedListener(numberPickerValueChange)
        minuteNumberPicker.setOnValueChangedListener(numberPickerValueChange)
        secondNumberPicker.setOnValueChangedListener(numberPickerValueChange)

        timerFragmentMuteSwitch = binding!!.fragmentTimerMuteSwitch
        timerFragmentMuteSwitch.setOnCheckedChangeListener(switchChange)
        timerFragmentMuteStateExpTextView = binding!!.fragmentTimerMuteStateExpTextView

        timerFragmentMuteStateTextView = binding!!.fragmentTimerMuteStateTextView
        timerFragmentStartTimerButton = binding!!.fragmentTimerStartTimerButton
        timerFragmentStopTimerButton = binding!!.fragmentTimerStopTimerButton

        timerFragmentStartTimerButton.setOnClickListener(startTimerOnClick)
        timerFragmentStopTimerButton.setOnClickListener(stopTimerOnClick)

        timerFragmentNumberPickerLinearLayout = binding!!.linearLayoutNumberPicker
        timerFragmentTimerCountDownLinearLayout = binding!!.linearLayoutTimerCountDown

        timerFragmentHourTextView = binding!!.fragmentTimerHourTextView
        timerFragmentMinuteTextView = binding!!.fragmentTimerMinuteTextView
        timerFragmentSecondTextView = binding!!.fragmentTimerSecondTextView

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentTimerBinding.inflate(layoutInflater)
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
            callback.changeHeader(NavigationMenuHelper.TIMER)
        }

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}