package com.habibi.core.di

import androidx.room.Room
import com.habibi.core.BuildConfig
import com.habibi.core.data.source.IUsersDataSource
import com.habibi.core.data.source.UsersRepository
import com.habibi.core.data.source.datastore.FindPreference
import com.habibi.core.data.source.local.LocalDataSource
import com.habibi.core.data.source.local.room.FindDatabase
import com.habibi.core.data.source.remote.RemoteDataSource
import com.habibi.core.data.source.remote.network.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<FindDatabase>().findDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FindDatabase::class.java, "Find.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single {
        FindPreference(
            androidContext()
        )
    }
    single<IUsersDataSource> { UsersRepository(get(), get(), get()) }
}