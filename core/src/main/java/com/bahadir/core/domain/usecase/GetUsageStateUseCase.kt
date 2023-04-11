package com.bahadir.core.domain.usecase

import com.bahadir.core.common.Resource
import com.bahadir.core.domain.model.UsageStateUI
import com.bahadir.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsageStateUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<List<UsageStateUI>>> =
        repository.getUsageStatesTime()

}
