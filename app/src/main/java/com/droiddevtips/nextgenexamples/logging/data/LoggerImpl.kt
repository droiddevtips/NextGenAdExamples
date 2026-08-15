package com.droiddevtips.nextgenexamples.logging.data

import android.util.Log
import com.droiddevtips.nextgenexamples.logging.domain.LogLevel
import com.droiddevtips.nextgenexamples.logging.domain.Logger

/**
 * Default [Logger] implementation used across the entire app.
 *
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class LoggerImpl: Logger {
    override fun log(
        level: LogLevel,
        message: String
    ) {
        val tag = "[Ad Example]"
        val logMessage = "[${this::class.java}] - $message"

        when(level) {
            LogLevel.Info -> Log.i(tag,logMessage)

            LogLevel.Error -> Log.e(tag,logMessage)

            LogLevel.Debug -> Log.d(tag,logMessage)

            LogLevel.Verbose -> Log.v(tag,logMessage)

            LogLevel.Warning -> Log.w(tag,logMessage)
        }
    }
}