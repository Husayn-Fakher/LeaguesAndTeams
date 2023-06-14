package com.example.leaguesandteams.feature_leagues.data.remote


import retrofit2.http.GET
import retrofit2.http.Query

interface LeaguesApiService {

    @GET("all_leagues.php")
    suspend fun getAllLeagues(): LeaguesResponse

    @GET("search_all_teams.php")
    suspend fun getTeams(@Query("l") leagueName: String): TeamsResponse

}