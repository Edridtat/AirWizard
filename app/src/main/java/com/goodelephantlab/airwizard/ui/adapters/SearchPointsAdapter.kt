package com.goodelephantlab.airwizard.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.data.model.Autocomplete
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_points.*


class SearchPointsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var elementsList: MutableList<Autocomplete> = mutableListOf()
    var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_points, parent, false)
        return PointsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as PointsViewHolder
        val element = elementsList[position]
        viewHolder.tvPointName.text = element.name
        viewHolder.itemView.setOnClickListener {
            onClickListener?.onClick(position, element)
        }
    }

    override fun getItemCount(): Int {
        return elementsList.size
    }

    fun setList(elements: List<Autocomplete>) {
        this.elementsList.clear()
        this.elementsList.addAll(elements)
        notifyDataSetChanged()
    }

    inner class PointsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer

    interface OnClickListener {
        fun onClick(position: Int, element: Autocomplete)
    }
}
