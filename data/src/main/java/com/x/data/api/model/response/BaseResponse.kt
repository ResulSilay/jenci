package com.x.data.api.model.response

import androidx.annotation.Keep
import com.x.domain.model.ResponseModel
import okhttp3.ResponseBody
import retrofit2.Response

@Keep
fun Response<String>.toResponseString(): String {
    return body().toString()
}

@Keep
fun Response<ResponseBody>.toModel(): ResponseModel {
    return ResponseModel(
        code = code(),
        body = body()?.string()
    )
}