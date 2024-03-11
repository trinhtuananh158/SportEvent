package com.example.sportevent.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportevent.data.model.Matches
import com.example.sportevent.data.model.Team
import com.example.sportevent.ui.repositories.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val matchRepository: MatchRepository) : ViewModel() {

    private val _matches = MutableLiveData<Matches>()
    val matches get() = _matches

    fun loadMatches() {
        matchRepository.getMatches { matches ->
            _matches.postValue(matches)
        }
    }

    fun loadMatchesByTeam(id: String) {
        matchRepository.getMatchesByTeam(id) { matches ->
            _matches.postValue(matches)
        }
    }
}