package com.example.sportevent.data.remote

import com.example.sportevent.data.model.Matches
import com.example.sportevent.data.model.Team
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("teams")
    fun getTeams(): Call<Team>

    @GET("teams/matches")
    fun getMatches(): Call<Matches>

    @GET("teams/{id}/matches")
    fun getMatchesByTeam(@Path("id") id: String): Call<Matches>
}