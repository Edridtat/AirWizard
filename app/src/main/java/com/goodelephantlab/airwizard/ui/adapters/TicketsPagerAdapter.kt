package com.goodelephantlab.airwizard.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goodelephantlab.airwizard.R
import com.goodelephantlab.airwizard.ui.tickets.TicketsFragment

class TicketsPagerAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return TicketsFragment.newInstance(position)
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun getPageTitle(position: Int): String {
        return when (position) {
            0 -> fragment.getString(R.string.tickets_calendar)
            else -> fragment.getString(R.string.tickets_cheap)
        }
    }
}