package com.x.common.logger

interface Logger {

    fun init()

    fun e(t: Throwable?)

    fun log(log: String?)
}