package com.bahadir.core.domain.usecase

import com.bahadir.core.domain.repository.Repository
import javax.inject.Inject

class GetServiceUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getServiceStatus()
}