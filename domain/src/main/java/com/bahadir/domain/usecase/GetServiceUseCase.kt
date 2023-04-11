package com.bahadir.overlayservice.domain.usecase

import com.bahadir.overlayservice.domain.repository.Repository
import javax.inject.Inject

class GetServiceUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke() =repository.getServiceStatus()
}