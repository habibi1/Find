package com.habibi.core.data.source.remote

import com.habibi.core.data.source.remote.network.ApiResponse
import com.habibi.core.data.source.remote.network.ApiService
import com.habibi.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteDataSourceTest {

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var remoteDataSource: RemoteDataSource

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.IO)
        remoteDataSource = RemoteDataSource(apiService)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search users - given empty data then return ApiResponse_Empty`(): Unit = runBlocking {
        val emptyData = dataDummySearchUsersEmpty
        val query = "query"

        `when`(apiService.getSearchUsers(query)).thenReturn(emptyData)

        val call = remoteDataSource.getSearchUsers(query).first()
        assertNotNull(call)
        assertEquals(call, ApiResponse.Empty(null))
    }

    @Test
    fun `search users - given not empty data then return ApiResponse_Success`(): Unit = runBlocking {
        val notEmptyData = dataDummySearchUsersNotEmpty
        val query = "query"

        `when`(apiService.getSearchUsers(query)).thenReturn(notEmptyData)

        val call = remoteDataSource.getSearchUsers(query).first()
        assertNotNull(call)
        assertEquals(call, ApiResponse.Success(notEmptyData.items))
    }

    @Test
    fun `search users - given null data then return ApiResponse_Error`(): Unit = runBlocking {
        val query = "query"

        `when`(apiService.getSearchUsers(query)).thenReturn(null)

        val call = remoteDataSource.getSearchUsers(query).first()
        assertNotNull(call)
        assertTrue(call is ApiResponse.Error)
    }

    @Test
    fun `detail user - given not empty data then return ApiResponse_Success`(): Unit = runBlocking {
        val detailUser = dataDummyDetailUserResponse
        val login = "login"

        `when`(apiService.getDetailUser(login)).thenReturn(detailUser)

        val call = remoteDataSource.getDetailUser(login).first()
        assertNotNull(call)
        assertEquals(call, ApiResponse.Success(detailUser))
    }

    @Test
    fun `detail user - given null data then return ApiResponse_Error`(): Unit = runBlocking {
        val login = "login"

        `when`(apiService.getDetailUser(login)).thenReturn(null)

        val call = remoteDataSource.getDetailUser(login).first()
        assertNotNull(call)
        assertTrue(call is ApiResponse.Error)
    }

    @Test
    fun `repository user - given not empty data then return ApiResponse_Success`(): Unit = runBlocking {
        val dataRepository = listOf(dataDummyUserRepository)
        val login = "login"

        `when`(apiService.getUserRepository(login)).thenReturn(dataRepository)

        val call = remoteDataSource.getUserRepository(login).first()
        assertNotNull(call)
        assertEquals(call,  ApiResponse.Success(dataRepository))
    }

    @Test
    fun `repository user - given empty data then return ApiResponse_Empty`(): Unit = runBlocking {
        val dataRepository = ArrayList<UserRepositoryResponseItem>()
        val login = "login"

        `when`(apiService.getUserRepository(login)).thenReturn(dataRepository)

        val call = remoteDataSource.getUserRepository(login).first()
        assertNotNull(call)
        assertEquals(call,  ApiResponse.Empty(null))
    }

    @Test
    fun `repository user - given null data then return ApiResponse_Error`(): Unit = runBlocking {
        val login = "login"

        `when`(apiService.getUserRepository(login)).thenReturn(null)

        val call = remoteDataSource.getUserRepository(login).first()
        assertNotNull(call)
        assertTrue(call is  ApiResponse.Error)
    }

    private val dataDummyDetailUserResponse = DetailUserResponse(
        "bio",
        "login",
        "type",
        "email",
        1,
        "avatar",
        2,
        "location"
    )

    private val dataDummyUsersItem1 = UsersItem(
        "login1",
        "type1",
        "avatar1"
    )

    private val dataDummyUsersItem2 = UsersItem(
        "login2",
        "type2",
        "avatar2"
    )

    private val dataDummySearchUsersNotEmpty = SearchUsersResponse(
        1,
        false,
        listOf(dataDummyUsersItem1, dataDummyUsersItem2)
    )

    private val dataDummySearchUsersEmpty = SearchUsersResponse(
        0,
        false,
        listOf()
    )

    private val dataDummyOwner = Owner(
        "avatar"
    )

    private val dataDummyUserRepository = UserRepositoryResponseItem(
        2,
        "name",
        "desc",
        dataDummyOwner
    )
}