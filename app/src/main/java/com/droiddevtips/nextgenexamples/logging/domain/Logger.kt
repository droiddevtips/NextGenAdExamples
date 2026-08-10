package com.droiddevtips.nextgenexamples.logging.domain

interface Logger {
    fun log(level: LogLevel = LogLevel.Info, message: String)
}