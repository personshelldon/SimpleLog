/*
 * Modified by Vladyslav Lozytskyi on 05.05.18 22:05
 * Copyright (c) 2018. All rights reserved.
 */

@file:Suppress("unused")

package com.don11995.log

import android.os.Build
import android.support.annotation.IntRange
import android.text.TextUtils
import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

object SimpleLog {
    /**
     * Max log size for one Log call
     */
    private const val MAX_LOG_CHUNK_SIZE = 4000

    /**
     * Max tag length for devices with target API < 24
     */
    private const val MAX_TAG_LENGTH = 23

    /**
     * Constant to get class name from stack
     */
    private const val CALL_STACK_INDEX = 2

    /**
     * Array that contains enabled log levels
     */
    private var sLogLevels = HashSet<LogLevel>()

    /**
     * Divider char to use with [Group]
     */
    private var sDividerChar = '-'

    /**
     * Lenght of one block [.sDividerChar]
     */
    private var sDividerBlockSize = 8

    private var sLogProcessorList = ArrayList<LogProcessor>()

    private var sPrintReferences = false

    init {
        enableAllLogs()
    }

    /**
     * Disable all logs
     */
    @JvmStatic
    fun disableAllLogs() {
        sLogLevels.clear()
    }

    /**
     * Enable all logs
     */
    @JvmStatic
    fun enableAllLogs() {
        sLogLevels.add(LogLevel.ERROR)
        sLogLevels.add(LogLevel.DEBUG)
        sLogLevels.add(LogLevel.WARNING)
        sLogLevels.add(LogLevel.INFO)
        sLogLevels.add(LogLevel.VERBOSE)
        sLogLevels.add(LogLevel.ASSERT)
    }

    /**
     * Enable/disable log level
     *
     * @param logLevel log level to enable/disable. Can be one of [LogLevel]
     * @param enabled  enable/disable flag
     */
    @JvmStatic
    fun setLogLevelEnabled(logLevel: LogLevel, enabled: Boolean) {
        if (enabled) {
            sLogLevels.add(logLevel)
        } else {
            sLogLevels.remove(logLevel)
        }
    }

    /**
     * Check if log level enabled/disabled
     *
     * @param logLevel log level to enable/disable. Can be one of [LogLevel]
     * @return true, if logLevel enabled
     */
    @JvmStatic
    fun isLogLevelEnabled(logLevel: LogLevel): Boolean {
        return sLogLevels.contains(logLevel)
    }

    /**
     * Set divider char to use with [Group]
     *
     * @param divider char to set
     */
    @JvmStatic
    fun setDividerChar(divider: Char) {
        sDividerChar = divider
    }

    /**
     * Set divider block size to use with [Group]
     *
     * @param dividerBlockSize length of one block size
     */
    @JvmStatic
    fun setDividerBlockSize(@IntRange(from = 1) dividerBlockSize: Int) {
        sDividerBlockSize = if (dividerBlockSize <= 0) {
            1
        } else {
            dividerBlockSize
        }
    }

    private fun formatText(text: String?, vararg args: Any): String? {
        return if (TextUtils.isEmpty(text)
                || args.isEmpty()) {
            text
        } else {
            String.format(Locale.getDefault(), text!!, *args)
        }
    }

    private fun formatText(group: Group): String {
        var format = group.mText
        val groupName = group.mGroupName
        var buffer = CharArray(sDividerBlockSize)
        Arrays.fill(buffer,
                sDividerChar)
        val divider = String(buffer)
        format = divider + groupName + divider + "\n\t" + format
        buffer = CharArray(groupName.length)
        Arrays.fill(buffer,
                sDividerChar)
        format += "\n\t" + divider + divider + String(buffer)
        return format
    }

    private fun getTagFromObject(o: Any?): String? {
        return if (o is Group) {
            o.mTag
        } else {
            null
        }
    }

    private fun getMessageFromObject(o: Any?): String? {
        return when (o) {
            is Group -> formatText(o)
            is Throwable -> {
                val errors = StringWriter()
                o.printStackTrace(PrintWriter(errors))
                errors.toString()
            }
            else -> o?.toString()
        }
    }

    private fun isGroupObject(o: Any?): Boolean {
        return o is Group
    }

    /**
     * Print current method name
     */
    @JvmStatic
    fun fd() {
        printLog(LogLevel.DEBUG, null, true, null, null, false)
    }

    /**
     * Print current method name
     */
    @JvmStatic
    fun fe() {
        printLog(LogLevel.ERROR, null, true, null, null, false)
    }

    /**
     * Print current method name
     */
    @JvmStatic
    fun fi() {
        printLog(LogLevel.INFO, null, true, null, null, false)
    }

    /**
     * Print current method name
     */
    @JvmStatic
    fun fv() {
        printLog(LogLevel.VERBOSE, null, true, null, null, false)
    }

    /**
     * Print current method name
     */
    @JvmStatic
    fun fw() {
        printLog(LogLevel.WARNING, null, true, null, null, false)
    }

    /**
     * Print current method name
     */
    @JvmStatic
    fun fwtf() {
        printLog(LogLevel.ASSERT, null, true, null, null, false)
    }

    /**
     * Print current method name with custom tag
     *
     * @param tag tag to use
     */
    @JvmStatic
    fun tfd(tag: String?) {
        printLog(LogLevel.DEBUG, null, true, tag, null, false)
    }

    /**
     * Print current method name with custom tag
     *
     * @param tag tag to use
     */
    @JvmStatic
    fun tfe(tag: String?) {
        printLog(LogLevel.ERROR, null, true, tag, null, false)
    }

    /**
     * Print current method name with custom tag
     *
     * @param tag tag to use
     */
    @JvmStatic
    fun tfi(tag: String?) {
        printLog(LogLevel.INFO, null, true, tag, null, false)
    }

    /**
     * Print current method name with custom tag
     *
     * @param tag tag to use
     */
    @JvmStatic
    fun tfv(tag: String?) {
        printLog(LogLevel.VERBOSE, null, true, tag, null, false)
    }

    /**
     * Print current method name with custom tag
     *
     * @param tag tag to use
     */
    @JvmStatic
    fun tfw(tag: String?) {
        printLog(LogLevel.WARNING, null, true, tag, null, false)
    }

    /**
     * Print current method name with custom tag
     *
     * @param tag tag to use
     */
    @JvmStatic
    fun tfwtf(tag: String?) {
        printLog(LogLevel.ASSERT, null, true, tag, null, false)
    }

    /**
     * Print Object into logcat. Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun d(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.DEBUG, getMessageFromObject(o), false, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat. Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun e(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ERROR, getMessageFromObject(o), false, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat. Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun i(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.INFO, getMessageFromObject(o), false, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat. Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun v(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.VERBOSE, getMessageFromObject(o), false, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat. Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun w(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.WARNING, getMessageFromObject(o), false, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat. Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun wtf(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ASSERT, getMessageFromObject(o), false, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   to print into logcat
     */
    @JvmStatic
    fun td(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.DEBUG, getMessageFromObject(o), false, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun te(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ERROR, getMessageFromObject(o), false, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun ti(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.INFO, getMessageFromObject(o), false, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tv(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.VERBOSE, getMessageFromObject(o), false, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tw(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.WARNING, getMessageFromObject(o), false, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun twtf(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ASSERT, getMessageFromObject(o), false, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun fd(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.DEBUG, getMessageFromObject(o), true, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun fe(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ERROR, getMessageFromObject(o), true, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun fi(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.INFO, getMessageFromObject(o), true, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun fv(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.VERBOSE, getMessageFromObject(o), true, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun fw(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.WARNING, getMessageFromObject(o), true, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * Object can be any type or [Group]
     *
     * @param o object to print into logcat
     */
    @JvmStatic
    fun fwtf(o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ASSERT, getMessageFromObject(o), true, getTagFromObject(o),
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tfd(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.DEBUG, getMessageFromObject(o), true, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tfe(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ERROR, getMessageFromObject(o), true, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tfi(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.INFO, getMessageFromObject(o), true, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tfv(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.VERBOSE, getMessageFromObject(o), true, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tfw(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.WARNING, getMessageFromObject(o), true, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag tag to use
     * @param o   object to print into logcat
     */
    @JvmStatic
    fun tfwtf(tag: String?, o: Any?) {
        val isGroup = isGroupObject(o)
        printLog(LogLevel.ASSERT, getMessageFromObject(o), true, tag,
                o as? Throwable,
                isGroup)
    }

    /**
     * Print string with args using [String.format]
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun d(format: String?, vararg args: Any) {
        printLog(LogLevel.DEBUG, formatText(format, *args), false, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun e(format: String?, vararg args: Any) {
        printLog(LogLevel.ERROR, formatText(format, *args), false, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun i(format: String?, vararg args: Any) {
        printLog(LogLevel.INFO, formatText(format, *args), false, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun v(format: String?, vararg args: Any) {
        printLog(LogLevel.VERBOSE, formatText(format, *args), false, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun w(format: String?, vararg args: Any) {
        printLog(LogLevel.WARNING, formatText(format, *args), false, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun wtf(format: String?, vararg args: Any) {
        printLog(LogLevel.ASSERT, formatText(format, *args), false, null, null, false)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun td(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.DEBUG, formatText(format, *objects), false, tag, null, false)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun te(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.ERROR, formatText(format, *objects), false, tag, null, false)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun ti(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.INFO, formatText(format, *objects), false, tag, null, false)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tv(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.VERBOSE, formatText(format, *objects), false, tag, null, false)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tw(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.WARNING, formatText(format, *objects), false, tag, null, false)
    }

    /**
     * Print Object into logcat with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun twtf(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.ASSERT, formatText(format, *objects), false, tag, null, false)
    }

    /**
     * Print string with args using [String.format]
     * with current method name at start
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun fd(format: String?, vararg args: Any) {
        printLog(LogLevel.DEBUG, formatText(format, *args), true, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     * with current method name at start
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun fe(format: String?, vararg args: Any) {
        printLog(LogLevel.ERROR, formatText(format, *args), true, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     * with current method name at start
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun fi(format: String?, vararg args: Any) {
        printLog(LogLevel.INFO, formatText(format, *args), true, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     * with current method name at start
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun fv(format: String?, vararg args: Any) {
        printLog(LogLevel.VERBOSE, formatText(format, *args), true, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     * with current method name at start
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun fw(format: String?, vararg args: Any) {
        printLog(LogLevel.WARNING, formatText(format, *args), true, null, null, false)
    }

    /**
     * Print string with args using [String.format]
     * with current method name at start
     *
     * @param format string to print
     * @param args   args to use with [String.format]
     */
    @JvmStatic
    fun fwtf(format: String?, vararg args: Any) {
        printLog(LogLevel.ASSERT, formatText(format, *args), true, null, null, false)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tfd(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.DEBUG, formatText(format, *objects), true, tag, null, false)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tfe(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.ERROR, formatText(format, *objects), true, tag, null, false)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tfi(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.INFO, formatText(format, *objects), true, tag, null, false)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tfv(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.VERBOSE, formatText(format, *objects), true, tag, null, false)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tfw(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.WARNING, formatText(format, *objects), true, tag, null, false)
    }

    /**
     * Print Object into logcat with current function name at start of log
     * and with custom tag
     * Object can be any type or [Group]
     *
     * @param tag     tag to use
     * @param format  string to print
     * @param objects args to use with [String.format]
     */
    @JvmStatic
    fun tfwtf(tag: String, format: String?, vararg objects: Any) {
        printLog(LogLevel.ASSERT, formatText(format, *objects), true, tag, null, false)
    }

    /**
     * Add [LogProcessor] to log callbacks.
     * [LogProcessor] can add additional logic to every log call
     *
     * @param logProcessor [LogProcessor] to add
     */
    @JvmStatic
    fun addLogProcessor(logProcessor: LogProcessor) {
        sLogProcessorList.add(logProcessor)
    }

    /**
     * Remove [LogProcessor] from log callbacks
     *
     * @param logProcessor [LogProcessor] to delete
     */
    @JvmStatic
    fun removeLogProcessor(logProcessor: LogProcessor) {
        sLogProcessorList.remove(logProcessor)
    }

    /**
     * Enable/disable printing line of code where log was called
     *
     * @param enabled enable/disable print log reference
     */
    @JvmStatic
    fun setPrintReferences(enabled: Boolean) {
        sPrintReferences = enabled
    }

    private fun detectClassName(origClassName: String): String {
        if (TextUtils.isEmpty(origClassName)) return origClassName
        var className = origClassName
        val lastDot = className.lastIndexOf('.')
        if (lastDot >= 0) {
            className = className.substring(lastDot + 1)
        }
        val firstDollar = className.indexOf('$')
        if (firstDollar > 0) {
            className = className.substring(0, firstDollar)
        }
        return className
    }

    private fun printLog(logLevel: LogLevel,
                         inMessage: String?,
                         printMethodName: Boolean,
                         inTag: String?,
                         e: Throwable?,
                         isGroup: Boolean) {
        var message = inMessage ?: ""
        var tag = inTag ?: ""
        val element = Throwable().stackTrace[CALL_STACK_INDEX]
        val fileExtension = if (Class.forName(element.className).isKotlinClass()) {
            ".kt"
        } else {
            ".java"
        }
        val className = detectClassName(element.className)
        if (TextUtils.isEmpty(tag)) {
            tag = className
        }
        if (tag.length > MAX_TAG_LENGTH
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            tag = tag.substring(0, MAX_TAG_LENGTH)
        }
        message = message.replace("\r", "")
                .replace("\n+".toRegex(), "\n")
                .trim()
        if (printMethodName) {
            val method = element.methodName
            message = if (message.isEmpty()) {
                "$method()"
            } else {
                (method + "() -> "
                        + (if (isGroup) "\n\t" else "")
                        + message)
            }
        }
        if (sPrintReferences && e == null) {
            message = ("($className$fileExtension:${element.lineNumber})"
                    + (if (isGroup) "\n\t" else " ")
                    + message)
            message = message.trim()
        }
        val logs = ArrayList<String>()
        while (message.length > MAX_LOG_CHUNK_SIZE) {
            var temp = message.substring(0, MAX_LOG_CHUNK_SIZE)
            var nextStartIndex = temp.lastIndexOf('\n')
            if (nextStartIndex <= 0) {
                nextStartIndex = MAX_LOG_CHUNK_SIZE
            }
            temp = temp.substring(0, nextStartIndex).trim()
            logs.add(temp)
            message = message.substring(nextStartIndex).trim()
        }
        logs.add(message)
        if (sLogLevels.contains(logLevel)) {
            for (s in logs) {
                if (s.isEmpty()) continue
                when (logLevel) {
                    LogLevel.DEBUG -> Log.d(tag, s)
                    LogLevel.ERROR -> Log.e(tag, s)
                    LogLevel.INFO -> Log.i(tag, s)
                    LogLevel.VERBOSE -> Log.v(tag, s)
                    LogLevel.WARNING -> Log.w(tag, s)
                    LogLevel.ASSERT -> Log.wtf(tag, s)
                }
            }
        }
        val fullMessage = logs.joinToString("", transform = { it })
        sLogProcessorList.forEach({ it.handleProcessLog(tag, fullMessage, logLevel, e) })
    }
}