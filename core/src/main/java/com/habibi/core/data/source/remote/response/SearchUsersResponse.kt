package com.habibi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<UsersItem?>? = null
)

data class UsersItem(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null

)
