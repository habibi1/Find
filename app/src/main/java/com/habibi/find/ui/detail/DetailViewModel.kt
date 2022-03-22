package com.habibi.find.ui.detail

import androidx.lifecycle.*
import com.habibi.core.data.source.IUsersDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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

    fun getDetailAndRepository(login: String) =
        viewModelScope.launch(Dispatchers.IO) {
            iUsersDataSource.getDetailUser(login)
        }
}