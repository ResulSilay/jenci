package com.x.data.api.model.response

import androidx.annotation.Keep
import com.x.domain.model.ActionModel
import okhttp3.ResponseBody
import retrofit2.Response

@Keep
fun Response<ResponseBody>.toActionModel(): ActionModel {
    return ActionModel(
        status = code()
    )
}