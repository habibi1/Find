package com.habibi.find.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.habibi.core.data.source.IUsersDataSource

class DetailViewModel(private val iUsersDataSource: IUsersDataSource) : ViewModel() {

    private val loadDetailTrigger = MutableLiveData(Unit)
    private val loadRepositoryTrigger = MutableLiveData(Unit)

    fun refreshDetail(){
        loadDetailTrigger.value = Unit
    }

    fun refreshRepository(){
        loadRepositoryTrigger.value = Unit
    }

    fun getDetailUser(login: String)= loadDetailTrigger.switchMap {
        iUsersDataSource.getDetailUser(login).asLiveData()
    }

    fun getUserRepository(login: String)= loadRepositoryTrigger.switchMap {
        iUsersDataSource.getUserRepository(login).asLiveData()
    }
}