package com.code4galaxy.graphqlspacex.domain.repository

import com.code4galaxy.graphqlspacex.data.dto.SatelliteData
import kotlinx.coroutines.flow.Flow

interface ISpaceXRepository {
    fun getSpaceXListFromApi(): Flow<List<SatelliteData?>>
}