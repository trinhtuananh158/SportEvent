package com.example.sportevent.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportevent.data.model.MatchItem
import com.example.sportevent.data.model.Matches
import com.example.sportevent.R
import com.example.sportevent.ui.utils.CommonUtils

class MatchPreviousAdapter(
    private var context: Context,
    private var matches: Matches,
    private val onItemClick: (MatchItem) -> Unit
) : RecyclerView.Adapter<MatchPreviousAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamNameTextView: TextView = view.findViewById(R.id.textViewTeamName)
        val matchDetailsTextView: TextView = view.findViewById(R.id.textViewMatchDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_match, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var match = matches.matches.previous[position]

        holder.teamNameTextView.text =
            String.format(context.getString(R.string.match), match.description)
        holder.matchDetailsTextView.text =
            String.format(context.getString(R.string.date),
                CommonUtils.formatDateTime(CommonUtils.convertDate(match.date)))

        // Set click listeners
        holder.itemView.setOnClickListener {
            onItemClick(match)
        }
    }

    override fun getItemCount(): Int {
        return matches.matches.previous.size
    }

    fun updateMatches(newMatches: Matches) {
        matches = newMatches
        notifyDataSetChanged()
    }
}