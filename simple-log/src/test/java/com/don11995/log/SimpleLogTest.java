package com.don11995.log;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.don11995.log.LogAssert.assertLog;

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
        assertLog().hasDebugMessage("SimpleLogTest", "testPrintWithFunctionName():"
                + "\ntest1")
                   .hasInfoMessage("SimpleLogTest", "testPrintWithFunctionName():"
                           + "\ntest2")
                   .hasErrorMessage("SimpleLogTest", "testPrintWithFunctionName():"
                           + "\ntest3")
                   .hasVerboseMessage("SimpleLogTest", "testPrintWithFunctionName():"
                           + "\ntest4")
                   .hasWarnMessage("SimpleLogTest", "testPrintWithFunctionName():"
                           + "\ntest5")
                   .hasAssertMessage("SimpleLogTest", "testPrintWithFunctionName():"
                           + "\ntest6")
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
        String finalLongText1 = Resources.LONG_TEXT.substring(0, 4000);
        String finalLongText2 = Resources.LONG_TEXT.substring(4000);
        String finalLongText3 = Resources.LONG_TEXT_3999;
        String finalLongText4 = Resources.LONG_TEXT_4000;

        SimpleLog.fd(Resources.LONG_TEXT);
        SimpleLog.fe(Resources.LONG_TEXT_3999);
        SimpleLog.fi(Resources.LONG_TEXT_3999_N);
        SimpleLog.fv(Resources.LONG_TEXT_4000);
        SimpleLog.fw(Resources.LONG_TEXT_4000_N);

        assertLog().hasDebugMessage("SimpleLogTest", "testPrintVeryBigLogWithFunctionName():")
                   .hasDebugMessage("SimpleLogTest", finalLongText1)
                   .hasDebugMessage("SimpleLogTest", finalLongText2)
                   .hasErrorMessage("SimpleLogTest", "testPrintVeryBigLogWithFunctionName():")
                   .hasErrorMessage("SimpleLogTest", finalLongText3)
                   .hasInfoMessage("SimpleLogTest", "testPrintVeryBigLogWithFunctionName():")
                   .hasInfoMessage("SimpleLogTest", finalLongText3)
                   .hasVerboseMessage("SimpleLogTest", "testPrintVeryBigLogWithFunctionName():")
                   .hasVerboseMessage("SimpleLogTest", finalLongText4)
                   .hasWarnMessage("SimpleLogTest", "testPrintVeryBigLogWithFunctionName():")
                   .hasWarnMessage("SimpleLogTest", finalLongText4)
                   .hasNoMoreMessages();
    }

    @Test
    public void testPrintCustomTag() {
        String classTag = "SimpleLogTest";
        String finalTag = "CUSTOMTAG";
        String finalLog1 = "testPrintCustomTag()";
        String finalLog2 = "TestLog";
        String finalLog3 = "testPrintCustomTag():\nTestLog";

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

    @Test
    public void testPrintAbstractInnerClasses() {
        final String testTag = "SimpleLogTest";
        final String testLog = "TestLog";
        final String testLog2 = "onEvent():\nTestLog";
        AbstractInnerClass.TestListener listener = new AbstractInnerClass.TestListener() {
            @Override
            public void onEvent() {
                SimpleLog.d(testLog);
                SimpleLog.fd(testLog);
                assertLog().hasDebugMessage(testTag, testLog)
                           .hasDebugMessage(testTag, testLog2)
                           .hasNoMoreMessages();
            }
        };
        listener.onEvent();
    }

}