package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.repository.api.NodeRepository
import com.x.domain.usecase.NodeUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class NodeUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val nodeRepository: NodeRepository,
) : NodeUseCase {

    override fun getAllNode() = flow {
        try {
            emit(Resource.Loading(true))
            val nodeModel = nodeRepository.getAll()
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = nodeModel
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

    override fun get(nodeName: String) = flow {
        try {
            emit(Resource.Loading(true))
            val viewJobModel = nodeRepository.getBy(name = nodeName)
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = viewJobModel
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