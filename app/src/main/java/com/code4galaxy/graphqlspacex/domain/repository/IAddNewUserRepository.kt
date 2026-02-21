package com.code4galaxy.graphqlspacex.domain.repository

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface IAddNewUserRepository {
    fun addNewUser(uuid: UUID, name: String): Flow<Int?>
}
//List<AddUserResponse?>