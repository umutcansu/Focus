package com.thell.focus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.databinding.SettingsItemLayoutBinding
import com.thell.focus.helper.settings.Settings
import com.thell.focus.helper.settings.SettingsHelper

class SettingsAdapter(private val context: Context,
                      private val data:List<SettingsEntity>,
                      private val clickListener : (current:SettingsEntity) -> Unit = {})
    : RecyclerView.Adapter<SettingsAdapter.ViewHolder>()
{

    private lateinit var binding: SettingsItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        binding = SettingsItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        val view = binding.root
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.setData(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(item:SettingsEntity)
        {
            if(item.State == Settings.StateType.OK)
            {
                binding.checkboxSettingsState.isChecked = true
            }
            binding.tvSettingsName.text = item.SettingsDescription

            binding.checkboxSettingsState.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if(isChecked)
                    {
                        item.State = Settings.StateType.OK
                    }
                    else
                    {
                        item.State = Settings.StateType.NOK
                    }

                    clickBehavior(item)
                }
            })
        }

        private fun clickBehavior(item: SettingsEntity)
        {
            clickListener(item)
        }
    }
}