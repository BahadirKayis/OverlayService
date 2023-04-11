package com.bahadir.overlayservice.domain.usecase

import com.bahadir.overlayservice.domain.repository.Repository
import javax.inject.Inject

class SetServiceUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(status: Boolean) = repo.setServiceStatus(status)
}