package com.droiddevtips.nextgenexamples.logging.domain

/**
 * Contract for application-wide logging.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface Logger {
    fun log(level: LogLevel = LogLevel.Info, message: String)
}