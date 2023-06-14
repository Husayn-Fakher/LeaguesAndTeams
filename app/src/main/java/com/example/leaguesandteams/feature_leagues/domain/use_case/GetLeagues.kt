package com.example.leaguesandteams.feature_leagues.domain.use_case

import com.example.leaguesandteams.core.util.Resource
import com.example.leaguesandteams.feature_leagues.data.remote.League
import kotlinx.coroutines.flow.Flow
import my.app.fdj.feature_leagues.domain.repository.LeaguesRepository

class GetLeagues(private val repository: LeaguesRepository) {
    operator fun invoke(): Flow<Resource<List<League>>> {
        return repository.getLeagues()
    }
}