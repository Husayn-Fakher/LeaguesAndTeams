package com.example.leaguesandteams.feature_leagues.data.repository

import com.example.leaguesandteams.core.util.Resource
import com.example.leaguesandteams.feature_leagues.data.remote.League
import com.example.leaguesandteams.feature_leagues.data.remote.LeaguesApiService
import com.example.leaguesandteams.feature_leagues.data.remote.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.app.fdj.feature_leagues.domain.repository.LeaguesRepository
import retrofit2.HttpException
import java.io.IOException

class LeaguesRepositoryImplementation(private val api: LeaguesApiService) : LeaguesRepository {

    override fun getLeagues(): Flow<Resource<List<League>>> = flow {
        emit(Resource.Loading())
        try {
            val leaguesResponse = api.getAllLeagues()
            emit(Resource.Success(leaguesResponse.leagues))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Something went wrong",
                    )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Could not reach server, check your internet connection",
                    )
            )
        }
    }

    override fun getTeams(leagueName: String): Flow<Resource<List<Team>>> = flow {
        emit(Resource.Loading())
        try {
            val teamsResponse = api.getTeams(leagueName)
            emit(Resource.Success(teamsResponse.teams))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Something went wrong",
                    )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Could not reach server, check your internet connection",
                    )
            )
        }
    }
}