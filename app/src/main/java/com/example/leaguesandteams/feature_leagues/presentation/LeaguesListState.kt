package com.example.leaguesandteams.feature_leagues.presentation

import com.example.leaguesandteams.feature_leagues.data.remote.League

data class LeaguesListState(
    val LeaguesList: List<League> = emptyList(),
    val isLoading: Boolean = false
)