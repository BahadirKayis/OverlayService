package com.bahadir.core.domain.usecase

import com.bahadir.core.domain.repository.Repository
import javax.inject.Inject

class SetServiceUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(status: Boolean) = repo.setServiceStatus(status)
}