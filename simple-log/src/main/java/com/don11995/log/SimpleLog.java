package com.don11995.log;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("all")
public class SimpleLog {

    public static final int LOG_LEVEL_NONE = -1;
    public static final int LOG_LEVEL_ERROR = 0;
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_WARNING = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_VERBOSE = 4;
    public static final int LOG_LEVEL_ALL = 5;
    private static final int MAX_LOG_CHUNK_SIZE = 4000;
    private static int mLogLevel = LOG_LEVEL_ALL;
    private static char sDividerChar = '-';
    private static int sDividerBlockSize = 8;

    public static void setLogLevel(@LogLevel int logLevel) {
        mLogLevel = logLevel;
    }

    public static void setDividerChar(char divider) {
        sDividerChar = divider;
    }

    public static void setDividerBlockSize(@IntRange(from = 1) int dividerBlockSize) {
        sDividerBlockSize = dividerBlockSize;
    }

    private static String formatGroup(String text, Object... args) {
        if (TextUtils.isEmpty(text)
                || args == null
                || args.length == 0)
            return text;
        return String.format(text,
                             args);
    }

    private static String formatGroup(Group group) {
        String format = group.getText();
        String groupName = group.getGroupName();
        if (groupName == null) groupName = "";
        char buffer[] = new char[sDividerBlockSize];
        Arrays.fill(buffer,
                    sDividerChar);
        String divider = new String(buffer);
        format = divider + groupName + divider + "\n" + format;
        buffer = new char[groupName.length()];
        Arrays.fill(buffer,
                    sDividerChar);
        format += "\n" + divider + divider + new String(buffer);
        return format;
    }

    public static void d() {
        printLog(LOG_LEVEL_DEBUG, null, true);
    }

    public static void e() {
        printLog(LOG_LEVEL_ERROR, null, true);
    }

    public static void i() {
        printLog(LOG_LEVEL_INFO, null, true);
    }

    public static void v() {
        printLog(LOG_LEVEL_VERBOSE, null, true);
    }

    public static void w() {
        printLog(LOG_LEVEL_WARNING, null, true);
    }

    public static void wtf() {
        printLog(LOG_LEVEL_ALL, null, true);
    }

    public static void d(Object object) {
        printLog(LOG_LEVEL_DEBUG, object != null ? object.toString() : null, false);
    }

    public static void e(Object object) {
        printLog(LOG_LEVEL_ERROR, object != null ? object.toString() : null, false);
    }

    public static void i(Object object) {
        printLog(LOG_LEVEL_INFO, object != null ? object.toString() : null, false);
    }

    public static void v(Object object) {
        printLog(LOG_LEVEL_VERBOSE, object != null ? object.toString() : null, false);
    }

    public static void w(Object object) {
        printLog(LOG_LEVEL_WARNING, object != null ? object.toString() : null, false);
    }

    public static void wtf(Object object) {
        printLog(LOG_LEVEL_ALL, object != null ? object.toString() : null, false);
    }

    public static void fd(Object object) {
        printLog(LOG_LEVEL_DEBUG, object != null ? object.toString() : null, true);
    }

    public static void fe(Object object) {
        printLog(LOG_LEVEL_ERROR, object != null ? object.toString() : null, true);
    }

    public static void fi(Object object) {
        printLog(LOG_LEVEL_INFO, object != null ? object.toString() : null, true);
    }

    public static void fv(Object object) {
        printLog(LOG_LEVEL_VERBOSE, object != null ? object.toString() : null, true);
    }

    public static void fw(Object object) {
        printLog(LOG_LEVEL_WARNING, object != null ? object.toString() : null, true);
    }

    public static void fwtf(Object object) {
        printLog(LOG_LEVEL_ALL, object != null ? object.toString() : null, true);
    }

    public static void d(String format, Object... args) {
        printLog(LOG_LEVEL_DEBUG, formatGroup(format, args), false);
    }

    public static void e(String format, Object... args) {
        printLog(LOG_LEVEL_ERROR, formatGroup(format, args), false);
    }

    public static void i(String format, Object... args) {
        printLog(LOG_LEVEL_INFO, formatGroup(format, args), false);
    }

    public static void v(String format, Object... args) {
        printLog(LOG_LEVEL_VERBOSE, formatGroup(format, args), false);
    }

    public static void w(String format, Object... args) {
        printLog(LOG_LEVEL_WARNING, formatGroup(format, args), false);
    }

    public static void wtf(String format, Object... args) {
        printLog(LOG_LEVEL_ALL, formatGroup(format, args), false);
    }

    public static void fd(String format, Object... args) {
        printLog(LOG_LEVEL_DEBUG, formatGroup(format, args), true);
    }

    public static void fe(String format, Object... args) {
        printLog(LOG_LEVEL_ERROR, formatGroup(format, args), true);
    }

    public static void fi(String format, Object... args) {
        printLog(LOG_LEVEL_INFO, formatGroup(format, args), true);
    }

    public static void fv(String format, Object... args) {
        printLog(LOG_LEVEL_VERBOSE, formatGroup(format, args), true);
    }

    public static void fw(String format, Object... args) {
        printLog(LOG_LEVEL_WARNING, formatGroup(format, args), true);
    }

    public static void fwtf(String format, Object... args) {
        printLog(LOG_LEVEL_ALL, formatGroup(format, args), true);
    }

    public static void d(Group group) {
        printLog(LOG_LEVEL_DEBUG, formatGroup(group), false);
    }

    public static void e(Group group) {
        printLog(LOG_LEVEL_ERROR, formatGroup(group), false);
    }

    public static void i(Group group) {
        printLog(LOG_LEVEL_INFO, formatGroup(group), false);
    }

    public static void v(Group group) {
        printLog(LOG_LEVEL_VERBOSE, formatGroup(group), false);
    }

    public static void w(Group group) {
        printLog(LOG_LEVEL_WARNING, formatGroup(group), false);
    }

    public static void wtf(Group group) {
        printLog(LOG_LEVEL_ALL, formatGroup(group), false);
    }

    public static void fd(Group group) {
        printLog(LOG_LEVEL_DEBUG, formatGroup(group), true);
    }

    public static void fe(Group group) {
        printLog(LOG_LEVEL_ERROR, formatGroup(group), true);
    }

    public static void fi(Group group) {
        printLog(LOG_LEVEL_INFO, formatGroup(group), true);
    }

    public static void fv(Group group) {
        printLog(LOG_LEVEL_VERBOSE, formatGroup(group), true);
    }

    public static void fw(Group group) {
        printLog(LOG_LEVEL_WARNING, formatGroup(group), true);
    }

    public static void fwtf(Group group) {
        printLog(LOG_LEVEL_ALL, formatGroup(group), true);
    }

    public static void e(Throwable throwable) {
        if (throwable == null) {
            printLog(LOG_LEVEL_ERROR, null, false);
            return;
        }
        StringWriter errors = new StringWriter();
        throwable.printStackTrace(new PrintWriter(errors));
        printLog(LOG_LEVEL_ERROR, errors.toString(), false);
    }

    public static void fe(Throwable throwable) {
        if (throwable == null) {
            printLog(LOG_LEVEL_ERROR, null, true);
            return;
        }
        StringWriter errors = new StringWriter();
        throwable.printStackTrace(new PrintWriter(errors));
        printLog(LOG_LEVEL_ERROR, errors.toString(), true);
    }

    private static void printLog(@LogLevel int logLevel,
                                 @Nullable String message,
                                 boolean printFunctionName) {
        if (mLogLevel < logLevel) return;
        if (message == null) message = "";
        StackTraceElement element = Thread.currentThread().getStackTrace()[4];
        String tag = element.getClassName();
        int lastDot = tag.lastIndexOf('.');
        if (lastDot >= 0) {
            tag = tag.substring(lastDot + 1);
        }
        int firstDollar = tag.indexOf('$');
        if (firstDollar > 0) {
            tag = tag.substring(0, firstDollar);
        }
        message = message.replaceAll("\r",
                                     "").replaceAll("\n\n",
                                                    "\n").trim();
        if (printFunctionName) {
            String method = element.getMethodName();
            if (message.isEmpty()) {
                message = method + "()";
            } else {
                message = method + "():\n" + message;
            }
        }
        if (message.isEmpty()) return;
        ArrayList<String> logs = new ArrayList<>();
        while (message.length() > MAX_LOG_CHUNK_SIZE) {
            String temp = message.substring(0,
                                            MAX_LOG_CHUNK_SIZE);
            int nextStartIndex = temp.lastIndexOf('\n') + 1;
            if (nextStartIndex <= 0) {
                nextStartIndex = MAX_LOG_CHUNK_SIZE;
            }
            temp = temp.substring(0,
                                  nextStartIndex).trim();
            logs.add(temp);
            message = message.substring(nextStartIndex);
        }
        logs.add(message);
        for (String s : logs) {
            switch (logLevel) {
                case LOG_LEVEL_DEBUG:
                    android.util.Log.d(tag,
                                       s);
                    break;
                case LOG_LEVEL_ERROR:
                    android.util.Log.e(tag,
                                       s);
                    break;
                case LOG_LEVEL_INFO:
                    android.util.Log.i(tag,
                                       s);
                    break;
                case LOG_LEVEL_VERBOSE:
                    android.util.Log.v(tag,
                                       s);
                    break;
                case LOG_LEVEL_WARNING:
                    android.util.Log.w(tag,
                                       s);
                    break;
                case LOG_LEVEL_ALL:
                    android.util.Log.wtf(tag,
                                         s);
                    break;
                case LOG_LEVEL_NONE:
                    break;
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            LOG_LEVEL_NONE,
            LOG_LEVEL_DEBUG,
            LOG_LEVEL_ERROR,
            LOG_LEVEL_INFO,
            LOG_LEVEL_VERBOSE,
            LOG_LEVEL_WARNING,
            LOG_LEVEL_ALL
    })
    private @interface LogLevel {
    }
}

