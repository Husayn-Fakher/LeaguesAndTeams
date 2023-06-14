package my.app.fdj.feature_leagues.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaguesandteams.core.util.Resource
import com.example.leaguesandteams.feature_leagues.domain.use_case.GetLeagues
import com.example.leaguesandteams.feature_leagues.presentation.LeaguesListState
import com.example.leaguesandteams.feature_leagues.presentation.TeamsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import my.app.fdj.feature_leagues.domain.use_case.GetTeams
import javax.inject.Inject


@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val getLeagues: GetLeagues,
    private val getTeams: GetTeams
) : ViewModel() {
    private val _state = mutableStateOf(LeaguesListState())
    val state: State<LeaguesListState> = _state

    private var getJob: Job? = null

    private val _stateTeams = mutableStateOf(TeamsListState())
    val stateTeams: State<TeamsListState> = _stateTeams

    private var getJobTeams: Job? = null

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getLeaguesList()
    }

    fun getLeaguesList() {
        getJob?.cancel()
        getJob = viewModelScope.launch {
            getLeagues().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            LeaguesList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            LeaguesList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown Error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            LeaguesList = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun getTeamsList(league: String) {
        getJobTeams?.cancel()
        getJobTeams = viewModelScope.launch {
            getTeams(league).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _stateTeams.value = stateTeams.value.copy(
                            TeamsList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        if (_stateTeams.value.TeamsList.isEmpty()) {
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    "No teams to display"
                                )
                            )
                        }
                    }
                    is Resource.Error -> {
                        _stateTeams.value = _stateTeams.value.copy(
                            TeamsList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown Error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _stateTeams.value = stateTeams.value.copy(
                            TeamsList = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}

