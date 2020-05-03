package com.thell.focus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.databinding.SettingsItemLayoutBinding

class SettingsAdapter(val context: Context, val data:List<SettingsEntity>, val clickListener : (current:SettingsEntity) -> Unit = {}) :
RecyclerView.Adapter<SettingsAdapter.ViewHolder>()
{

    private lateinit var binding: SettingsItemLayoutBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      /*  val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.settings_item_layout,parent,false)*/

        binding = SettingsItemLayoutBinding.inflate(LayoutInflater.from(context))
        val view = binding.root
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(item:SettingsEntity)
        {

        }

        fun clickBehavior(item: SettingsEntity)
        {

        }
    }
}