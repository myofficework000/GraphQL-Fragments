package com.code4galaxy.graphqlspacex.data.repository

import com.code4galaxy.graphqlspacex.domain.repository.IAddNewUserRepository
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.network.http.HttpInfo
import com.codegalaxy.graphqldemo.model.Insert_usersMutation
import com.codegalaxy.graphqldemo.model.type.Users_insert_input
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

//if response is 200, then assume user is added.
class AddNewUserRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : IAddNewUserRepository {
    override fun addNewUser(uuid: UUID, name: String): Flow<Int?> = flow {
        val launches = apolloClient.mutation(
            Insert_usersMutation(
                listOf(
                    Users_insert_input(id = Optional.present(uuid), name = Optional.present(name))
                )
            )
        ).execute().executionContext[HttpInfo]?.statusCode
        emit(launches)
    }.flowOn(Dispatchers.IO)
}


//class AddNewUserRepositoryImpl @Inject constructor(
//    private val apolloClient: ApolloClient
//) : IAddNewUserRepository {
//    override fun addNewUser(uuid: UUID, name: String): Flow<List<AddUserResponse?>> = flow {
//        val launches = apolloClient.mutation(
//            Insert_usersMutation(
//                listOf(
//                    Users_insert_input(id = Optional.present(uuid), name = Optional.present(name))
//                )
//            )
//        ).execute().data?.insert_users?.returning
//        val mappedLaunches = launches?.map { launch -> mapToAddUserResponse(launch) }.orEmpty()
//        emit(mappedLaunches)
//    }.flowOn(Dispatchers.IO)
//}