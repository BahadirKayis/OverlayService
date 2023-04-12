package com.bahadir.core.domain.usecase

import com.bahadir.core.domain.repository.Repository
import javax.inject.Inject

class SetStartTimeUseCase @Inject constructor(private val repo: Repository) {
    suspend operator fun invoke(time: Long) = repo.setServiceStartTime(time)
}