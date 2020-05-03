package com.thell.focus.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thell.focus.R
import com.thell.mutenotification.model.NavigationDrawerItem

class NavigationDrawerAdapter(val context: Context, val data: ArrayList<NavigationDrawerItem>,val menuChangeListener:(current:NavigationDrawerItem) -> Unit = {}) :
    RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)

        val v = inflater.inflate(R.layout.navigation_drawer_item_layout, p0, false)

        return ViewHolder(v)

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
           /* itemView.navigationDrawerItemIcon.setImageResource(current.icon)
            itemView.navigationDrawerItemText.text = current.title

            setBackgroundColorMenuItem(current.selected)

            itemView.rootLayout.setOnClickListener{

                if(getSelected() != current)
                {
                    menuChangeListener(current)
                    setSelected(current)
                }

            }*/
        }

        private fun setBackgroundColorMenuItem(state:Boolean):Boolean
        {
           /* if(state)
                itemView.rootLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorSelectedMenuItem))
            else
                itemView.rootLayout.setBackgroundColor(ContextCompat.getColor(itemView.context,  R.color.colorMenuItem))

            return state*/
            return false
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