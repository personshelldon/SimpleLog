/*
 * Modified by Vladyslav Lozytskyi on 11.04.18 1:23
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public abstract class LogProcessor {

    private final Handler mHandler;

    /**
     * Callback for every log call.
     */
    public LogProcessor() {
        this(new Handler());
    }

    /**
     * Callback for every log call.
     *
     * @param handler {@link Handler} for {@link #processLog} function
     */
    public LogProcessor(@NonNull Handler handler) {
        mHandler = handler;
    }

    /**
     * Callback wrapper on every log call.
     *
     * @param tag      log tag
     * @param message  log message
     * @param priority log priority {@link SimpleLog.LogLevel}
     * @param e        source exception if thrown. May be null.
     */
    protected void handleProcessLog(String tag,
                                    String message,
                                    @SimpleLog.LogLevel int priority,
                                    @Nullable Throwable e) {
        mHandler.post(() -> processLog(tag, message, priority, e));
    }

    /**
     * Callback on every log call.
     * Will be called only after log printed to log.
     *
     * @param tag      log tag
     * @param message  log message
     * @param priority log priority {@link SimpleLog.LogLevel}
     * @param e        source exception if thrown. May be null.
     */
    public abstract void processLog(String tag,
                                    String message,
                                    @SimpleLog.LogLevel int priority,
                                    @Nullable Throwable e);
}
