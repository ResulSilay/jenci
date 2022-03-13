package com.x.domain.usecase.impl

import com.x.common.logger.Logger
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus
import com.x.domain.model.BuildChartItemModel
import com.x.domain.model.BuildChartModel
import com.x.domain.model.JobBuildDetailModel
import com.x.domain.model.StatsModel
import com.x.domain.repository.api.JobRepository
import com.x.domain.repository.api.NodeRepository
import com.x.domain.repository.api.QueueRepository
import com.x.domain.repository.api.ViewRepository
import com.x.domain.usecase.DashboardUseCase
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

class DashboardUseCaseImpl @Inject constructor(
    private val logger: Logger,
    private val nodeRepository: NodeRepository,
    private val viewRepository: ViewRepository,
    private val jobRepository: JobRepository,
    private val queueRepository: QueueRepository,
) : DashboardUseCase {

    override fun getStats() = flow {
        try {
            emit(Resource.Loading(true))
            val nodeModel = nodeRepository.getAll()
            val viewModel = viewRepository.getAllView()
            val queueModel = queueRepository.getAll()
            val buildModel = jobRepository.getBuilds()

            val buildList = ArrayList<JobBuildDetailModel>()
            buildModel.jobs?.forEach {
                it.builds?.forEach { buildIt ->
                    buildList.add(JobBuildDetailModel().apply {
                        duration = buildIt.duration
                        timestamp = buildIt.timestamp
                    })
                }
            }

            val calendar = Calendar.getInstance()
            val groups = buildList.groupBy { item ->
                item.timestamp?.let {
                    val date = Date(it)
                    calendar.time = date
                }
                calendar.get(Calendar.DAY_OF_WEEK)
            }

            val buildChartModel = BuildChartModel().apply {
                items = groups.map {
                    BuildChartItemModel().apply {
                        day = it.key
                        buildCount = it.value.size
                    }
                }
            }

            val statsModel = StatsModel().apply {
                nodeCount = nodeModel.computer?.size
                viewCount = viewModel.viewCount
                jobCount = viewModel.jobCount
                queueCount = queueModel.queueCount
                queueList = queueModel.items
                buildChartList = buildChartModel
            }
            emit(
                Resource.Success(
                    _code = ResourceStatus.SUCCESS.ordinal,
                    _data = statsModel
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