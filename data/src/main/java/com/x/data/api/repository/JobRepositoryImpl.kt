package com.x.data.api.repository

import com.x.data.api.model.response.toModel
import com.x.data.api.model.response.toResponseString
import com.x.data.api.service.JobService
import com.x.domain.model.JobBuildDetailModel
import com.x.domain.model.JobBuildModel
import com.x.domain.model.JobModel
import com.x.domain.model.ResponseModel
import com.x.domain.repository.api.JobRepository
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val jobService: JobService,
) : JobRepository {

    override suspend fun get(jobPath: String): JobModel {
        return jobService.getJobs(path = jobPath).toModel()
    }

    override suspend fun build(jobPath: String): ResponseModel {
        return jobService.build(path = jobPath).toModel()
    }

    override suspend fun build(jobPath: String, fieldMap: Map<String, String>): ResponseModel {
        return jobService.build(path = jobPath, fieldMap = fieldMap).toModel()
    }

    override suspend fun getBuilds(): JobBuildModel {
        return jobService.getBuilds().toModel()
    }

    override suspend fun getBuild(jobPath: String, buildId: String): JobBuildDetailModel {
        return jobService.getBuild(path = jobPath, buildId = buildId).toModel()
    }

    override suspend fun getConsoleLog(jobPath: String, buildId: String): String {
        return jobService.getConsoleLog(path = jobPath, buildId = buildId).toResponseString()
    }
}