package com.goodelephantlab.airwizard.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.ui.MainViewModel
import com.goodelephantlab.airwizard.ui.calendar.CalendarFragment
import com.goodelephantlab.airwizard.ui.points.PointFragment
import com.goodelephantlab.airwizard.ui.tickets.TicketsPagerFragment
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.DESTINATION
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.ORIGIN
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity!!.getViewModel()

        tvPoint1.setOnClickListener { openPointsFragment(PointFragment.newInstance(ORIGIN)) }
        tvPoint2.setOnClickListener { openPointsFragment(PointFragment.newInstance(DESTINATION)) }
        tvDate1.setOnClickListener { openCalendarFragment(CalendarFragment.newInstance(ORIGIN)) }
        tvDate2.setOnClickListener { openCalendarFragment(CalendarFragment.newInstance(DESTINATION)) }
        ivDate2Clear.setOnClickListener { viewModel.clearReturnDate() }
        btnSearch.setOnClickListener { openTicketsFragment() }

        viewModel.originLD.observe(activity!!, Observer {
            tvPoint1.text = it.name
            viewModel.enableBtnIfNeed()
        })
        viewModel.destinationLD.observe(viewLifecycleOwner, Observer {
            tvPoint2.text = it.name
            viewModel.enableBtnIfNeed()
        })

        val formatter = SimpleDateFormat("dd MMMM yyyy (EEEE)", Locale.getDefault())

        viewModel.departDateLD.observe(activity!!, Observer {
            if (it != 0L) {
                tvDate1.text = formatter.format(it)
                tvDate2.isEnabled = true
                ivDate2.setImageResource(R.drawable.ic_calendar2_enabled)
                viewModel.enableBtnIfNeed()
            }
        })
        viewModel.returnDateLD.observe(activity!!, Observer {
            if (it != 0L) {
                tvDate2.text = formatter.format(it)
                ivDate2Clear.visibility = View.VISIBLE
            } else {
                tvDate2.text = getString(R.string.add_return_flight)
                ivDate2Clear.visibility = View.GONE
            }
        })

        viewModel.enableSearchLd.observe(activity!!, Observer {
            btnSearch.isEnabled = it
        })

        viewModel.fetchOrigin()
        viewModel.fetchDestination()
        viewModel.fetchDepartmentDate()
        viewModel.fetchReturnDate()
    }

    private fun openPointsFragment(fragment: PointFragment) {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack(PointFragment::class.java.name)
            .commit()
    }

    private fun openCalendarFragment(fragment: CalendarFragment) {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, fragment)
            .addToBackStack(CalendarFragment::class.java.name)
            .commit()
    }

    private fun openTicketsFragment() {
        parentFragmentManager.beginTransaction()
            .add(R.id.container, TicketsPagerFragment.newInstance())
            .addToBackStack(TicketsPagerFragment::class.java.name)
            .commit()
    }

}
