package com.code4galaxy.graphqlspacex.di

import com.code4galaxy.graphqlspacex.data.graphql.adapters.UuidAdapter
import com.code4galaxy.graphqlspacex.data.repository.AddNewUserRepositoryImpl
import com.code4galaxy.graphqlspacex.data.repository.SpaceRepositoryImpl
import com.code4galaxy.graphqlspacex.domain.usecase.AddNewUserUseCase
import com.code4galaxy.graphqlspacex.domain.usecase.GetSpaceXListUseCase
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.codegalaxy.graphqldemo.model.type.Uuid
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Dependency injection module for providing Apollo Client and OkHttpClient
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        // Create and configure Apollo Client
        return ApolloClient.Builder()
            .serverUrl("https://spacex-production.up.railway.app/") // GraphQL endpoint
            .okHttpClient(okHttpClient) // Configure with OkHttpClient
            .addCustomScalarAdapter(Uuid.type, UuidAdapter)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        // Create and configure OkHttpClient with logging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add logging interceptor
            .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
            .readTimeout(30, TimeUnit.SECONDS) // Set read timeout
            .build()
    }

    @Singleton
    @Provides
    fun provideSpaceXListUseCase(repository: SpaceRepositoryImpl): GetSpaceXListUseCase {
        return GetSpaceXListUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideAddNewUserUseCase(repository: AddNewUserRepositoryImpl): AddNewUserUseCase {
        return AddNewUserUseCase(repository)
    }
}