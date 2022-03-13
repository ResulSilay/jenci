package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.repository.api.QueueRepository
import com.x.domain.usecase.QueueUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class QueueUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val queueRepository: QueueRepository,
) : QueueUseCase {

    override fun getAllQueue() = flow {
        try {
            emit(Resource.Loading(true))
            val queueModel = queueRepository.getAll()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = queueModel
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

    override fun cancelQueue(queueId: String) = flow {
        try {
            emit(Resource.Loading(true))
            val actionModel = queueRepository.cancelQueue(queueId = queueId)
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = actionModel
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Failure(
                    _code = e.code(),
                    _message = e.message()
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Failure(
                    _code = e.hashCode(),
                    _message = e.localizedMessage
                )
            )
        }
    }
}