package com.habibi.core.data.source.local

import com.habibi.core.data.source.local.entity.UsersEntity
import com.habibi.core.data.source.local.room.FindDao
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalDataSourceTest {

    @Mock
    private lateinit var findDao: FindDao

    private lateinit var localDataSource: LocalDataSource

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.IO)
        localDataSource = LocalDataSource(findDao)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search user - call method then return not empty list`()= runBlocking {
        val data = listOf(dataDummyUserEntity)

        `when`(findDao.getSearchUsers()).thenReturn(flowOf(data))

        val call = localDataSource.getSearchUsers().first()
        assertNotNull(call)
        assertEquals(call, data)
    }

    @Test
    fun `insert search users`()= runBlocking {
        val data = listOf(dataDummyUserEntity)

        `when`(findDao.insertSearchUsers(data)).thenReturn(Unit)

        localDataSource.insertSearchUsers(data)
        verify(findDao).insertSearchUsers(data)
    }

    @Test
    fun deleteSearchUsers()= runBlocking {
        `when`(findDao.deleteSearchUsers()).thenReturn(Unit)

        localDataSource.deleteSearchUsers()
        verify(findDao).deleteSearchUsers()
    }

    private val dataDummyUserEntity = UsersEntity(
        "login",
        "type",
        "avatar"
    )
}