package com.code4galaxy.graphqlspacex.data.mapper

import com.code4galaxy.graphqlspacex.data.dto.AddUserResponse
import com.codegalaxy.graphqldemo.model.Insert_usersMutation

fun mapToAddUserResponse(addUserResponse: Insert_usersMutation.Returning?): AddUserResponse? {
    return if (addUserResponse != null) {
        AddUserResponse(
            id = addUserResponse.id.toString(),
            name = addUserResponse.name.orEmpty()
        )
    } else{
        null
    }
}