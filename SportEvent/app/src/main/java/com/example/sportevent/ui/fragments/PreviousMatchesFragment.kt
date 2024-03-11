package com.example.sportevent.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportevent.data.model.MatchModel
import com.example.sportevent.data.model.MatchItem
import com.example.sportevent.data.model.Matches
import com.example.sportevent.R
import com.example.sportevent.data.model.Team
import com.example.sportevent.data.model.TeamItem
import com.example.sportevent.ui.activities.VideoPlayerActivity
import com.example.sportevent.ui.viewmodels.TeamViewModel
import com.example.sportevent.ui.viewmodels.ViewModelFactory
import com.example.sportevent.ui.adapters.MatchPreviousAdapter
import com.example.sportevent.ui.utils.CommonUtils
import com.example.sportevent.ui.viewmodels.MatchViewModel

class PreviousMatchesFragment : Fragment() {

    private lateinit var matchPreviousAdapter: MatchPreviousAdapter
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var matchViewModel: MatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_previous_matches, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPreviousMatches)

        // Set up RecyclerView with adapter
        matchPreviousAdapter = MatchPreviousAdapter(requireContext(),
            Matches(
                MatchModel(
                    ArrayList(),
                    ArrayList()
                )
            )
        ) { match -> showMatchDetails(match) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = matchPreviousAdapter
        matchViewModel = ViewModelProvider(this, ViewModelFactory())[MatchViewModel::class.java]
        observeMatch()
        setHasOptionsMenu(true)
        matchViewModel.loadMatches()
        observeTeam()
        return view
    }

    private fun observeMatch() {
        // Observe teams LiveData
        matchViewModel.matches.observe(viewLifecycleOwner) { match ->
            match?.let {
                matchPreviousAdapter.updateMatches(it)
            }
        }
    }

    private fun observeTeam() {
        teamViewModel = ViewModelProvider(this, ViewModelFactory())[TeamViewModel::class.java]

        // Observe teams LiveData
        teamViewModel.teams.observe(viewLifecycleOwner) { teams ->
            teams?.let {
                //Add this item to filter all team
                if (teams.teams[0].id != "All")
                    teams.teams.add(
                        0, TeamItem(
                            context?.getString(R.string.all)!!,
                            context?.getString(R.string.all_team)!!, ""
                        )
                    )
                showTeamSelectionDialog(teams)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.matches_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter_team -> {
                teamViewModel.loadTeams()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTeamSelectionDialog(team: Team) {
        // Implement a dialog or another UI element for team selection
        // Update the 'selectedTeam' variable accordingly
        val teamNames = CommonUtils.getTeamNames(team).toTypedArray()

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(context?.getString(R.string.select_a_team))
            .setItems(teamNames) { _, which ->
                if (team.teams[which].id == context?.getString(R.string.all))
                    matchViewModel.loadMatches()
                else
                    matchViewModel.loadMatchesByTeam(team.teams[which].id)
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showMatchDetails(match: MatchItem) {
        val intent = Intent(activity, VideoPlayerActivity::class.java)
        intent.putExtra(VideoPlayerActivity.VIDEO, match.highlights)
        startActivity(intent)
    }

}