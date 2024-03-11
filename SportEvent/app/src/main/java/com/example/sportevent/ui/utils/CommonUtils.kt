package com.example.sportevent.ui.utils

import com.example.sportevent.data.model.MatchItem
import com.example.sportevent.data.model.Team
import java.text.SimpleDateFormat
import java.util.Date

object CommonUtils {
    fun getTeamNames(team: Team): ArrayList<String>{
        var stringArray = ArrayList<String>()
        for (teams in team.teams){
            stringArray.add(teams.name)
        }
        return stringArray
    }

    fun convertDate(dateString: String): Date{
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(
            dateString?.replace(
                "Z$".toRegex(),
                "+0000"
            )
        )
    }

    fun formatDateTime(date: Date): String{
        var dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
        return dateFormat.format(date)
    }
}