package com.x.common.extension

import com.x.common.type.PathType

fun String.pathToTitle(pathType: PathType = PathType.JOB): String {
    return this.replace("/${pathType.value}/", "/")
}

fun String.toParamName(): String {
    return this.removePrefix("{").removeSuffix("}")
}

fun String?.urlToPath(pathType: PathType = PathType.JOB): String {
    var path = this.urlDecode()
    val pathStartIndex = path.indexOf("${pathType.value}/")
    path = path.substring(pathStartIndex, path.length)

    if (!path.startsWith(pathType.value.toString())) {
        path = "${pathType.value}/$path"
    }

    return path.removePrefix("/").removeSuffix("/")
}