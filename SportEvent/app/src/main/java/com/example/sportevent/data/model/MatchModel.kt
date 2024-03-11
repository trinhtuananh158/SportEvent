package com.example.sportevent.data.model

data class Matches(val matches: MatchModel)

data class MatchModel(val previous: ArrayList<MatchItem>, val upcoming: ArrayList<MatchItem>)

data class MatchItem(val date: String, val description: String, val home: String, val away: String,
                     val winner: String, val highlights: String, var reminder: Boolean)
