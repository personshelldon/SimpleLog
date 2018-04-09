/*
 * Modified by Vladyslav Lozytskyi on 4/9/18 11:23 PM
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class LogProcessor {

    private final Handler mHandler;

    public LogProcessor() {
        this(new Handler());
    }

    public LogProcessor(@NonNull Handler handler) {
        mHandler = handler;
    }

    void handleProcessLog(String tag,
                          String message,
                          @SimpleLog.LogLevel int priority,
                          @Nullable Throwable e) {
        mHandler.post(() -> processLog(tag, message, priority, e));
    }

    public abstract void processLog(String tag,
                                    String message,
                                    @SimpleLog.LogLevel int priority,
                                    @Nullable Throwable e);
}
