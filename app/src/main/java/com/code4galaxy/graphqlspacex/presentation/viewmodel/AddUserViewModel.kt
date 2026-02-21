package com.code4galaxy.graphqlspacex.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code4galaxy.graphqlspacex.domain.usecase.AddNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

// ViewModel for managing UI-related data and fetching satellite data
@HiltViewModel
class AddUserViewModel @Inject constructor(private val useCase: AddNewUserUseCase) : ViewModel() {
    // LiveData to observe GraphQL response
    private val _graphQLResponseModel = MutableStateFlow<Int?>(null)
    val graphQLResponseModel: StateFlow<Int?> =
        _graphQLResponseModel.asStateFlow()

    // Function to Add new user
    fun addNewUser(name: String) = viewModelScope.launch {
        try {
            // Add User and receive response
            useCase(UUID.randomUUID(), name).collect { result ->
                _graphQLResponseModel.value = result
            }
        } catch (exception: Exception) {
            // Handle exceptions and log error
            Log.e("ERROR", exception.message.toString())
        }
    }
    fun clearResponse() {
        _graphQLResponseModel.value = null
    }
}