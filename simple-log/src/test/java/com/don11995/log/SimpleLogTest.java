/*
 * Modified by Vladyslav Lozytskyi on 4/10/18 12:57 AM
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

import android.os.Build;
import android.support.annotation.Nullable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import static com.don11995.log.LogAssert.assertLog;
import static com.google.common.truth.Truth.assertThat;

@Config(sdk = Build.VERSION_CODES.JELLY_BEAN)
@RunWith(RobolectricTestRunner.class)
public class SimpleLogTest {

    @Test
    public void testPrintFunctionNames() {
        SimpleLog.fd();
        SimpleLog.fi();
        SimpleLog.fe();
        SimpleLog.fv();
        SimpleLog.fw();
        SimpleLog.fwtf();
        assertLog().hasDebugMessage("SimpleLogTest", "testPrintFunctionNames()")
                .hasInfoMessage("SimpleLogTest", "testPrintFunctionNames()")
                .hasErrorMessage("SimpleLogTest", "testPrintFunctionNames()")
                .hasVerboseMessage("SimpleLogTest", "testPrintFunctionNames()")
                .hasWarnMessage("SimpleLogTest", "testPrintFunctionNames()")
                .hasAssertMessage("SimpleLogTest", "testPrintFunctionNames()")
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintSimpleLog() {
        SimpleLog.d("test1");
        SimpleLog.i("test2");
        SimpleLog.e("test3");
        SimpleLog.v("test4");
        SimpleLog.w("test5");
        SimpleLog.wtf("test6");
        assertLog().hasDebugMessage("SimpleLogTest", "test1")
                .hasInfoMessage("SimpleLogTest", "test2")
                .hasErrorMessage("SimpleLogTest", "test3")
                .hasVerboseMessage("SimpleLogTest", "test4")
                .hasWarnMessage("SimpleLogTest", "test5")
                .hasAssertMessage("SimpleLogTest", "test6")
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintWithFunctionName() {
        SimpleLog.fd("test1");
        SimpleLog.fi("test2");
        SimpleLog.fe("test3");
        SimpleLog.fv("test4");
        SimpleLog.fw("test5");
        SimpleLog.fwtf("test6");
        assertLog().hasDebugMessage("SimpleLogTest",
                "testPrintWithFunctionName() -> test1")
                .hasInfoMessage("SimpleLogTest",
                        "testPrintWithFunctionName() -> test2")
                .hasErrorMessage("SimpleLogTest",
                        "testPrintWithFunctionName() -> test3")
                .hasVerboseMessage("SimpleLogTest",
                        "testPrintWithFunctionName() -> test4")
                .hasWarnMessage("SimpleLogTest",
                        "testPrintWithFunctionName() -> test5")
                .hasAssertMessage("SimpleLogTest",
                        "testPrintWithFunctionName() -> test6")
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintGroup() {
        String finalResult = "--------TITLE--------\n"
                + "Line 1: arg1\n"
                + "Line 2: arg2\n"
                + "---------------------";
        String arg1 = "arg1";
        String arg2 = "arg2";
        Group group = new Group("TITLE")
                .append("Line 1: %s", arg1)
                .append("Line 2: %s", arg2);
        SimpleLog.d(group);
        SimpleLog.e(group);
        SimpleLog.i(group);
        SimpleLog.w(group);
        SimpleLog.v(group);
        SimpleLog.wtf(group);
        assertLog().hasDebugMessage("SimpleLogTest", finalResult)
                .hasErrorMessage("SimpleLogTest", finalResult)
                .hasInfoMessage("SimpleLogTest", finalResult)
                .hasWarnMessage("SimpleLogTest", finalResult)
                .hasVerboseMessage("SimpleLogTest", finalResult)
                .hasAssertMessage("SimpleLogTest", finalResult)
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintGroupWithFunctionName() {
        String finalResult = "testPrintGroupWithFunctionName() -> \n"
                + "--------TITLE--------\n"
                + "Line 1: arg1\n"
                + "Line 2: arg2\n"
                + "---------------------";
        String arg1 = "arg1";
        String arg2 = "arg2";
        Group group = new Group("TITLE")
                .append("Line 1: %s", arg1)
                .append("Line 2: %s", arg2);
        SimpleLog.fd(group);
        SimpleLog.fe(group);
        SimpleLog.fi(group);
        SimpleLog.fw(group);
        SimpleLog.fv(group);
        SimpleLog.fwtf(group);
        assertLog().hasDebugMessage("SimpleLogTest", finalResult)
                .hasErrorMessage("SimpleLogTest", finalResult)
                .hasInfoMessage("SimpleLogTest", finalResult)
                .hasWarnMessage("SimpleLogTest", finalResult)
                .hasVerboseMessage("SimpleLogTest", finalResult)
                .hasAssertMessage("SimpleLogTest", finalResult)
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintPriorities() {
        SimpleLog.disableAllLogs();
        SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_DEBUG, true);
        SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_INFO, true);
        SimpleLog.setLogLevelEnabled(SimpleLog.LOG_LEVEL_ASSERT, true);

        SimpleLog.d("test1");
        SimpleLog.i("test2");
        SimpleLog.e("test3");
        SimpleLog.v("test4");
        SimpleLog.w("test5");
        SimpleLog.wtf("test6");

        SimpleLog.disableAllLogs();

        SimpleLog.d("test7");
        SimpleLog.i("test8");
        SimpleLog.e("test9");
        SimpleLog.v("test10");
        SimpleLog.w("test11");
        SimpleLog.wtf("test12");

        assertLog().hasDebugMessage("SimpleLogTest", "test1")
                .hasInfoMessage("SimpleLogTest", "test2")
                .hasAssertMessage("SimpleLogTest", "test6")
                .hasNoMoreMessages();

        SimpleLog.enableAllLogs();
    }

    @Test
    public void testPrintVeryBigLog() {
        String finalLongText1 = Resources.LONG_TEXT.substring(0, 4000);
        String finalLongText2 = Resources.LONG_TEXT.substring(4000);
        String finalLongText3 = Resources.LONG_TEXT_3999;
        String finalLongText4 = Resources.LONG_TEXT_4000;

        SimpleLog.d(Resources.LONG_TEXT);
        SimpleLog.e(Resources.LONG_TEXT_3999);
        SimpleLog.i(Resources.LONG_TEXT_3999_N);
        SimpleLog.v(Resources.LONG_TEXT_4000);
        SimpleLog.w(Resources.LONG_TEXT_4000_N);

        assertLog().hasDebugMessage("SimpleLogTest", finalLongText1)
                .hasDebugMessage("SimpleLogTest", finalLongText2)
                .hasErrorMessage("SimpleLogTest", finalLongText3)
                .hasInfoMessage("SimpleLogTest", finalLongText3)
                .hasVerboseMessage("SimpleLogTest", finalLongText4)
                .hasWarnMessage("SimpleLogTest", finalLongText4)
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintVeryBigLogWithFunctionName() {
        String finalLongText1 = Resources.LONG_TEXT.substring(0, 3959);
        String finalLongText2 = Resources.LONG_TEXT.substring(3959);
        String finalLongText3 = Resources.LONG_TEXT_3999.substring(0, 3959);
        String finalLongText4 = Resources.LONG_TEXT_3999.substring(3959);
        String finalLongText5 = Resources.LONG_TEXT_4000.substring(0, 3959);
        String finalLongText6 = Resources.LONG_TEXT_4000.substring(3959);

        SimpleLog.fd(Resources.LONG_TEXT);
        SimpleLog.fe(Resources.LONG_TEXT_3999);
        SimpleLog.fi(Resources.LONG_TEXT_3999_N);
        SimpleLog.fv(Resources.LONG_TEXT_4000);
        SimpleLog.fw(Resources.LONG_TEXT_4000_N);

        assertLog().hasDebugMessage("SimpleLogTest",
                "testPrintVeryBigLogWithFunctionName() -> " + finalLongText1)
                .hasDebugMessage("SimpleLogTest", finalLongText2)
                .hasErrorMessage("SimpleLogTest",
                        "testPrintVeryBigLogWithFunctionName() -> " + finalLongText3)
                .hasErrorMessage("SimpleLogTest", finalLongText4)
                .hasInfoMessage("SimpleLogTest",
                        "testPrintVeryBigLogWithFunctionName() -> " + finalLongText3)
                .hasInfoMessage("SimpleLogTest", finalLongText4)
                .hasVerboseMessage("SimpleLogTest",
                        "testPrintVeryBigLogWithFunctionName() -> " + finalLongText5)
                .hasVerboseMessage("SimpleLogTest", finalLongText6)
                .hasWarnMessage("SimpleLogTest",
                        "testPrintVeryBigLogWithFunctionName() -> " + finalLongText5)
                .hasWarnMessage("SimpleLogTest", finalLongText6)
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintCustomTag() {
        String classTag = "SimpleLogTest";
        String finalTag = "CUSTOMTAG";
        String finalLog1 = "testPrintCustomTag()";
        String finalLog2 = "TestLog";
        String finalLog3 = "testPrintCustomTag() -> TestLog";

        SimpleLog.tfd(null);
        SimpleLog.tfd(finalTag);
        SimpleLog.td(finalTag, "TestLog");
        SimpleLog.tfd(finalTag, "TestLog");
        SimpleLog.tfe(null);
        SimpleLog.tfe(finalTag);
        SimpleLog.te(finalTag, "TestLog");
        SimpleLog.tfe(finalTag, "TestLog");
        SimpleLog.tfi(null);
        SimpleLog.tfi(finalTag);
        SimpleLog.ti(finalTag, "TestLog");
        SimpleLog.tfi(finalTag, "TestLog");
        SimpleLog.tfv(null);
        SimpleLog.tfv(finalTag);
        SimpleLog.tv(finalTag, "TestLog");
        SimpleLog.tfv(finalTag, "TestLog");
        SimpleLog.tfw(null);
        SimpleLog.tfw(finalTag);
        SimpleLog.tw(finalTag, "TestLog");
        SimpleLog.tfw(finalTag, "TestLog");
        SimpleLog.tfwtf(null);
        SimpleLog.tfwtf(finalTag);
        SimpleLog.twtf(finalTag, "TestLog");
        SimpleLog.tfwtf(finalTag, "TestLog");

        assertLog().hasDebugMessage(classTag, finalLog1)
                .hasDebugMessage(finalTag, finalLog1)
                .hasDebugMessage(finalTag, finalLog2)
                .hasDebugMessage(finalTag, finalLog3)
                .hasErrorMessage(classTag, finalLog1)
                .hasErrorMessage(finalTag, finalLog1)
                .hasErrorMessage(finalTag, finalLog2)
                .hasErrorMessage(finalTag, finalLog3)
                .hasInfoMessage(classTag, finalLog1)
                .hasInfoMessage(finalTag, finalLog1)
                .hasInfoMessage(finalTag, finalLog2)
                .hasInfoMessage(finalTag, finalLog3)
                .hasVerboseMessage(classTag, finalLog1)
                .hasVerboseMessage(finalTag, finalLog1)
                .hasVerboseMessage(finalTag, finalLog2)
                .hasVerboseMessage(finalTag, finalLog3)
                .hasWarnMessage(classTag, finalLog1)
                .hasWarnMessage(finalTag, finalLog1)
                .hasWarnMessage(finalTag, finalLog2)
                .hasWarnMessage(finalTag, finalLog3)
                .hasAssertMessage(classTag, finalLog1)
                .hasAssertMessage(finalTag, finalLog1)
                .hasAssertMessage(finalTag, finalLog2)
                .hasAssertMessage(finalTag, finalLog3)
                .hasNoMoreMessages();
    }

    @Test
    public void testPrintNull() {
        String finalTag = "SimpleLogTest";
        String finalLog = "testPrintNull()";

        SimpleLog.d(null);
        SimpleLog.d(null, (Object) null);
        SimpleLog.fd(null);
        SimpleLog.tfd(null);
        SimpleLog.tfd(null, null);

        assertLog().hasDebugMessage(finalTag, finalLog)
                .hasDebugMessage(finalTag, finalLog)
                .hasDebugMessage(finalTag, finalLog)
                .hasNoMoreMessages();
    }

    @SuppressWarnings("Convert2Lambda")
    @Test
    public void testPrintAbstractInnerClasses() {
        final String testTag = "SimpleLogTest";
        final String testLog = "TestLog";
        final String testLog2 = "onEvent() -> TestLog";
        final String testLog3 = "testPrintAbstractInnerClasses() -> TestLog";
        final String testLog4 = "null() -> TestLog";
        AbstractInnerClass.TestListener listener = () -> {
            SimpleLog.d(testLog);
            SimpleLog.fd(testLog);

        };
        AbstractInnerClass.TestListener listener2 = new AbstractInnerClass.TestListener() {
            @Override
            public void onEvent() {
                SimpleLog.d(testLog);
                SimpleLog.fd(testLog);
            }
        };

        AbstractInnerClass.TestListener listener3 = new AbstractInnerClass.TestListener() {
            @Override
            public void onEvent() {
                AbstractInnerClass.TestListener listener4 = new AbstractInnerClass.TestListener() {
                    @Override
                    public void onEvent() {
                        SimpleLog.d(testLog);
                        SimpleLog.fd(testLog);
                    }
                };
                listener4.onEvent();
            }
        };

        AbstractInnerClass.TestListener listener5 = () -> {
            AbstractInnerClass.TestListener listener6 = new AbstractInnerClass.TestListener() {
                @Override
                public void onEvent() {
                    SimpleLog.d(testLog);
                    SimpleLog.fd(testLog);
                }
            };
            listener6.onEvent();
        };

        AbstractInnerClass.TestListener listener7 = new AbstractInnerClass.TestListener() {
            @Override
            public void onEvent() {
                AbstractInnerClass.TestListener listener8 = () -> {
                    SimpleLog.d(testLog);
                    SimpleLog.fd(testLog);
                };
                listener8.onEvent();
            }
        };

        AbstractInnerClass.TestListener listener9 = () -> {
            AbstractInnerClass.TestListener listener10 = () -> {
                SimpleLog.d(testLog);
                SimpleLog.fd(testLog);
            };
            listener10.onEvent();
        };

        listener.onEvent();
        listener2.onEvent();
        listener3.onEvent();
        listener5.onEvent();
        listener7.onEvent();
        listener9.onEvent();

        assertLog()
                .hasDebugMessage(testTag, testLog)
                .hasDebugMessage(testTag, testLog3)
                .hasDebugMessage(testTag, testLog)
                .hasDebugMessage(testTag, testLog2)
                .hasDebugMessage(testTag, testLog)
                .hasDebugMessage(testTag, testLog2)
                .hasDebugMessage(testTag, testLog)
                .hasDebugMessage(testTag, testLog2)
                .hasDebugMessage(testTag, testLog)
                .hasDebugMessage(testTag, testLog2)
                .hasDebugMessage(testTag, testLog)
                .hasDebugMessage(testTag, testLog4)
                .hasNoMoreMessages();
    }

    @Test
    public void testLogProcessor() {
        LogProcessor logProcessor = new LogProcessor() {
            @Override
            public void processLog(String tag, String message, int priority, @Nullable Throwable e) {
                if (priority == SimpleLog.LOG_LEVEL_ERROR) return;
                SimpleLog.e("Log processor message");
            }
        };
        LogProcessor logProcessor2 = new LogProcessor() {
            @Override
            public void processLog(String tag, String message, int priority, @Nullable Throwable e) {
                assertThat(tag).isEqualTo("Test");
                assertThat(message).startsWith("java.io.IOException");
                assertThat(priority).isEqualTo(SimpleLog.LOG_LEVEL_WARNING);
                assertThat(e).isNotNull();
            }
        };
        SimpleLog.addLogProcessor(logProcessor);
        SimpleLog.d("Test1");
        SimpleLog.removeLogProcessor(logProcessor);
        SimpleLog.d("Test2");

        SimpleLog.addLogProcessor(logProcessor2);
        SimpleLog.tw("Test", new IOException("Test error"));
        SimpleLog.removeLogProcessor(logProcessor2);

        assertLog()
                .hasDebugMessage(getClass().getSimpleName(), "Test1")
                .hasErrorMessage(getClass().getSimpleName(), "Log processor message")
                .hasDebugMessage(getClass().getSimpleName(), "Test2")
                .hasWarnMessageStartsWith("Test", "java.io.IOException")
                .hasNoMoreMessages();

    }

    @Test
    public void testPrintExceptions() {
        SimpleLog.d(new IOException("Test"));
        SimpleLog.e(new IOException("Test2"));
        assertLog().hasDebugMessageStartsWith(getClass().getSimpleName(), "java.io.IOException: Test")
                .hasErrorMessageStartsWith(getClass().getSimpleName(), "java.io.IOException: Test2")
                .hasNoMoreMessages();
    }
}