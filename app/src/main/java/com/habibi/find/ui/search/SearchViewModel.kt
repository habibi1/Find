package com.habibi.find.ui.search

import androidx.lifecycle.*
import com.habibi.core.data.source.IUsersDataSource
import kotlinx.coroutines.launch

class SearchViewModel(private val iUsersDataSource: IUsersDataSource) : ViewModel() {

    private val _firstTimeLoad = MutableLiveData<Boolean>()
    val firstTimeLoad get() = _firstTimeLoad

    private val _name = MutableLiveData<String>()
    val name get() = _name

    private val loadTrigger = MutableLiveData(Unit)

    fun setFirstTimeLoad(isFirstTime: Boolean){
        _firstTimeLoad.value = isFirstTime
    }

    fun setName(name: String){
        _name.value = name
    }

    fun getKeywordSearch() = iUsersDataSource.getKeywordSearch().asLiveData()

    fun saveKeywordSearch(keyword: String) = viewModelScope.launch {
        iUsersDataSource.saveKeywordSearch(keyword)
    }

    fun refreshLoad(){
        loadTrigger.value = Unit
    }

    fun getSearchUsers(query: String) = loadTrigger.switchMap {
        iUsersDataSource.getSearchUsers(
            query,
            queryIsNotSame(query)
        ).asLiveData()
    }

    private fun queryIsNotSame(query: String): Boolean{
        return query != name.value
    }
}