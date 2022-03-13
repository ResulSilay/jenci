package com.x.data.api.model.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.x.domain.model.ViewItemModel
import com.x.domain.model.ViewModel

@Keep
data class ViewResponse(
    @field:Json(name = "nodeName") var nodeName: String? = null,
    @field:Json(name = "description") var description: String? = null,
    @field:Json(name = "url") var url: String? = null,
    @field:Json(name = "numExecutors") var numExecutors: Int? = null,
    @field:Json(name = "views") var views: List<ViewDataModel>? = listOf(),
    @field:Json(name = "jobs") var jobs: List<ViewJobDataModel>? = listOf(),
)

@Keep
data class ViewDataModel(
    @field:Json(name = "_class") var className: String? = null,
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "url") var url: String? = null,
)

@Keep
fun ViewResponse.toModel(): ViewModel {
    return ViewModel(
        name = nodeName,
        views = views?.map { view ->
            ViewItemModel(
                name = view.name,
                url = view.url
            )
        },
        viewCount = views?.size,
        jobCount = jobs?.count()
    )
}