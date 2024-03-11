package com.example.sportevent.ui.repositories

import com.example.sportevent.data.model.Matches
import com.example.sportevent.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchRepository(private val apiService: ApiService) {

    fun getMatches(callback: (Matches) -> Unit) {
        val call: Call<Matches> = apiService.getMatches()

        call.enqueue(object : Callback<Matches?> {
            override fun onResponse(call: Call<Matches?>, response: Response<Matches?>) {
                if (response.isSuccessful) {
                    val teams = response.body()
                    teams?.let{callback(it)}
                }
            }

            override fun onFailure(call: Call<Matches?>, t: Throwable) {

            }
        })
    }

    fun getMatchesByTeam(id: String, callback: (Matches) -> Unit) {
        val call: Call<Matches> = apiService.getMatchesByTeam(id)

        call.enqueue(object : Callback<Matches?> {
            override fun onResponse(call: Call<Matches?>, response: Response<Matches?>) {
                if (response.isSuccessful) {
                    val teams = response.body()
                    teams?.let{callback(it)}
                }
            }

            override fun onFailure(call: Call<Matches?>, t: Throwable) {

            }
        })
    }
}