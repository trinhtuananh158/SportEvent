package com.example.sportevent.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportevent.R
import com.example.sportevent.data.model.MatchItem
import com.example.sportevent.data.model.MatchModel
import com.example.sportevent.data.model.Matches
import com.example.sportevent.data.model.Team
import com.example.sportevent.data.model.TeamItem
import com.example.sportevent.ui.adapters.MatchUpcomingAdapter
import com.example.sportevent.ui.utils.CommonUtils
import com.example.sportevent.ui.utils.Notification
import com.example.sportevent.ui.viewmodels.MatchViewModel
import com.example.sportevent.ui.viewmodels.TeamViewModel
import com.example.sportevent.ui.viewmodels.ViewModelFactory
import java.util.Calendar


class UpcomingMatchesFragment : Fragment() {

    private lateinit var matchUpcomingAdapter: MatchUpcomingAdapter
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var matchViewModel: MatchViewModel
    private var isAllMatch = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tab_upcoming_matches, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewUpcomingMatches)

        // Set up RecyclerView with adapter
        matchUpcomingAdapter = MatchUpcomingAdapter(requireContext(),
            Matches(
                MatchModel(
                    ArrayList(),
                    ArrayList()
                )
            )
        ) { match -> reminderUser(match) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = matchUpcomingAdapter
        matchViewModel = ViewModelProvider(this, ViewModelFactory())[MatchViewModel::class.java]
        handleMatch()
        matchViewModel.loadMatches()
        handleTeam()
        setHasOptionsMenu(true)
        return view
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

    private fun handleMatch() {
        // Observe match LiveData
        matchViewModel.matches.observe(viewLifecycleOwner) { match ->
            match?.let {
                matchUpcomingAdapter.updateMatches(it)
                if(isAllMatch)
                    it.matches.upcoming.add(MatchItem("2024-03-10T18:00:00.000Z", "Testing reminder match",
                    "", "", "", "", false))
                setRemindersForUpcomingMatches(it)
            }
        }
    }

    private fun handleTeam() {
        teamViewModel = ViewModelProvider(this, ViewModelFactory())[TeamViewModel::class.java]

        // Observe teams LiveData
        teamViewModel.teams.observe(viewLifecycleOwner) { teams ->
            teams?.let {
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

    private fun setRemindersForUpcomingMatches(matches: Matches) {
        val now = Calendar.getInstance().time
        for (match in matches.matches.upcoming) {
            if (match.reminder && CommonUtils.convertDate(match.date).time <= now.time) {
                Notification.createNotification(context, match.description)
            }
        }
    }

    private fun showTeamSelectionDialog(team: Team) {
        // Implement a dialog or another UI element for team selection
        // Update the 'selectedTeam' variable accordingly
        val teamNames = CommonUtils.getTeamNames(team).toTypedArray()

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(context?.getString(R.string.select_a_team))
            .setItems(teamNames) { _, which ->
                if (team.teams[which].id == "All") {
                    isAllMatch = true
                    matchViewModel.loadMatches()
                }
                else {
                    isAllMatch = false
                    matchViewModel.loadMatchesByTeam(team.teams[which].id)
                }
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun reminderUser(match: MatchItem) {
        // For simplicity, we'll just print a reminder message here
        AlertDialog.Builder(context)
            .setMessage(context?.getString(R.string.reminder_message)
                ?.let { String.format(it, match.description) })
            .setPositiveButton(
                context?.getString(R.string.yes)
            ) { _, _ ->
                val now = Calendar.getInstance().time
                match.reminder = true
                Toast.makeText(
                    context, context?.getString(R.string.set_reminder_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                if (CommonUtils.convertDate(match.date).time <= now.time) {
                    Notification.createNotification(context, match.description)
                }
            }
            .setNegativeButton(context?.getString(R.string.cancel), null)
            .show()
    }
}