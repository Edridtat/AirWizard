package com.goodelephantlab.airwizard.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.ui.MainViewModel
import com.goodelephantlab.airwizard.ui.adapters.TicketsCalendarAdapter
import com.goodelephantlab.airwizard.ui.adapters.TicketsCheapestAdapter
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.CALENDAR_TICKETS
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TicketsFragment : Fragment() {

    companion object {
        private const val ARG_TYPE = "arg_type"
        fun newInstance(type: Int) = TicketsFragment().apply {
            arguments = bundleOf(ARG_TYPE to type)
        }
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var rvTickets: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.tickets_fragment, container, false)
        rvTickets = view.findViewById(R.id.rvTickets)
        tvEmpty = view.findViewById(R.id.tvEmpty)
        progressBar = view.findViewById(R.id.pb)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity!!.getViewModel()

        rvTickets.layoutManager = LinearLayoutManager(context)
        rvTickets.itemAnimator = DefaultItemAnimator()

        when (arguments?.getInt(ARG_TYPE, CALENDAR_TICKETS)) {
            CALENDAR_TICKETS -> {
                rvTickets.adapter = TicketsCalendarAdapter()
                viewModel.calendarTicketsLD.observe(activity!!, Observer {
                    (rvTickets.adapter as TicketsCalendarAdapter).setData(it)
                    showEmptyIfNeed(it.isEmpty())
                })
            }
            else -> {
                rvTickets.adapter = TicketsCheapestAdapter()
                viewModel.cheapestTicketsLD.observe(activity!!, Observer {
                    (rvTickets.adapter as TicketsCheapestAdapter).setData(it)
                    showEmptyIfNeed(it.isEmpty())
                })
            }
        }
        viewModel.showProgressBarLD.observe(activity!!, Observer {
            if (it) {
                rvTickets.visibility = View.GONE
                tvEmpty.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        })
        viewModel.fetchFlightInfo()
    }

    private fun showEmptyIfNeed(emptyList: Boolean) {
        if (emptyList) {
            rvTickets.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            rvTickets.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE
        }
        progressBar.visibility = View.GONE
    }
}
