package com.goodelephantlab.airwizard.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.data.model.LatestTicket
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tickets.*
import java.text.SimpleDateFormat
import java.util.*

//todo пока не используется
class TicketsLatestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var elements = arrayListOf<LatestTicket>()
    private val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val outputFormatter = SimpleDateFormat("dd-MM-yyy HH:mm", Locale.getDefault())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tickets, parent, false)
        return TicketsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as TicketsViewHolder
        val element = elements[position]
        with(viewHolder) {
            tvDestination.text = element.destination
            tvChangesValue.text = element.changes.toString()
            tvPrice.text = tvPrice.context.getString(R.string.price, element.price)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getItemCount(): Int {
        return elements.size
    }


    fun setData(elements: ArrayList<LatestTicket>) {
        this.elements.clear()
        this.elements = elements
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer
}
