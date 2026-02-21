package com.code4galaxy.graphqlspacex.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.graphqlspacex.data.dto.SatelliteData
import com.code4galaxy.graphqlspacex.domain.usecase.GetSpaceXListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel for managing UI-related data and fetching satellite data
@HiltViewModel
class SpaceXViewModel @Inject constructor(private val useCase: GetSpaceXListUseCase) : ViewModel() {
    // LiveData to observe GraphQL response
    private val _graphQLResponseModel = MutableStateFlow<List<SatelliteData?>?>(null)
    val graphQLResponseModel: StateFlow<List<SatelliteData?>?> =
        _graphQLResponseModel.asStateFlow()

    init {
        fetchSpaceXData() // Fetch satellite data on initialization
    }

    // Function to fetch satellite data from the repository
    private fun fetchSpaceXData() = viewModelScope.launch {
        try {
            // Get satellites data and post value to LiveData
            useCase().collect { result ->
                _graphQLResponseModel.value = result
            }
        } catch (exception: Exception) {
            // Handle exceptions and log error
            Log.e("ERROR", exception.message.toString())
        }
    }
}