package com.x.domain.repository.api

import com.x.domain.model.JobViewListModel
import com.x.domain.model.ViewModel

interface ViewRepository {

    suspend fun getAllView(): ViewModel

    suspend fun getViewJobs(name: String): JobViewListModel
}