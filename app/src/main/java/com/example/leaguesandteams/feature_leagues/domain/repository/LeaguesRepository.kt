package my.app.fdj.feature_leagues.domain.repository

import com.example.leaguesandteams.core.util.Resource
import com.example.leaguesandteams.feature_leagues.data.remote.League
import com.example.leaguesandteams.feature_leagues.data.remote.Team
import kotlinx.coroutines.flow.Flow


interface LeaguesRepository {

    fun getLeagues(): Flow<Resource<List<League>>>

    fun getTeams(leagueName: String): Flow<Resource<List<Team>>>
}