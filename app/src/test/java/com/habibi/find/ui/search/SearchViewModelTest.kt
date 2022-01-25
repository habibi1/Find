package com.habibi.find.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.habibi.core.data.source.IUsersDataSource
import com.habibi.core.data.source.Resource
import com.habibi.core.data.source.local.entity.UsersEntity
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observerWithResource: Observer<Resource<List<UsersEntity>>>

    @Mock
    private lateinit var observerWithString: Observer<String>

    @Mock
    private lateinit var userDataSource: IUsersDataSource

    private lateinit var viewModel: SearchViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = SearchViewModel(userDataSource)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get first time load is true`() {
        viewModel.setFirstTimeLoad(true)
        assertTrue(viewModel.firstTimeLoad.value!!)
    }

    @Test
    fun `get first time load is false`() {
        viewModel.setFirstTimeLoad(false)
        assertFalse(viewModel.firstTimeLoad.value!!)
    }

    @Test
    fun `get name`() {
        val name = "name"
        viewModel.setName(name)
        assertEquals(viewModel.name.value, name)
    }

    @Test
    fun `get keyword search`() {
        val keyword = "keyword"

        `when`(userDataSource.getKeywordSearch()).thenReturn(flowOf(keyword))

        val call = viewModel.getKeywordSearch()
        call.observeForever(observerWithString)
        val result = call.value
        assertNotNull(result)
        assertEquals(result, keyword)
    }

    @Test
    fun `save keyword search`() = runBlocking {
        val keyword = "keyword"

        `when`(userDataSource.saveKeywordSearch(keyword)).thenReturn(null)

        viewModel.saveKeywordSearch(keyword)
        verify(userDataSource).saveKeywordSearch(keyword)
    }

    @Test
    fun `get search users is not empty`() {
        val data = listOf(dataDummyUserEntity)
        val query = "query"
        val queryIsSame = true

        `when`(userDataSource.getSearchUsers(query, queryIsSame)).thenReturn(
            flowOf(
                Resource.Success(data)
            )
        )

        val call = viewModel.getSearchUsers(query)
        call.observeForever(observerWithResource)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Success)
        assertEquals(result?.data, data)
    }

    @Test
    fun `get search users is empty`() {
        val query = "query"
        val queryIsSame = true

        `when`(userDataSource.getSearchUsers(query, queryIsSame)).thenReturn(
            flowOf(
                Resource.Empty(null)
            )
        )

        val call = viewModel.getSearchUsers(query)
        call.observeForever(observerWithResource)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Empty)
        assertEquals(result?.data, null)
    }

    @Test
    fun `get search users is error`() {
        val query = "query"
        val message = "massage"
        val queryIsSame = true

        `when`(userDataSource.getSearchUsers(query, queryIsSame)).thenReturn(
            flowOf(
                Resource.Error(message)
            )
        )

        val call = viewModel.getSearchUsers(query)
        call.observeForever(observerWithResource)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Error)
        assertEquals(result?.message, message)
    }

    @Test
    fun `get search users is loading`() {
        val query = "query"
        val queryIsSame = true

        `when`(userDataSource.getSearchUsers(query, queryIsSame)).thenReturn(
            flowOf(
                Resource.Loading()
            )
        )

        val call = viewModel.getSearchUsers(query)
        call.observeForever(observerWithResource)
        val result = call.value
        assertNotNull(result)
        assertTrue(result is Resource.Loading)
    }

    private val dataDummyUserEntity = UsersEntity(
        "login",
        "type",
        "avatar"
    )
}