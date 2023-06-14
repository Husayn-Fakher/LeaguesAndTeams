package com.example.leaguesandteams.feature_leagues.di

import com.example.leaguesandteams.feature_leagues.data.remote.LeaguesApiService
import com.example.leaguesandteams.feature_leagues.data.repository.LeaguesRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.app.fdj.feature_leagues.domain.repository.LeaguesRepository
import com.example.leaguesandteams.feature_leagues.domain.use_case.GetLeagues
import my.app.fdj.feature_leagues.domain.use_case.GetTeams
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModuel {

    @Provides
    @Singleton
    fun provideGetLeaguesUseCase(repository: LeaguesRepository): GetLeagues {
        return GetLeagues(repository)
    }

    @Provides
    @Singleton
    fun provideGetTeamUseCase(repository: LeaguesRepository): GetTeams {
        return GetTeams(repository)
    }

    @Provides
    @Singleton
    fun provideLeaguesRepository(
        api: LeaguesApiService,
    ): LeaguesRepository {
        return LeaguesRepositoryImplementation(api)
    }

    @Provides
    fun provideLeaguesApiService(): LeaguesApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/api/v1/json/50130162/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(LeaguesApiService::class.java)
    }
}