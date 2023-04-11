package com.bahadir.overlayservice.domain.usecase

import com.bahadir.overlayservice.common.Resource
import com.bahadir.overlayservice.domain.model.UsageStateUI
import com.bahadir.overlayservice.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsageStateUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<List<UsageStateUI>>> =
         repository.getUsageStatesTime()

}
