package com.example.sportevent.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sportevent.R
import com.example.sportevent.ui.fragments.PreviousMatchesFragment
import com.example.sportevent.ui.fragments.TeamsFragment
import com.example.sportevent.ui.fragments.UpcomingMatchesFragment

class MatchesPagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        const val TEAM = 0
        const val PREVIOUS = 1
        const val UPCOMING = 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TeamsFragment()
            1 -> PreviousMatchesFragment()
            2 -> UpcomingMatchesFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            TEAM -> context.getString(R.string.teams)
            PREVIOUS -> context.getString(R.string.previous_matches)
            UPCOMING -> context.getString(R.string.upcoming_matches)
            else -> null
        }
    }
}