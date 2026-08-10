package com.droiddevtips.nextgenexamples.logging.domain

sealed class LogLevel {
    data object Info: LogLevel()
    data object Error: LogLevel()
    data object Debug: LogLevel()
    data object Verbose: LogLevel()
    data object Warning: LogLevel()
}