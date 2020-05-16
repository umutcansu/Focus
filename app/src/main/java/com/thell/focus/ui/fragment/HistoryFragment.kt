package com.thell.focus.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageButton
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thell.focus.R
import com.thell.focus.adapter.NotificationHistoryAdapter
import com.thell.focus.adapter.SettingsAdapter
import com.thell.focus.database.entity.NotificationEntity
import com.thell.focus.database.entity.SettingsEntity
import com.thell.focus.databinding.FragmentHistoryBinding
import com.thell.focus.helper.global.GuiHelper
import com.thell.focus.helper.navigation.IFragmentCallback
import com.thell.focus.helper.navigation.NavigationMenuHelper
import com.thell.focus.helper.settings.SettingsHelper
import com.thell.focus.viewmodel.NotificationHistoryViewModel


class HistoryFragment :Fragment(), SwipeRefreshLayout.OnRefreshListener
{

    private lateinit var navController: NavController
    private  var binding: FragmentHistoryBinding? = null
    private lateinit var viewModel: NotificationHistoryViewModel

    private lateinit var notificationHistoryList:List<NotificationEntity>
    private lateinit var adapter : NotificationHistoryAdapter
    private lateinit var recycleView : RecyclerView
    private lateinit var searchBox: SearchView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var clearButton: ImageButton
    private lateinit var filter: Filter

    private val clearOnClick = object :View.OnClickListener
    {
        lateinit var dialog: AlertDialog
        lateinit var alertDialogBuilder: AlertDialog.Builder

        override fun onClick(p0: View)
        {
            GuiHelper.startRotatingView(null,p0,::coreClick)
        }

        private fun coreClick()
        {
            if(!::notificationHistoryList.isInitialized || notificationHistoryList.isEmpty())
                return

            if(!::alertDialogBuilder.isInitialized)
            {
                val message = getString(R.string.clearAllHistory)

                alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle(R.string.app_name)
                alertDialogBuilder.setMessage(message)
                alertDialogBuilder.setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { dialog, _ ->
                        clearHistory()
                        dialog.dismiss()
                    })
                alertDialogBuilder.setNegativeButton(
                    R.string.no,
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

    private fun clearHistory() {
        viewModel.deleteAllHistoryNotification()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
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
            callback.changeHeader(NavigationMenuHelper.HISTORY)
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationHistoryViewModel::class.java)
        getNotificationHistory()

    }



    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private  fun initUI()
    {
        recycleView = binding!!.fragmentNotificationHistoryRecycleView
        searchBox =  binding!!.fragmentNotificationHistorySearchView
        swipeRefresh =  binding!!.fragmentNotificationHistorySwipeRefresh
        swipeRefresh.setOnRefreshListener(this)
        clearButton = binding!!.fragmentNotificationHistoryClearButton
        clearButton.setOnClickListener(clearOnClick)
    }

    private fun init()
    {
        initFilter()
        initSearchBox()
    }


    private fun clickNotification(notificationEntity: NotificationEntity)
    {

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

    private fun getNotificationHistory()
    {
        viewModel.getAllNotificationHistory().observe(viewLifecycleOwner, Observer {
            notificationHistoryList = it
            setupRecyclerView()
        })
    }

    private fun setupRecyclerView()
    {
        adapter = NotificationHistoryAdapter(
            requireContext(),
            notificationHistoryList,
            ::clickNotification
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
        getNotificationHistory()
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

                    val resultList = ArrayList<NotificationEntity>()
                    for (d in notificationHistoryList)
                    {
                        resultList.add(d)
                    }

                    result.count = resultList.size
                    result.values = resultList
                }
                else
                {
                    val resultList = ArrayList<NotificationEntity>()
                    for (d in notificationHistoryList)
                    {
                        if (d.ApplicationName.toLowerCase().contains(constraint.toString().toLowerCase()))
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
                adapter =
                if(results?.values != null)
                    NotificationHistoryAdapter(requireContext(),results.values as List<NotificationEntity>,::clickNotification)
                else
                    NotificationHistoryAdapter(requireContext(), arrayListOf(),::clickNotification)

                recycleView.adapter = adapter
            }

        }

    }

}