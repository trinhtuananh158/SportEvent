package com.example.sportevent.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportevent.R
import com.example.sportevent.data.model.Team
import com.example.sportevent.data.model.TeamItem
import com.example.sportevent.ui.viewmodels.TeamViewModel
import com.example.sportevent.ui.viewmodels.ViewModelFactory
import com.example.sportevent.ui.adapters.TeamAdapter

class TeamsFragment : Fragment() {

    private lateinit var teamAdapter: TeamAdapter
    private lateinit var teamViewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewTeams)

        teamAdapter = TeamAdapter(Team(ArrayList())) { teamItem -> showTeamDetails(teamItem) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = teamAdapter

        // Fetch team data from the API
        // Create ViewModel
        teamViewModel = ViewModelProvider(this, ViewModelFactory())[TeamViewModel::class.java]

        // Observe teams LiveData
        teamViewModel.teams.observe(viewLifecycleOwner) { teams ->
            teams?.let {
                teamAdapter.updateTeams(it)
            }
        }

        // Load teams
        teamViewModel.loadTeams()
        setHasOptionsMenu(true)

        return view
    }

    private fun showTeamDetails(team: TeamItem) {
    }
}