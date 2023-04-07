package com.bahadir.services.domain.usecase

import com.bahadir.services.domain.repository.Repository
import javax.inject.Inject

class GetServiceUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() =repository.getServiceStatus()
}