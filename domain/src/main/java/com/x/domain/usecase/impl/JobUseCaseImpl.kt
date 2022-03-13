package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.repository.api.JobRepository
import com.x.domain.usecase.JobUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class JobUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val jobRepository: JobRepository,
) : JobUseCase {

    override fun getJob(jobUrl: String) = flow {
        try {
            emit(Resource.Loading(true))
            val jobModel = jobRepository.get(jobPath = jobUrl)
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = jobModel
                )
            )
        } catch (e: HttpException) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.code(),
                    _message = e.message()
                )
            )
        } catch (e: Exception) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
                )
            )
        }
    }

    override fun build(jobUrl: String) = flow {
        try {
            emit(Resource.Loading(true))
            val build = jobRepository.build(jobPath = jobUrl)

            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = build
                )
            )
        } catch (e: HttpException) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.code(),
                    _message = e.message()
                )
            )
        } catch (e: Exception) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
                )
            )
        }
    }

    override fun buildWithParameters(jobUrl: String, fieldMap: Map<String, String>) = flow {
        try {
            emit(Resource.Loading(true))
            val build = jobRepository.build(jobPath = jobUrl, fieldMap = fieldMap)
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = build
                )
            )
        } catch (e: HttpException) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.code(),
                    _message = e.message()
                )
            )
        } catch (e: Exception) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
                )
            )
        }
    }

    override fun getBuild(jobUrl: String, buildId: String) = flow {
        try {
            emit(Resource.Loading(true))
            val buildDetailModel = jobRepository.getBuild(jobPath = jobUrl, buildId = buildId)
            val consoleLog = jobRepository.getConsoleLog(jobPath = jobUrl, buildId = buildId)

            buildDetailModel.apply {
                this.consoleLog = consoleLog
            }

            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = buildDetailModel
                )
            )
        } catch (e: HttpException) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.code(),
                    _message = e.message()
                )
            )
        } catch (e: Exception) {
            logger.e(e)
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
                )
            )
        }
    }
}