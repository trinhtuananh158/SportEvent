package com.example.sportevent.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportevent.data.model.Team
import com.example.sportevent.ui.repositories.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(private val teamRepository: TeamRepository) : ViewModel() {

    private val _teams = MutableLiveData<Team>()
    val teams get() = _teams

    fun loadTeams() {
        teamRepository.getAllTeams { teamsList ->
            _teams.postValue(teamsList)
        }
    }
}