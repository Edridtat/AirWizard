package com.goodelephantlab.airwizard.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.data.model.CalendarTicket
import com.goodelephantlab.airwizard.utils.loadImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tickets.*
import java.text.SimpleDateFormat
import java.util.*


class TicketsCalendarAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var elements = arrayListOf<CalendarTicket>()
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
            tvChangesValue.text = element.transfers.toString()
            tvFlightNumberValue.text = element.flightNumber.toString()
            tvPrice.text = tvPrice.context.getString(R.string.price, element.price)
            inputFormatter.parse(element.departureAt)?.let {
                tvDepTimeValue.text = outputFormatter.format(it)
            }
            inputFormatter.parse(element.returnAt)?.let {
                tvReturnTimeValue.text = outputFormatter.format(it)
            }
            ivCompanyLogo.loadImage(element.airline)
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }


    fun setData(elements: ArrayList<CalendarTicket>) {
        this.elements.clear()
        this.elements = elements
        notifyDataSetChanged()
    }

    inner class TicketsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer
}
