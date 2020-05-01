package com.goodelephantlab.airwizard.ui.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.data.model.Autocomplete
import com.goodelephantlab.airwizard.ui.MainViewModel
import com.goodelephantlab.airwizard.ui.adapters.SearchPointsAdapter
import com.goodelephantlab.airwizard.utils.constants.PointsDefinition.ORIGIN
import kotlinx.android.synthetic.main.point_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class PointFragment : Fragment(), SearchPointsAdapter.OnClickListener {

    companion object {
        private const val ARG_DEFINITION = "arg_definition"
        fun newInstance(definition: Int) = PointFragment().apply {
            arguments = bundleOf(ARG_DEFINITION to definition)
        }
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.point_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity!!.getViewModel()

        rvSearch.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        rvSearch.itemAnimator = DefaultItemAnimator()
        rvSearch.adapter = SearchPointsAdapter()
        (rvSearch.adapter as SearchPointsAdapter).onClickListener = this

        searchToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        searchToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        searchToolbar.title = when (arguments!!.getInt(ARG_DEFINITION)) {
            ORIGIN -> getString(R.string.title_origin)
            else -> getString(R.string.title_destination)
        }
        searchToolbar.inflateMenu(R.menu.menu_toolbar_search)
        searchView = searchToolbar.menu.findItem(R.id.item_search).actionView as SearchView
        searchView.isIconified = false
        searchView.queryHint = when (arguments!!.getInt(ARG_DEFINITION)) {
            ORIGIN -> getString(R.string.point_origin)
            else -> getString(R.string.point_destination)
        }
        viewModel.searchPoints(searchView)
        viewModel.pointsLD.observe(activity!!, Observer {
            it.let { elements ->
                rvSearch?.let { (it.adapter as SearchPointsAdapter).setList(elements) }
            }
        })
    }

    override fun onClick(position: Int, element: Autocomplete) {
        viewModel.savePoint(element, arguments!!.getInt(ARG_DEFINITION))
        activity?.onBackPressed()
    }
}
