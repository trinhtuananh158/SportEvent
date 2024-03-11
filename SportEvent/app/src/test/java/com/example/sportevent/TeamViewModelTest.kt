package com.example.sportevent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sportevent.data.model.Team
import com.example.sportevent.data.model.TeamItem
import com.example.sportevent.ui.repositories.TeamRepository
import com.example.sportevent.ui.viewmodels.TeamViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TeamViewModelTest {

    // Rule to force LiveData to be handled on the same thread
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockTeamRepository: TeamRepository

    @Mock
    private lateinit var mockObserver: Observer<Team>

    @InjectMocks
    private lateinit var teamViewModel: TeamViewModel

    @Captor
    private lateinit var teamListCaptor: ArgumentCaptor<Team>

    @Before
    fun setUp() {
        // Initialize ViewModel and inject the mock repository
        teamViewModel = TeamViewModel(mockTeamRepository)
        // Observe the LiveData in the ViewModel
        teamViewModel.teams.observeForever(mockObserver)
        MockitoAnnotations.initMocks(TeamViewModel::javaClass)
    }

    @Test
    fun testLoadTeamsSuccess() {
        // Given
        teamListCaptor = ArgumentCaptor.forClass(Team::class.java) as ArgumentCaptor<Team>
        val mockCallback: (Team) -> Unit = mock()
        val mockTeamsList = ArrayList<TeamItem>()
        mockTeamsList.add(TeamItem("1", "Team Red Dragon", "dragon"))

        `when`(mockTeamRepository.getAllTeams(mockCallback)).thenAnswer {
            val callback = it.arguments[0] as (Team) -> Unit
            callback.invoke(Team(mockTeamsList))
        }

        // When
        teamViewModel.loadTeams()

        // Then
        verify(mockObserver).onChanged(teamListCaptor.capture())

        assertEquals(mockTeamsList, teamListCaptor.value)
        // You can also verify other aspects of the ViewModel's behavior based on your requirements
    }

    @Test
    fun testLoadTeamsFailure() {
        // Given
        val mockTeamsList = ArrayList<TeamItem>()
        val mockCallback: (Team) -> Unit = mock()
        mockTeamsList.add(TeamItem("1", "Team Red Dragon", "dragon"))
        `when`(mockTeamRepository.getAllTeams(mockCallback)).thenAnswer {
            val callback = it.arguments[0] as (Team) -> Unit
            callback.invoke(Team(mockTeamsList))
            // Simulate an error by not invoking the callback
        }

        // When
        teamViewModel.loadTeams()

        // Then
        verifyZeroInteractions(mockObserver)
        // You can also verify other aspects of the ViewModel's behavior based on your requirements
    }
}