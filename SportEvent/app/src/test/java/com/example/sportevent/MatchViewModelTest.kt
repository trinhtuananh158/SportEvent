package com.example.sportevent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sportevent.data.model.MatchItem
import com.example.sportevent.data.model.MatchModel
import com.example.sportevent.data.model.Matches
import com.example.sportevent.ui.repositories.MatchRepository
import com.example.sportevent.ui.viewmodels.MatchViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MatchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockMatchRepository: MatchRepository

    @Mock
    private lateinit var mockObserver: Observer<Matches>

    @InjectMocks
    private lateinit var matchViewModel: MatchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        matchViewModel.matches.observeForever(mockObserver)
    }

    @Test
    fun testLoadMatches() {
        // Given
        val mockCallback: (Matches) -> Unit = mock()
        val matchList = ArrayList<MatchItem>()
        matchList.add(MatchItem("", "Team RedDragon", "", "", "", "", false))
        val mockMatches = Matches(MatchModel(matchList, matchList))

        `when`(mockMatchRepository.getMatches(mockCallback)).thenAnswer {
            val callback = it.arguments[0] as (Matches) -> Unit
            callback.invoke(mockMatches)
        }

        // When
        matchViewModel.loadMatches()

        verify(mockMatchRepository).getMatches(mockCallback)

        // Then
        assertLiveDataEventTriggered(mockMatches)
    }

    @Test
    fun testLoadMatchesByTeam() {
        // Given
        val teamId = "767ec50c-7fdb-4c3d-98f9-d6727ef8252b"
        val mockCallback: (Matches) -> Unit = mock()
        val matchList = ArrayList<MatchItem>()
        matchList.add(MatchItem("", "Team RedDragon", "", "", "", "", false))
        val mockMatches = Matches(MatchModel(matchList, matchList))

        // When
        matchViewModel.loadMatchesByTeam(teamId)

        // Then
        verify(mockMatchRepository).getMatchesByTeam(teamId, mockCallback)
        assertLiveDataEventTriggered(mockMatches)
    }

    private fun assertLiveDataEventTriggered(expectedValue: Matches) {
        val captor = argumentCaptor<Matches>()
        captor.run {
            verify(mockObserver).onChanged(capture())
            val capturedValue = captor.firstValue
            assertEquals(expectedValue, capturedValue)
        }
    }
}