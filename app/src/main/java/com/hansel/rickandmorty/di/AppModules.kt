package com.hansel.rickandmorty.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hansel.rickandmorty.data.datasource.RemoteDataSourceImpl
import com.hansel.rickandmorty.data.remote.RickAndMortyApi
import com.hansel.rickandmorty.domain.datasource.RemoteDataSource
import com.hansel.rickandmorty.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpModule = module {
    single {
        OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .callTimeout(35, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}

val apiServiceModule = module {
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
}

val dataSourceModule = module {
    single<RemoteDataSource> {
        RemoteDataSourceImpl(get())
    }
}