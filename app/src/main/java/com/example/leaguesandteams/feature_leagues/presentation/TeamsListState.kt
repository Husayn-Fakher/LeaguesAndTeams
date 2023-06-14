package com.example.leaguesandteams.feature_leagues.presentation

import com.example.leaguesandteams.feature_leagues.data.remote.Team

data class TeamsListState(
    val TeamsList: List<Team> = emptyList(),
    val isLoading: Boolean = false
)