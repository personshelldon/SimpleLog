package com.don11995.log

import android.util.Log
import com.google.common.truth.Truth.assertThat
import org.robolectric.shadows.ShadowLog

@Suppress("unused")
internal class LogAssert private constructor(private val items: List<ShadowLog.LogItem>) {
    private var index = 0

    fun hasVerboseMessage(tag: String, message: String): LogAssert {
        return hasMessage(Log.VERBOSE, tag, message)
    }

    fun hasDebugMessage(tag: String, message: String): LogAssert {
        return hasMessage(Log.DEBUG, tag, message)
    }

    fun hasInfoMessage(tag: String, message: String): LogAssert {
        return hasMessage(Log.INFO, tag, message)
    }

    fun hasWarnMessage(tag: String, message: String): LogAssert {
        return hasMessage(Log.WARN, tag, message)
    }

    fun hasErrorMessage(tag: String, message: String): LogAssert {
        return hasMessage(Log.ERROR, tag, message)
    }

    fun hasAssertMessage(tag: String, message: String): LogAssert {
        return hasMessage(Log.ASSERT, tag, message)
    }

    fun hasDebugMessageStartsWith(tag: String, message: String): LogAssert {
        return hasMessageStartWith(Log.DEBUG, tag, message)
    }

    fun hasErrorMessageStartsWith(tag: String, message: String): LogAssert {
        return hasMessageStartWith(Log.ERROR, tag, message)
    }

    fun hasWarnMessageStartsWith(tag: String, message: String): LogAssert {
        return hasMessageStartWith(Log.WARN, tag, message)
    }

    private fun hasMessage(priority: Int, tag: String, message: String): LogAssert {
        val item = items[index++]
        assertThat(item.type).isEqualTo(priority)
        assertThat(item.tag).isEqualTo(tag)
        assertThat(item.msg).isEqualTo(message)
        return this
    }

    private fun hasMessageStartWith(priority: Int, tag: String, message: String): LogAssert {
        val item = items[index++]
        assertThat(item.type).isEqualTo(priority)
        assertThat(item.tag).isEqualTo(tag)
        assertThat(item.msg).startsWith(message)
        return this
    }

    fun hasNoMoreMessages() {
        assertThat(items).hasSize(index)
    }

    companion object {

        fun assertLog(tag: String): LogAssert {
            return LogAssert(ShadowLog.getLogsForTag(tag))
        }
    }
}
