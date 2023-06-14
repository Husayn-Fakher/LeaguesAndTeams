package com.example.leaguesandteams

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.leaguesandteams.ui.theme.LeaguesAndTeamsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import my.app.fdj.feature_leagues.presentation.LeaguesViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeaguesAndTeamsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background

                ) {
                    MainContent()
                }
            }
        }
    }


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainContent() {
        val viewModel: LeaguesViewModel = hiltViewModel()
        val state = viewModel.state.value
        val stateTeams = viewModel.stateTeams.value
        val scaffoldState = rememberScaffoldState()

        LaunchedEffect(key1 = true) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is LeaguesViewModel.UIEvent.ShowSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }

                    else -> {}
                }
            }
        }


        Scaffold(scaffoldState = scaffoldState)
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading || stateTeams.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(250.dp)
                            .padding(16.dp),
                        strokeWidth = 8.dp
                    )
                }
                Column(
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    DisplayList(
                        leagues = state.LeaguesList,
                        stateTeams.TeamsList,
                        onLeagueSelected = { selectedLeague ->
                            viewModel.getTeamsList(selectedLeague.strLeague)
                        }
                    )
                }
            }
        }
    }
}
