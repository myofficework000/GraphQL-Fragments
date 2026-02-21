package com.code4galaxy.graphqlspacex.domain.usecase

import com.code4galaxy.graphqlspacex.domain.repository.IAddNewUserRepository
import java.util.UUID

class AddNewUserUseCase(
    private val addNewUserRepo: IAddNewUserRepository
) {
    operator fun invoke(uuid: UUID, name: String) = addNewUserRepo.addNewUser(uuid, name)
}