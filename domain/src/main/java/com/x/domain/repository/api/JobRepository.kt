package com.x.domain.repository.api

import com.x.domain.model.*

interface JobRepository {

    suspend fun get(jobPath: String): JobModel

    suspend fun build(jobPath: String): ResponseModel

    suspend fun build(jobPath: String, fieldMap: Map<String, String>): ResponseModel

    suspend fun getBuilds(): JobBuildModel

    suspend fun getBuild(jobPath: String, buildId: String): JobBuildDetailModel

    suspend fun getConsoleLog(jobPath: String, buildId: String): String
}