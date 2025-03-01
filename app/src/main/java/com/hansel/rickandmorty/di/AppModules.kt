package com.hansel.rickandmorty.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hansel.rickandmorty.data.datasource.LocalDataSourceImpl
import com.hansel.rickandmorty.data.datasource.RemoteDataSourceImpl
import com.hansel.rickandmorty.data.local.CharacterDao
import com.hansel.rickandmorty.data.local.CharacterDatabase
import com.hansel.rickandmorty.data.remote.CharacterMediator
import com.hansel.rickandmorty.data.remote.RickAndMortyApi
import com.hansel.rickandmorty.data.repository.CharacterRepositoryImpl
import com.hansel.rickandmorty.domain.datasource.LocalDataSource
import com.hansel.rickandmorty.domain.datasource.RemoteDataSource
import com.hansel.rickandmorty.domain.repository.CharacterRepository
import com.hansel.rickandmorty.presentation.CharacterViewModel
import com.hansel.rickandmorty.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
val appModules = module {
    single {
        OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .callTimeout(35, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .baseUrl(BASE_URL)
            .build()
    }

    single {
        get<Retrofit>().create(RickAndMortyApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            CharacterDatabase::class.java,
            "character-database"
        ).build()
    }

    single {
        get<CharacterDatabase>().getCharacterDao()
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(get())
    }

    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }

    single<CharacterRepository> {
        CharacterRepositoryImpl(get(), get(), get())
    }

    viewModel { CharacterViewModel(get()) }

    single {
        Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterMediator(get(), get()),
            pagingSourceFactory = { get<CharacterDao>().pagingSource() }
        )
    }
}