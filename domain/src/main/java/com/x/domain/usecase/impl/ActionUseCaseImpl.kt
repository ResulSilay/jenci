package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.repository.api.ActionRepository
import com.x.domain.usecase.ActionUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class ActionUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val actionRepository: ActionRepository,
) : ActionUseCase {

    override fun restart() = flow {
        try {
            emit(Resource.Loading(true))
            val actionModel = actionRepository.restart()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = actionModel
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

    override fun safeRestart() = flow {
        try {
            emit(Resource.Loading(true))
            val actionModel = actionRepository.safeRestart()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = actionModel
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

    override fun shutdown() = flow {
        try {
            emit(Resource.Loading(true))
            val actionModel = actionRepository.shutdown()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = actionModel
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

    override fun safeShutdown() = flow {
        try {
            emit(Resource.Loading(true))
            val actionModel = actionRepository.safeShutdown()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = actionModel
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

    override fun cancelQuietDown() = flow {
        try {
            emit(Resource.Loading(true))
            val actionModel = actionRepository.cancelQuietDown()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = actionModel
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

    override fun quietDown() = flow {
        try {
            emit(Resource.Loading(true))
            val actionModel = actionRepository.quietDown()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = actionModel
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