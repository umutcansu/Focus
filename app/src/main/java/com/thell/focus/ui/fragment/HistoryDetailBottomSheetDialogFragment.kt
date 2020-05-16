package com.thell.focus.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thell.focus.R
import com.thell.focus.database.entity.NotificationEntity
import com.thell.focus.databinding.FragmentBottomSheetHistoryItemDetailsBinding
import com.thell.focus.helper.global.GuiHelper

class HistoryDetailBottomSheetDialogFragment: BottomSheetDialogFragment()
{

    lateinit var historyDetailFragmentIcon: ImageView
    lateinit var historyDetailFragmentPackageName: TextView
    lateinit var historyDetailFragmentDetailContent: TextView
    lateinit var historyDetailFragmentPostTime: TextView
    lateinit var historyDetailFragmentMuteStateIcon: ImageView

    lateinit var notificationEntity: NotificationEntity
    private  var binding: FragmentBottomSheetHistoryItemDetailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentBottomSheetHistoryItemDetailsBinding.inflate(layoutInflater)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null)
        {
            val notificationBundle = HistoryDetailBottomSheetDialogFragmentArgs.fromBundle(requireArguments())
            notificationEntity = notificationBundle.historyNavigation!!
            initUI()
            init()
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun init()
    {
        historyDetailFragmentIcon.background = getIcon(requireContext(),notificationEntity)
        historyDetailFragmentPackageName.text = notificationEntity.ApplicationName
        historyDetailFragmentDetailContent.text =  notificationEntity.Ticket
        var postTime = GuiHelper.epochToDate(notificationEntity.PostTime)
        historyDetailFragmentPostTime.text = postTime

        if(notificationEntity.MuteState)
            historyDetailFragmentMuteStateIcon.setImageResource(R.drawable.ic_notifications_off_black_24dp)
        else
            historyDetailFragmentMuteStateIcon.setImageResource(R.drawable.ic_notifications_black_24dp)
    }

    private fun getIcon(context: Context, notificationEntity: NotificationEntity) =
        GuiHelper.getIcon(context, notificationEntity.PackageName)

    private fun initUI()
    {
        historyDetailFragmentIcon =  binding!!.notificationHistoryDetailIconImageview
        historyDetailFragmentPackageName = binding!!.notificationHistoryDetailPackageNameTextview
        historyDetailFragmentDetailContent = binding!!.notificationHistoryDetailContentTextview
        historyDetailFragmentPostTime =  binding!!.notificationHistoryDetailPosttimeTextview
        historyDetailFragmentMuteStateIcon = binding!!.notificationHistoryDetailMutestateImageview

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


}