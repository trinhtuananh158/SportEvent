import com.example.sportevent.data.model.Team
import com.example.sportevent.data.model.TeamItem
import com.example.sportevent.data.remote.ApiService
import com.example.sportevent.ui.repositories.TeamRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class TeamRepositoryTest {

    @Mock
    private lateinit var mockApiService: ApiService

    @Mock
    private lateinit var mockCall: Call<Team>

    @Test
    fun testGetAllTeamsSuccess() {
        // Given
        var mockData = ArrayList<TeamItem>()
        mockData.add(TeamItem("1", "Team Red Dragon", "dragon"))
        val teamRepository = TeamRepository(mockApiService)
        val callback: (Team) -> Unit = mock()

        `when`(mockApiService.getTeams()).thenReturn(mockCall)
        `when`(mockCall.enqueue(any())).thenAnswer {
            val callback = it.arguments[0] as Callback<Team>

            callback.onResponse(mockCall, Response.success(Team(mockData)))
        }

        // When
        teamRepository.getAllTeams(callback)

        // Then
        verify(callback).invoke(Team(mockData))
    }

    @Test
    fun testGetAllTeamsFailure() {
        // Given
        var mockData = ArrayList<TeamItem>()
        mockData.add(TeamItem("1", "Team Red Dragon", "dragon"))
        val teamRepository = TeamRepository(mockApiService)
        val callback: (Team) -> Unit = mock()

        `when`(mockApiService.getTeams()).thenReturn(mockCall)
        `when`(mockCall.enqueue(any())).thenAnswer {
            val callback = it.arguments[0] as Callback<Team>
            callback.onFailure(mockCall, RuntimeException("Error fetching teams"))
        }

        // When
        teamRepository.getAllTeams(callback)

        // Then
        verify(callback, never()).invoke(Team(mockData))
    }
}