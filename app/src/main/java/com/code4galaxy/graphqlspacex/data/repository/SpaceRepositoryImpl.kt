package com.code4galaxy.graphqlspacex.data.repository

import com.code4galaxy.graphqlspacex.data.dto.SatelliteData
import com.code4galaxy.graphqlspacex.data.mapper.mapToSatelliteData
import com.code4galaxy.graphqlspacex.domain.repository.ISpaceXRepository
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.codegalaxy.graphqldemo.model.FetchSpaceXListQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SpaceRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ISpaceXRepository {
    override fun getSpaceXListFromApi(): Flow<List<SatelliteData?>> = flow {
        val launches = apolloClient.query(FetchSpaceXListQuery(Optional.present(10))).execute().data?.launchesPast
        val mappedLaunches = launches?.map { launch -> mapToSatelliteData(launch) }.orEmpty()
        emit(mappedLaunches)
    }.flowOn(Dispatchers.IO)
}