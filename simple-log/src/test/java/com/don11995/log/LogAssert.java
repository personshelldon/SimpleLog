/*
 * Modified by Vladyslav Lozytskyi on 4/10/18 12:21 AM
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

import android.util.Log;

import org.robolectric.shadows.ShadowLog;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class LogAssert {
    private final List<ShadowLog.LogItem> items;
    private int index = 0;

    private LogAssert(List<ShadowLog.LogItem> items) {
        this.items = items;
    }

    static LogAssert assertLog() {
        return new LogAssert(ShadowLog.getLogs());
    }

    LogAssert hasVerboseMessage(String tag, String message) {
        return hasMessage(Log.VERBOSE, tag, message);
    }

    LogAssert hasDebugMessage(String tag, String message) {
        return hasMessage(Log.DEBUG, tag, message);
    }

    LogAssert hasInfoMessage(String tag, String message) {
        return hasMessage(Log.INFO, tag, message);
    }

    LogAssert hasWarnMessage(String tag, String message) {
        return hasMessage(Log.WARN, tag, message);
    }

    LogAssert hasErrorMessage(String tag, String message) {
        return hasMessage(Log.ERROR, tag, message);
    }

    LogAssert hasAssertMessage(String tag, String message) {
        return hasMessage(Log.ASSERT, tag, message);
    }

    LogAssert hasDebugMessageStartsWith(String tag, String message) {
        return hasMessageStartWith(Log.DEBUG, tag, message);
    }

    LogAssert hasErrorMessageStartsWith(String tag, String message) {
        return hasMessageStartWith(Log.ERROR, tag, message);
    }

    LogAssert hasWarnMessageStartsWith(String tag, String message) {
        return hasMessageStartWith(Log.WARN, tag, message);
    }

    private LogAssert hasMessage(int priority, String tag, String message) {
        ShadowLog.LogItem item = items.get(index++);
        assertThat(item.type).isEqualTo(priority);
        assertThat(item.tag).isEqualTo(tag);
        assertThat(item.msg).isEqualTo(message);
        return this;
    }

    private LogAssert hasMessageStartWith(int priority, String tag, String message) {
        ShadowLog.LogItem item = items.get(index++);
        assertThat(item.type).isEqualTo(priority);
        assertThat(item.tag).isEqualTo(tag);
        assertThat(item.msg).startsWith(message);
        return this;
    }

    void hasNoMoreMessages() {
        assertThat(items).hasSize(index);
    }
}
