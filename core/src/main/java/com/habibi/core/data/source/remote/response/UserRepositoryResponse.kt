package com.habibi.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserRepositoryResponseItem(

	@field:SerializedName("stargazers_count")
	val stargazersCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("owner")
	val owner: Owner? = null
)

data class Owner(

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

)
