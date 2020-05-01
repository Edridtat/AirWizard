package com.goodelephantlab.airwizard.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.ui.MainViewModel
import com.goodelephantlab.airwizard.ui.adapters.TicketsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.tickets_pager_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TicketsPagerFragment : Fragment() {

    companion object {
        fun newInstance() = TicketsPagerFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.tickets_pager_fragment, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity!!.getViewModel()

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        val adapter = TicketsPagerAdapter(this)
        vpTickets.adapter = adapter
        TabLayoutMediator(tlTickets, vpTickets) { tab, position ->
            tab.text = adapter.getPageTitle(position)
            vpTickets.setCurrentItem(tab.position, true)
        }.attach()
        viewModel.flightInfoTitleLD.observe(activity!!, Observer {
            toolbar.title = it
        })
    }
}
