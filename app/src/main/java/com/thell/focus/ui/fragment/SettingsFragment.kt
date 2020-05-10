package com.thell.focus.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thell.focus.FocusApplication
import com.thell.focus.adapter.SettingsAdapter
import com.thell.focus.database.AppDatabase
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.databinding.FragmentSettingsBinding
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.focus.helper.navigation.NavigationMenuHelper
import com.thell.focus.helper.settings.SettingsHelper
import com.thell.focus.repository.repo.SettingsRepository
import com.thell.focus.viewmodel.SettingsViewModel
import java.util.*
import kotlin.collections.ArrayList


class SettingsFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener
{

    private lateinit var navController: NavController
    private  var binding: FragmentSettingsBinding? = null
    private lateinit var viewModel:SettingsViewModel

    private lateinit var settingsList:List<SettingsEntity>
    private lateinit var adapter : SettingsAdapter
    private lateinit var recycleView : RecyclerView
    private lateinit var searchBox: SearchView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var filter: Filter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        initUI()
        init()
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
            callback.changeHeader(NavigationMenuHelper.SETTING)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        getSettings()

    }



    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private  fun initUI()
    {
        recycleView = binding!!.fragmentSettingsRecycleView
        searchBox =  binding!!.fragmentSettingsSearchView
        swipeRefresh =  binding!!.fragmentSettingsSwipeRefresh
        swipeRefresh.setOnRefreshListener(this)
    }

    private fun init()
    {
        initFilter()
        initSearchBox()
    }


    private fun clickSettings(settingsEntity: SettingsEntity)
    {
        viewModel.updateByKey(settingsEntity)
        SettingsHelper.allSettings.first { it.ID == settingsEntity.ID }.State = settingsEntity.State

        /* if(settingsEntity.SettingsKey == SettingsHelper.Companion::SETTINGS_KEY_IS_NOTIFICATION_SAVED_ALWAYS.name)
         {
             if(settingsEntity.State == SettingsStateType.OK.state)
             {
                 NotificationServiceHelper.setStateService(context!!,true)
             }
         }*/
    }

    private fun initSearchBox()
    {
        searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter.filter(newText)
                return false
            }

        })
    }

    private fun getSettings()
    {
        viewModel.getAllNote().observe(viewLifecycleOwner, Observer {
            settingsList = it
            setupRecyclerView()
        })
    }

    private fun setupRecyclerView()
    {
        adapter = SettingsAdapter(
            requireContext(),
            settingsList,
            ::clickSettings
        )
        recycleView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recycleView.adapter = adapter
    }

    override fun onRefresh()
    {
        searchBox.setQuery("",false)
        init()
        getSettings()
        swipeRefresh.isRefreshing = false
    }


    private fun initFilter()
    {
        filter = object : android.widget.Filter()
        {
            override fun performFiltering(constraint: CharSequence?): FilterResults
            {
                val result = FilterResults()

                if (constraint.isNullOrEmpty() )
                {

                    val resultList = ArrayList<SettingsEntity>()
                    for (d in settingsList)
                    {
                        resultList.add(d)
                    }

                    result.count = resultList.size
                    result.values = resultList
                }
                else
                {
                    val resultList = ArrayList<SettingsEntity>()
                    for (d in settingsList)
                    {
                        if (d.SettingsDescription.toLowerCase().contains(constraint.toString().toLowerCase()))
                            resultList.add(d)

                    }

                    result.count = resultList.size
                    result.values = resultList

                }

                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?)
            {
                @Suppress("UNCHECKED_CAST")
                if(results?.values != null)
                    adapter = SettingsAdapter(context!!,results.values as List<SettingsEntity>,::clickSettings)
                else
                    adapter = SettingsAdapter(context!!, arrayListOf(),::clickSettings)

                recycleView.adapter = adapter
            }

        }

    }

}