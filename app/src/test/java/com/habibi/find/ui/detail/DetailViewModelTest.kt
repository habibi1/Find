package com.habibi.find.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.habibi.core.data.source.IUsersDataSource
import com.habibi.core.data.source.Resource
import com.habibi.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observerDetailUser: Observer<Resource<DetailUserResponse>>

    @Mock
    private lateinit var observerListRepository: Observer<Resource<List<UserRepositoryResponseItem?>>>

    @Mock
    private lateinit var userDataSource: IUsersDataSource

    private lateinit var viewModel: DetailViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = DetailViewModel(userDataSource)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get detail user is loading`() {
        val login = "login"

        `when`(userDataSource.getDetailUser(login)).thenReturn(
            flowOf(Resource.Loading())
        )

        val call = viewModel.getDetailUser(login)
        call.observeForever(observerDetailUser)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Loading)
    }

    @Test
    fun `get detail user is success`() {
        val data = dataDummyDetailUserResponse
        val login = "login"

        `when`(userDataSource.getDetailUser(login)).thenReturn(
            flowOf(Resource.Success(data))
        )

        val call = viewModel.getDetailUser(login)
        call.observeForever(observerDetailUser)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(result?.data, data)
    }

    @Test
    fun `get detail user is error`() {
        val login = "login"
        val message = "message"

        `when`(userDataSource.getDetailUser(login)).thenReturn(
            flowOf(Resource.Error(message))
        )

        val call = viewModel.getDetailUser(login)
        call.observeForever(observerDetailUser)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals(result?.message, message)
    }

    @Test
    fun `get user repository is loading`() {
        val login = "login"

        `when`(userDataSource.getUserRepository(login)).thenReturn(
            flowOf(Resource.Loading())
        )

        val call = viewModel.getUserRepository(login)
        call.observeForever(observerListRepository)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Loading)
    }

    @Test
    fun `get user repository is success`() {
        val data = listOf(dataDummyUserRepository)
        val login = "login"

        `when`(userDataSource.getUserRepository(login)).thenReturn(
            flowOf(Resource.Success(data))
        )

        val call = viewModel.getUserRepository(login)
        call.observeForever(observerListRepository)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(result?.data, data)
    }

    @Test
    fun `get user repository is empty`() {
        val login = "login"

        `when`(userDataSource.getUserRepository(login)).thenReturn(
            flowOf(Resource.Empty())
        )

        val call = viewModel.getUserRepository(login)
        call.observeForever(observerListRepository)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Empty)
    }

    @Test
    fun `get user repository is error`() {
        val login = "login"
        val message = "message"

        `when`(userDataSource.getUserRepository(login)).thenReturn(
            flowOf(Resource.Error(message))
        )

        val call = viewModel.getUserRepository(login)
        call.observeForever(observerListRepository)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals(result?.message, message)
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