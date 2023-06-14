package my.app.fdj.feature_leagues.domain.use_case

import com.example.leaguesandteams.core.util.Resource
import com.example.leaguesandteams.feature_leagues.data.remote.Team
import kotlinx.coroutines.flow.Flow
import my.app.fdj.feature_leagues.domain.repository.LeaguesRepository


class GetTeams(private val repository: LeaguesRepository) {
    operator fun invoke(league: String): Flow<Resource<List<Team>>> {
        return repository.getTeams(league)
    }
}