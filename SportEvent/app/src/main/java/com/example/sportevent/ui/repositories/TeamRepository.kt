package com.example.sportevent.ui.repositories

import com.example.sportevent.data.remote.ApiService
import com.example.sportevent.data.model.Team
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TeamRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllTeams(callback: (Team) -> Unit) {
        val call: Call<Team> = apiService.getTeams()

        call.enqueue(object : Callback<Team?> {
            override fun onResponse(call: Call<Team?>, response: Response<Team?>) {
                if (response.isSuccessful) {
                    val teams = response.body()
                    teams?.let { callback(it) }
                }
            }

            override fun onFailure(call: Call<Team?>, t: Throwable) {

            }
        })
    }
}