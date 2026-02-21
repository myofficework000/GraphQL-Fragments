package com.code4galaxy.graphqlspacex.domain.usecase

import com.code4galaxy.graphqlspacex.domain.repository.ISpaceXRepository

class GetSpaceXListUseCase(
    private val countryRepo: ISpaceXRepository
) {
    operator fun invoke() = countryRepo.getSpaceXListFromApi()
}