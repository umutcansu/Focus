package com.thell.focus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thell.focus.R
import com.thell.focus.databinding.FragmentMainBinding
import com.thell.focus.databinding.NavigationDrawerItemLayoutBinding
import com.thell.mutenotification.model.NavigationDrawerItem

class NavigationDrawerAdapter(val context: Context, val data: ArrayList<NavigationDrawerItem>,val menuChangeListener:(current:NavigationDrawerItem) -> Unit = {}) :
    RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>() {

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        binding = null
    }

    var binding: NavigationDrawerItemLayoutBinding? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        binding = NavigationDrawerItemLayoutBinding.inflate(inflater,p0,false)

        return ViewHolder(binding!!.root)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int)
    {
        val current = data[p1]
        p0.setData(current)
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        fun setData(current: NavigationDrawerItem)
        {

            binding!!.navigationDrawerItemIcon.setImageResource(current.icon)
            binding!!.navigationDrawerItemText.text = current.title

            setBackgroundColorMenuItem(current.selected)

            binding!!.mainLayout.setOnClickListener{

                if(getSelected() != current)
                {
                    menuChangeListener(current)
                    setSelected(current)
                }

            }
        }

        private fun setBackgroundColorMenuItem(state:Boolean):Boolean
        {
            if(state)
                binding!!.mainLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorSelectedMenuItem))
            else
                binding!!.mainLayout.setBackgroundColor(ContextCompat.getColor(itemView.context,  R.color.colorMenuItem))

            return state
        }

        private fun setSelected(current: NavigationDrawerItem)
        {
            for (i in data)
            {
               i.selected = setBackgroundColorMenuItem(i == current)
            }
            notifyDataSetChanged()

        }

        private fun getSelected():NavigationDrawerItem
        {
            return data.first { it.selected }
        }
    }
}