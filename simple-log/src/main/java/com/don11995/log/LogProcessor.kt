package com.don11995.log

import android.os.Handler

/**
 * Callback for every log call.
 *
 * @param handler [Handler] for [processLog] function
 */
abstract class LogProcessor(private val handler: Handler = Handler()) {

    /**
     * Callback wrapper on every log call.
     *
     * @param tag      log tag
     * @param message  log message
     * @param priority log priority [LogLevel]
     * @param e        source exception if thrown. May be null.
     */
    internal fun handleProcessLog(tag: String,
                                  message: String,
                                  priority: LogLevel,
                                  e: Throwable?) {
        handler.post {
            processLog(tag, message, priority, e)
        }
    }

    /**
     * Callback on every log call.
     * Will be called only after log printed to log.
     *
     * @param tag      log tag
     * @param message  log message
     * @param priority log priority [LogLevel]
     * @param e        source exception if thrown. May be null.
     */
    abstract fun processLog(tag: String,
                            message: String,
                            priority: LogLevel,
                            e: Throwable?)
}
