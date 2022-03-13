package com.x.domain.usecase

import com.x.common.resource.Resource
import com.x.domain.model.JobBuildDetailModel
import com.x.domain.model.JobModel
import com.x.domain.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface JobUseCase {

    fun getJob(jobUrl: String): Flow<Resource<JobModel>>

    fun build(jobUrl: String): Flow<Resource<ResponseModel>>

    fun buildWithParameters(jobUrl: String, fieldMap: Map<String, String>): Flow<Resource<ResponseModel>>

    fun getBuild(jobUrl: String, buildId: String): Flow<Resource<JobBuildDetailModel>>
}