package com.habibi.find.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.habibi.core.data.source.IUsersDataSource
import kotlinx.coroutines.launch

class SearchViewModel(private val iUsersDataSource: IUsersDataSource) : ViewModel() {

    private val _firstTimeLoad = MutableLiveData<Boolean>()
    val firstTimeLoad get() = _firstTimeLoad

    private val _name = MutableLiveData<String>()
    val name get() = _name

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

    fun getSearchUsers(query: String) =
        iUsersDataSource.getSearchUsers(
            query,
            queryIsSame(query)
        ).asLiveData()

    private fun queryIsSame(query: String): Boolean{
        return query != name.value
    }
}