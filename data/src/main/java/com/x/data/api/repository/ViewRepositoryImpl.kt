package com.x.data.api.repository

import com.x.data.api.model.response.toModel
import com.x.data.api.service.ViewService
import com.x.domain.model.JobViewListModel
import com.x.domain.model.ViewModel
import com.x.domain.repository.api.ViewRepository
import javax.inject.Inject

class ViewRepositoryImpl @Inject constructor(
    private val viewService: ViewService,
) : ViewRepository {

    override suspend fun getAllView(): ViewModel {
        return viewService.getViews().toModel()
    }

    override suspend fun getViewJobs(name: String): JobViewListModel {
        return viewService.getJobs(name).toModel()
    }
}