package com.example.sportevent.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sportevent.data.remote.ApiClient
import com.example.sportevent.ui.repositories.MatchRepository
import com.example.sportevent.ui.repositories.TeamRepository

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            return TeamViewModel(TeamRepository(ApiClient.apiService)) as T
        }
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(MatchRepository(ApiClient.apiService)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}