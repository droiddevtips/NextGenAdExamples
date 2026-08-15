package com.droiddevtips.nextgenexamples.logging.domain

/**
 * Sealed class with all the available log levels.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class LogLevel {
    data object Info: LogLevel()
    data object Error: LogLevel()
    data object Debug: LogLevel()
    data object Verbose: LogLevel()
    data object Warning: LogLevel()
}