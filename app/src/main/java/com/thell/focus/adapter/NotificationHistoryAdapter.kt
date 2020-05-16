package com.thell.focus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thell.focus.R
import com.thell.focus.database.entity.NotificationEntity
import com.thell.focus.databinding.NotificationHistoryItemLayoutBinding
import com.thell.focus.helper.global.GuiHelper

class NotificationHistoryAdapter(val context: Context,private val data:List<NotificationEntity>, val clickListener : (current:NotificationEntity) -> Unit = {})
    : RecyclerView.Adapter<NotificationHistoryAdapter.ViewHolder>()
{

    private lateinit var binding: NotificationHistoryItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = NotificationHistoryItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        val view = binding.root
        return ViewHolder(view)
    }

    override fun getItemCount():Int =  data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(notificationEntity: NotificationEntity)
        {
            binding.imgNotificationHistoryIcon.background = getIcon(context,notificationEntity)
            binding.tvPackageName.text = notificationEntity.ApplicationName
            binding.tvContent.text = notificationEntity.Ticket

            var postTime = GuiHelper.epochToDate(notificationEntity.PostTime)
            binding.tvPosttime.text = postTime

            binding.imgMuteState.apply {
                var state =
                    if(notificationEntity.MuteState)
                        R.drawable.ic_notifications_off_black_24dp
                    else
                        R.drawable.ic_notifications_black_24dp
                setImageResource(state)
            }

            binding.root.setOnClickListener {
                clickListener(notificationEntity)
            }
        }

        private fun getIcon(context: Context, notificationEntity: NotificationEntity) =
            GuiHelper.getIcon(context, notificationEntity.PackageName)
    }
}