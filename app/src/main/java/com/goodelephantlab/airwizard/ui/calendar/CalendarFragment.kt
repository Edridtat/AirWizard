package com.goodelephantlab.airwizard.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.ui.MainViewModel
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.ORIGIN
import kotlinx.android.synthetic.main.calendar_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CalendarFragment : Fragment() {

    companion object {
        private const val ARG_DEFINITION = "arg_definition"
        fun newInstance(definition: Int) = CalendarFragment().apply {
            arguments = bundleOf(ARG_DEFINITION to definition)
        }
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.calendar_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity!!.getViewModel()
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        when (arguments!!.getInt(ARG_DEFINITION)) {
            ORIGIN -> {
                toolbar.title = getString(R.string.title_flight)
                calendarView?.minDate = System.currentTimeMillis()
                viewModel.departDateLD.observe(activity!!, Observer {
                    it?.let {
                        calendarView?.date = it
                    }
                })
            }
            else -> {
                toolbar.title = getString(R.string.title_return_flight)
                viewModel.departDateLD.observe(activity!!, Observer {
                    it?.let {
                        calendarView?.minDate = it
                    }
                })
                viewModel.fetchDepartmentDate()
            }
        }
        calendarView.setOnDateChangeListener { _, year, month, day ->
            viewModel.addDate(year, month, day, arguments!!.getInt(ARG_DEFINITION))
        }
    }
}
