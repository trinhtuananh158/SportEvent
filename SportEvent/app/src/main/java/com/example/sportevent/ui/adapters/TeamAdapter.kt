package com.example.sportevent.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportevent.R
import com.example.sportevent.data.model.Team
import com.example.sportevent.data.model.TeamItem

class TeamAdapter(private var teams: Team, private val onItemClick: (TeamItem) -> Unit) :
    RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamNameTextView: TextView = view.findViewById(R.id.textViewTeamName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_team, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = teams.teams[position]
        holder.teamNameTextView.text = team.name

        // Set click listeners or other interactions here
        holder.itemView.setOnClickListener {
            onItemClick(team)
        }
    }

    override fun getItemCount(): Int {
        return teams.teams.size
    }

    fun updateTeams(newTeams: Team) {
        teams = newTeams
        notifyDataSetChanged()
    }
}