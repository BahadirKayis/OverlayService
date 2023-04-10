package com.bahadir.services.domain.usecase

import com.bahadir.services.common.Resource
import com.bahadir.services.domain.model.UsageStateUI
import com.bahadir.services.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsageStateUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<List<UsageStateUI>>> =
         repository.getUsageStatesTime()

}
