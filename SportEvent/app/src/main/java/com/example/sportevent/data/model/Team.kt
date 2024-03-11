package com.example.sportevent.data.model

data class Team(val teams: ArrayList<TeamItem>)

data class TeamItem(val id: String, val name: String, val logo: String)
