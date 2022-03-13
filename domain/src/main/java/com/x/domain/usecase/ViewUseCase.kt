package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.JobViewListModel
import com.x.domain.model.ViewModel
import kotlinx.coroutines.flow.Flow

interface ViewUseCase {

    fun getAllView(): Flow<Resource<ViewModel>>

    fun getViewJobs(viewName: String): Flow<Resource<JobViewListModel>>
}