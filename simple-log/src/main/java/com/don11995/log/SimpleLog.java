package com.don11995.log;

import android.os.Build;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@SuppressWarnings("all")
public class SimpleLog {

    public static final int LOG_LEVEL_ERROR = 0;
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_WARNING = 2;
    public static final int LOG_LEVEL_INFO = 3;
    public static final int LOG_LEVEL_VERBOSE = 4;
    public static final int LOG_LEVEL_ASSERT = 5;
    private static final int MAX_LOG_CHUNK_SIZE = 4000;
    private static final int MAX_TAG_LENGTH = 23;
    private static final int CALL_STACK_INDEX = 2;
    private static final Set<Integer> mLogLevels;
    private static char sDividerChar = '-';
    private static int sDividerBlockSize = 8;

    static {
        mLogLevels = new HashSet<>();
        enableAllLogs();
    }

    private SimpleLog() { }

    public static void disableAllLogs() {
        mLogLevels.clear();
    }

    public static void enableAllLogs() {
        mLogLevels.add(LOG_LEVEL_ERROR);
        mLogLevels.add(LOG_LEVEL_DEBUG);
        mLogLevels.add(LOG_LEVEL_WARNING);
        mLogLevels.add(LOG_LEVEL_INFO);
        mLogLevels.add(LOG_LEVEL_VERBOSE);
        mLogLevels.add(LOG_LEVEL_ASSERT);
    }

    public static void setLogLevelEnabled(@LogLevel int logLevel, boolean enabled) {
        if (enabled) {
            mLogLevels.add(logLevel);
        } else {
            mLogLevels.remove(logLevel);
        }
    }

    public static void setDividerChar(char divider) {
        sDividerChar = divider;
    }

    public static void setDividerBlockSize(@IntRange(from = 1) int dividerBlockSize) {
        sDividerBlockSize = dividerBlockSize;
    }

    private static String formatText(String text, Object... args) {
        if (TextUtils.isEmpty(text)
                || args == null
                || args.length == 0)
            return text;
        return String.format(Locale.getDefault(), text, args);
    }

    private static String formatText(Group group) {
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

    @Nullable
    private static String getTagFromObject(@Nullable Object object) {
        if (object instanceof Group) {
            return ((Group) object).getTag();
        } else {
            return null;
        }
    }

    @Nullable
    private static String getMessageFromObject(@Nullable Object object) {
        if (object instanceof Group) {
            return formatText((Group) object);
        } else if (object instanceof Throwable) {
            StringWriter errors = new StringWriter();
            ((Throwable) object).printStackTrace(new PrintWriter(errors));
            return errors.toString();
        } else {
            return object != null ? object.toString() : null;
        }
    }

    public static void fd() {
        printLog(LOG_LEVEL_DEBUG, null, true, null);
    }

    public static void fe() {
        printLog(LOG_LEVEL_ERROR, null, true, null);
    }

    public static void fi() {
        printLog(LOG_LEVEL_INFO, null, true, null);
    }

    public static void fv() {
        printLog(LOG_LEVEL_VERBOSE, null, true, null);
    }

    public static void fw() {
        printLog(LOG_LEVEL_WARNING, null, true, null);
    }

    public static void fwtf() {
        printLog(LOG_LEVEL_ASSERT, null, true, null);
    }

    public static void tfd(String tag) {
        printLog(LOG_LEVEL_DEBUG, null, true, tag);
    }

    public static void tfe(String tag) {
        printLog(LOG_LEVEL_ERROR, null, true, tag);
    }

    public static void tfi(String tag) {
        printLog(LOG_LEVEL_INFO, null, true, tag);
    }

    public static void tfv(String tag) {
        printLog(LOG_LEVEL_VERBOSE, null, true, tag);
    }

    public static void tfw(String tag) {
        printLog(LOG_LEVEL_WARNING, null, true, tag);
    }

    public static void tfwtf(String tag) {
        printLog(LOG_LEVEL_ASSERT, null, true, tag);
    }

    public static void d(Object object) {
        printLog(LOG_LEVEL_DEBUG, getMessageFromObject(object), false, getTagFromObject(object));
    }

    public static void e(Object object) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(object), false, getTagFromObject(object));
    }

    public static void i(Object object) {
        printLog(LOG_LEVEL_INFO, getMessageFromObject(object), false, getTagFromObject(object));
    }

    public static void v(Object object) {
        printLog(LOG_LEVEL_VERBOSE, getMessageFromObject(object), false, getTagFromObject(object));
    }

    public static void w(Object object) {
        printLog(LOG_LEVEL_WARNING, getMessageFromObject(object), false, getTagFromObject(object));
    }

    public static void wtf(Object object) {
        printLog(LOG_LEVEL_ASSERT, getMessageFromObject(object), false, getTagFromObject(object));
    }

    public static void td(String tag, Object object) {
        printLog(LOG_LEVEL_DEBUG, getMessageFromObject(object), false, tag);
    }

    public static void te(String tag, Object object) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(object), false, tag);
    }

    public static void ti(String tag, Object object) {
        printLog(LOG_LEVEL_INFO, getMessageFromObject(object), false, tag);
    }

    public static void tv(String tag, Object object) {
        printLog(LOG_LEVEL_VERBOSE, getMessageFromObject(object), false, tag);
    }

    public static void tw(String tag, Object object) {
        printLog(LOG_LEVEL_WARNING, getMessageFromObject(object), false, tag);
    }

    public static void twtf(String tag, Object object) {
        printLog(LOG_LEVEL_ASSERT, getMessageFromObject(object), false, tag);
    }

    public static void fd(Object object) {
        printLog(LOG_LEVEL_DEBUG, getMessageFromObject(object), true, getTagFromObject(object));
    }

    public static void fe(Object object) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(object), true, getTagFromObject(object));
    }

    public static void fi(Object object) {
        printLog(LOG_LEVEL_INFO, getMessageFromObject(object), true, getTagFromObject(object));
    }

    public static void fv(Object object) {
        printLog(LOG_LEVEL_VERBOSE, getMessageFromObject(object), true, getTagFromObject(object));
    }

    public static void fw(Object object) {
        printLog(LOG_LEVEL_WARNING, getMessageFromObject(object), true, getTagFromObject(object));
    }

    public static void fwtf(Object object) {
        printLog(LOG_LEVEL_ASSERT, getMessageFromObject(object), true, getTagFromObject(object));
    }

    public static void tfd(String tag, Object object) {
        printLog(LOG_LEVEL_DEBUG, getMessageFromObject(object), true, tag);
    }

    public static void tfe(String tag, Object object) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(object), true, tag);
    }

    public static void tfi(String tag, Object object) {
        printLog(LOG_LEVEL_INFO, getMessageFromObject(object), true, tag);
    }

    public static void tfv(String tag, Object object) {
        printLog(LOG_LEVEL_VERBOSE, getMessageFromObject(object), true, tag);
    }

    public static void tfw(String tag, Object object) {
        printLog(LOG_LEVEL_WARNING, getMessageFromObject(object), true, tag);
    }

    public static void tfwtf(String tag, Object object) {
        printLog(LOG_LEVEL_ASSERT, getMessageFromObject(object), true, tag);
    }

    public static void d(String format, Object... args) {
        printLog(LOG_LEVEL_DEBUG, formatText(format, args), false, null);
    }

    public static void e(String format, Object... args) {
        printLog(LOG_LEVEL_ERROR, formatText(format, args), false, null);
    }

    public static void i(String format, Object... args) {
        printLog(LOG_LEVEL_INFO, formatText(format, args), false, null);
    }

    public static void v(String format, Object... args) {
        printLog(LOG_LEVEL_VERBOSE, formatText(format, args), false, null);
    }

    public static void w(String format, Object... args) {
        printLog(LOG_LEVEL_WARNING, formatText(format, args), false, null);
    }

    public static void wtf(String format, Object... args) {
        printLog(LOG_LEVEL_ASSERT, formatText(format, args), false, null);
    }

    public static void fd(String format, Object... args) {
        printLog(LOG_LEVEL_DEBUG, formatText(format, args), true, null);
    }

    public static void fe(String format, Object... args) {
        printLog(LOG_LEVEL_ERROR, formatText(format, args), true, null);
    }

    public static void fi(String format, Object... args) {
        printLog(LOG_LEVEL_INFO, formatText(format, args), true, null);
    }

    public static void fv(String format, Object... args) {
        printLog(LOG_LEVEL_VERBOSE, formatText(format, args), true, null);
    }

    public static void fw(String format, Object... args) {
        printLog(LOG_LEVEL_WARNING, formatText(format, args), true, null);
    }

    public static void fwtf(String format, Object... args) {
        printLog(LOG_LEVEL_ASSERT, formatText(format, args), true, null);
    }

    public static void e(Throwable throwable) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(throwable), false, null);
    }

    public static void te(String tag, Throwable throwable) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(throwable), false, tag);
    }

    public static void fe(Throwable throwable) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(throwable), true, null);
    }

    public static void tfe(String tag, Throwable throwable) {
        printLog(LOG_LEVEL_ERROR, getMessageFromObject(throwable), true, tag);
    }

    private static void printLog(@LogLevel int logLevel,
                                 @Nullable String message,
                                 boolean printMethodName,
                                 @Nullable String tag) {
        if (!mLogLevels.contains(logLevel)) return;
        if (message == null) message = "";
        StackTraceElement element = new Throwable().getStackTrace()[CALL_STACK_INDEX];
        if (TextUtils.isEmpty(tag)) {
            tag = element.getClassName();
        }
        int lastDot = tag.lastIndexOf('.');
        if (lastDot >= 0) {
            tag = tag.substring(lastDot + 1);
        }
        int firstDollar = tag.indexOf('$');
        if (firstDollar > 0) {
            tag = tag.substring(0, firstDollar);
        }
        if (tag.length() > MAX_TAG_LENGTH && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            tag = tag.substring(0, MAX_TAG_LENGTH);
        }
        message = message.replaceAll("\r", "")
                         .replaceAll("\n+", "\n")
                         .trim();
        if (printMethodName) {
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
            String temp = message.substring(0, MAX_LOG_CHUNK_SIZE);
            int nextStartIndex = temp.lastIndexOf('\n');
            if (nextStartIndex <= 0) {
                nextStartIndex = MAX_LOG_CHUNK_SIZE;
            }
            temp = temp.substring(0, nextStartIndex).trim();
            logs.add(temp);
            message = message.substring(nextStartIndex).trim();
        }
        logs.add(message);
        for (String s : logs) {
            switch (logLevel) {
                case LOG_LEVEL_DEBUG:
                    android.util.Log.d(tag, s);
                    break;
                case LOG_LEVEL_ERROR:
                    android.util.Log.e(tag, s);
                    break;
                case LOG_LEVEL_INFO:
                    android.util.Log.i(tag, s);
                    break;
                case LOG_LEVEL_VERBOSE:
                    android.util.Log.v(tag, s);
                    break;
                case LOG_LEVEL_WARNING:
                    android.util.Log.w(tag, s);
                    break;
                case LOG_LEVEL_ASSERT:
                    android.util.Log.wtf(tag, s);
                    break;
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            LOG_LEVEL_DEBUG,
            LOG_LEVEL_ERROR,
            LOG_LEVEL_INFO,
            LOG_LEVEL_VERBOSE,
            LOG_LEVEL_WARNING,
            LOG_LEVEL_ASSERT
    })
    private @interface LogLevel {
    }
}

