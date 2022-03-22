# Find
**Find** is a user finder app on github. The app can show a list of users and can show details and a list of repositories stored on github. Each search will be saved in local storage, so it can be used when offline.
> **Note:** Application can be used with **minimum API level 21 or higher**

## Feature

- Search for users using keywords
- Display user details
- Displays a list of public repositories on github

## Resource API

[Github API](https://docs.github.com/en/rest)
 - [Search users](https://docs.github.com/en/rest/reference/search#search-users)
 - [Get a user](https://docs.github.com/en/rest/reference/users#get-a-user)
 - [List repository for a user](https://docs.github.com/en/rest/reference/repos#list-repositories-for-a-user)
		
## Architecture

[Android Architecture Components](https://developer.android.com/topic/libraries/architecture)

## Libraries

| Library                          	                                                              | Function							                                        |
|-------------------------------------------------------------------------------------------------|---------------------------------------------------------------|
| [Retrofit](https://square.github.io/retrofit/) and [OkHttp](https://square.github.io/okhttp/)   |Fetch data from network			                                  |
| [Koin](https://insert-koin.io/)            	                                                    | Service Locator					                                      |
| [Glide](https://github.com/bumptech/glide)	                                                    | Image Loading		                                              |
| [Room](https://developer.android.com/jetpack/androidx/releases/room)                            | Abstraction SQLite for local database                         |
| [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation)                | Navigation and passing data                                   |
| [Espresso](https://developer.android.com/training/testing/espresso)                             | Instrumentation testing                                       |
| [Mockito](https://github.com/mockito/mockito)                                                   | Unit testing                                                  |
| [Coroutine](https://developer.android.com/kotlin/coroutines)                                    | Asynchronous                                                  |
| [CircleImageView](https://github.com/hdodenhof/CircleImageView)                                 | Circle Image                                                  |
| [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)                 | Observable class holder                                       |
| [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)               | Store and manage UI-related data in a lifecycle conscious way |
| [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)               | store key-value pairs                                         |
