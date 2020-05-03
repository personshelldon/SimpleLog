package com.don11995.log

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.don11995.log.LogAssert.Companion.assertLog
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@Config(sdk = [Build.VERSION_CODES.JELLY_BEAN])
@RunWith(AndroidJUnit4::class)
class SimpleLogTest {

    @Test
    fun testPrintFunctionNames() {
        SimpleLog.fd()
        SimpleLog.fi()
        SimpleLog.fe()
        SimpleLog.fv()
        SimpleLog.fw()
        SimpleLog.fwtf()
        assertLog(TAG).hasDebugMessage(TAG, "testPrintFunctionNames()")
                .hasInfoMessage(TAG, "testPrintFunctionNames()")
                .hasErrorMessage(TAG, "testPrintFunctionNames()")
                .hasVerboseMessage(TAG, "testPrintFunctionNames()")
                .hasWarnMessage(TAG, "testPrintFunctionNames()")
                .hasAssertMessage(TAG, "testPrintFunctionNames()")
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintSimpleLog() {
        SimpleLog.d("test1")
        SimpleLog.i("test2")
        SimpleLog.e("test3")
        SimpleLog.v("test4")
        SimpleLog.w("test5")
        SimpleLog.wtf("test6")
        assertLog(TAG).hasDebugMessage(TAG, "test1")
                .hasInfoMessage(TAG, "test2")
                .hasErrorMessage(TAG, "test3")
                .hasVerboseMessage(TAG, "test4")
                .hasWarnMessage(TAG, "test5")
                .hasAssertMessage(TAG, "test6")
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintWithFunctionName() {
        SimpleLog.fd("test1")
        SimpleLog.fi("test2")
        SimpleLog.fe("test3")
        SimpleLog.fv("test4")
        SimpleLog.fw("test5")
        SimpleLog.fwtf("test6")
        assertLog(TAG).hasDebugMessage(TAG,
                "testPrintWithFunctionName() -> test1")
                .hasInfoMessage(TAG,
                        "testPrintWithFunctionName() -> test2")
                .hasErrorMessage(TAG,
                        "testPrintWithFunctionName() -> test3")
                .hasVerboseMessage(TAG,
                        "testPrintWithFunctionName() -> test4")
                .hasWarnMessage(TAG,
                        "testPrintWithFunctionName() -> test5")
                .hasAssertMessage(TAG,
                        "testPrintWithFunctionName() -> test6")
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintGroup() {
        val finalResult = ("--------TITLE--------\n\t"
                + "Line 1: arg1\n\t"
                + "Line 2: arg2\n\t"
                + "---------------------")
        val arg1 = "arg1"
        val arg2 = "arg2"
        val group = Group("TITLE")
                .append("Line 1: %s", arg1)
                .append("Line 2: %s", arg2)
        SimpleLog.d(group)
        SimpleLog.e(group)
        SimpleLog.i(group)
        SimpleLog.w(group)
        SimpleLog.v(group)
        SimpleLog.wtf(group)
        assertLog(TAG).hasDebugMessage(TAG, finalResult)
                .hasErrorMessage(TAG, finalResult)
                .hasInfoMessage(TAG, finalResult)
                .hasWarnMessage(TAG, finalResult)
                .hasVerboseMessage(TAG, finalResult)
                .hasAssertMessage(TAG, finalResult)
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintGroupWithFunctionName() {
        val finalResult = ("testPrintGroupWithFunctionName() -> \n\t"
                + "--------TITLE--------\n\t"
                + "Line 1: arg1\n\t"
                + "Line 2: arg2\n\t"
                + "---------------------")
        val arg1 = "arg1"
        val arg2 = "arg2"
        val group = Group("TITLE")
                .append("Line 1: %s", arg1)
                .append("Line 2: %s", arg2)
        SimpleLog.fd(group)
        SimpleLog.fe(group)
        SimpleLog.fi(group)
        SimpleLog.fw(group)
        SimpleLog.fv(group)
        SimpleLog.fwtf(group)
        assertLog(TAG).hasDebugMessage(TAG, finalResult)
                .hasErrorMessage(TAG, finalResult)
                .hasInfoMessage(TAG, finalResult)
                .hasWarnMessage(TAG, finalResult)
                .hasVerboseMessage(TAG, finalResult)
                .hasAssertMessage(TAG, finalResult)
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintPriorities() {
        SimpleLog.disableAllLogs()
        SimpleLog.setLogLevelEnabled(LogLevel.DEBUG, true)
        SimpleLog.setLogLevelEnabled(LogLevel.INFO, true)
        SimpleLog.setLogLevelEnabled(LogLevel.ASSERT, true)

        SimpleLog.d("test1")
        SimpleLog.i("test2")
        SimpleLog.e("test3")
        SimpleLog.v("test4")
        SimpleLog.w("test5")
        SimpleLog.wtf("test6")

        SimpleLog.disableAllLogs()

        SimpleLog.d("test7")
        SimpleLog.i("test8")
        SimpleLog.e("test9")
        SimpleLog.v("test10")
        SimpleLog.w("test11")
        SimpleLog.wtf("test12")

        assertLog(TAG).hasDebugMessage(TAG, "test1")
                .hasInfoMessage(TAG, "test2")
                .hasAssertMessage(TAG, "test6")
                .hasNoMoreMessages()

        SimpleLog.enableAllLogs()
    }

    @Test
    fun testPrintVeryBigLog() {
        val finalLongText1 = Resources.LONG_TEXT.substring(0, 4000)
        val finalLongText2 = Resources.LONG_TEXT.substring(4000)
        val finalLongText3 = Resources.LONG_TEXT_3999
        val finalLongText4 = Resources.LONG_TEXT_4000

        SimpleLog.d(Resources.LONG_TEXT)
        SimpleLog.e(Resources.LONG_TEXT_3999)
        SimpleLog.i(Resources.LONG_TEXT_3999_N)
        SimpleLog.v(Resources.LONG_TEXT_4000)
        SimpleLog.w(Resources.LONG_TEXT_4000_N)

        assertLog(TAG).hasDebugMessage(TAG, finalLongText1)
                .hasDebugMessage(TAG, finalLongText2)
                .hasErrorMessage(TAG, finalLongText3)
                .hasInfoMessage(TAG, finalLongText3)
                .hasVerboseMessage(TAG, finalLongText4)
                .hasWarnMessage(TAG, finalLongText4)
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintVeryBigLogWithFunctionName() {
        val finalLongText1 = Resources.LONG_TEXT.substring(0, 3959)
        val finalLongText2 = Resources.LONG_TEXT.substring(3959)
        val finalLongText3 = Resources.LONG_TEXT_3999.substring(0, 3959)
        val finalLongText4 = Resources.LONG_TEXT_3999.substring(3959)
        val finalLongText5 = Resources.LONG_TEXT_4000.substring(0, 3959)
        val finalLongText6 = Resources.LONG_TEXT_4000.substring(3959)

        SimpleLog.fd(Resources.LONG_TEXT)
        SimpleLog.fe(Resources.LONG_TEXT_3999)
        SimpleLog.fi(Resources.LONG_TEXT_3999_N)
        SimpleLog.fv(Resources.LONG_TEXT_4000)
        SimpleLog.fw(Resources.LONG_TEXT_4000_N)

        assertLog(TAG).hasDebugMessage(TAG,
                "testPrintVeryBigLogWithFunctionName() -> $finalLongText1")
                .hasDebugMessage(TAG, finalLongText2)
                .hasErrorMessage(TAG,
                        "testPrintVeryBigLogWithFunctionName() -> $finalLongText3")
                .hasErrorMessage(TAG, finalLongText4)
                .hasInfoMessage(TAG,
                        "testPrintVeryBigLogWithFunctionName() -> $finalLongText3")
                .hasInfoMessage(TAG, finalLongText4)
                .hasVerboseMessage(TAG,
                        "testPrintVeryBigLogWithFunctionName() -> $finalLongText5")
                .hasVerboseMessage(TAG, finalLongText6)
                .hasWarnMessage(TAG,
                        "testPrintVeryBigLogWithFunctionName() -> $finalLongText5")
                .hasWarnMessage(TAG, finalLongText6)
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintCustomTag() {
        val classTag = TAG
        val finalTag = "CUSTOMTAG"
        val finalLog1 = "testPrintCustomTag()"
        val finalLog2 = "TestLog"
        val finalLog3 = "testPrintCustomTag() -> TestLog"

        SimpleLog.tfd(null)
        SimpleLog.tfd(finalTag)
        SimpleLog.td(finalTag, "TestLog")
        SimpleLog.tfd(finalTag, "TestLog")
        SimpleLog.tfe(null)
        SimpleLog.tfe(finalTag)
        SimpleLog.te(finalTag, "TestLog")
        SimpleLog.tfe(finalTag, "TestLog")
        SimpleLog.tfi(null)
        SimpleLog.tfi(finalTag)
        SimpleLog.ti(finalTag, "TestLog")
        SimpleLog.tfi(finalTag, "TestLog")
        SimpleLog.tfv(null)
        SimpleLog.tfv(finalTag)
        SimpleLog.tv(finalTag, "TestLog")
        SimpleLog.tfv(finalTag, "TestLog")
        SimpleLog.tfw(null)
        SimpleLog.tfw(finalTag)
        SimpleLog.tw(finalTag, "TestLog")
        SimpleLog.tfw(finalTag, "TestLog")
        SimpleLog.tfwtf(null)
        SimpleLog.tfwtf(finalTag)
        SimpleLog.twtf(finalTag, "TestLog")
        SimpleLog.tfwtf(finalTag, "TestLog")

        assertLog(classTag).hasDebugMessage(classTag, finalLog1)
                .hasErrorMessage(classTag, finalLog1)
                .hasInfoMessage(classTag, finalLog1)
                .hasVerboseMessage(classTag, finalLog1)
                .hasWarnMessage(classTag, finalLog1)
                .hasAssertMessage(classTag, finalLog1)
                .hasNoMoreMessages()

        assertLog(finalTag).hasDebugMessage(finalTag, finalLog1)
                .hasDebugMessage(finalTag, finalLog2)
                .hasDebugMessage(finalTag, finalLog3)
                .hasErrorMessage(finalTag, finalLog1)
                .hasErrorMessage(finalTag, finalLog2)
                .hasErrorMessage(finalTag, finalLog3)
                .hasInfoMessage(finalTag, finalLog1)
                .hasInfoMessage(finalTag, finalLog2)
                .hasInfoMessage(finalTag, finalLog3)
                .hasVerboseMessage(finalTag, finalLog1)
                .hasVerboseMessage(finalTag, finalLog2)
                .hasVerboseMessage(finalTag, finalLog3)
                .hasWarnMessage(finalTag, finalLog1)
                .hasWarnMessage(finalTag, finalLog2)
                .hasWarnMessage(finalTag, finalLog3)
                .hasAssertMessage(finalTag, finalLog1)
                .hasAssertMessage(finalTag, finalLog2)
                .hasAssertMessage(finalTag, finalLog3)
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintNull() {
        val finalTag = TAG
        val finalLog = "testPrintNull()"

        SimpleLog.d(null)
        SimpleLog.fd(null)
        SimpleLog.tfd(null)
        SimpleLog.tfd(null, null)

        assertLog(finalTag).hasDebugMessage(finalTag, finalLog)
                .hasDebugMessage(finalTag, finalLog)
                .hasDebugMessage(finalTag, finalLog)
                .hasNoMoreMessages()
    }

    @Test
    fun testLogProcessor() {
        val logProcessor = object : LogProcessor() {
            override fun processLog(tag: String,
                                    message: String,
                                    priority: LogLevel,
                                    e: Throwable?) {
                if (priority === LogLevel.ERROR) return
                SimpleLog.e("Log processor message")
            }
        }
        val logProcessor2 = object : LogProcessor() {
            override fun processLog(tag: String,
                                    message: String,
                                    priority: LogLevel,
                                    e: Throwable?) {
                assertThat(tag).isEqualTo("Test")
                assertThat(message).startsWith("java.io.IOException")
                assertThat(priority).isEqualTo(LogLevel.WARNING)
                assertThat(e).isNotNull()
            }
        }
        SimpleLog.addLogProcessor(logProcessor)
        SimpleLog.d("Test1")
        SimpleLog.removeLogProcessor(logProcessor)
        SimpleLog.d("Test2")

        SimpleLog.addLogProcessor(logProcessor2)
        SimpleLog.tw("Test", IOException("Test error"))
        SimpleLog.removeLogProcessor(logProcessor2)

        assertLog(TAG)
                .hasDebugMessage(TAG, "Test1")
                .hasErrorMessage(TAG, "Log processor message")
                .hasDebugMessage(TAG, "Test2")
                .hasNoMoreMessages()
        assertLog("Test")
                .hasWarnMessageStartsWith("Test", "java.io.IOException")
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintExceptions() {
        SimpleLog.d(IOException("Test"))
        SimpleLog.e(IOException("Test2"))
        assertLog(TAG).hasDebugMessageStartsWith(TAG, "java.io.IOException: Test")
                .hasErrorMessageStartsWith(TAG, "java.io.IOException: Test2")
                .hasNoMoreMessages()
    }

    @Test
    fun testPrintReferences() {
        SimpleLog.setPrintReferences(true)
        SimpleLog.d("Test")
        val codeLine1 = Thread.currentThread().stackTrace[1].lineNumber - 1
        JavaReference.printTestLog()
        val codeLine2 = 6
        SimpleLog.e(Group("Group").append("Test"))
        val codeLine3 = Thread.currentThread().stackTrace[1].lineNumber - 1
        SimpleLog.setPrintReferences(false)

        val groupLog = ("(" + TAG + ".kt:" + codeLine3 + ")\n\t"
                + "--------Group--------\n\t"
                + "Test\n\t"
                + "---------------------")

        assertLog(TAG).hasDebugMessage(TAG,
                "(" + TAG
                        + ".kt:" + codeLine1 + ") Test")
                .hasErrorMessage(TAG, groupLog)
                .hasNoMoreMessages()
        assertLog(JavaReference::class.java.simpleName)
                .hasErrorMessage(JavaReference::class.java.simpleName,
                        "(" + JavaReference::class.java.simpleName
                                + ".java:" + codeLine2 + ") Test")
                .hasNoMoreMessages()
    }

    @Test
    fun testTrimLog() {
        SimpleLog.d("Test ")
        SimpleLog.e(" Test ")
        SimpleLog.w(" Test \n\n")
        assertLog(TAG).hasDebugMessage(TAG, "Test")
                .hasErrorMessage(TAG, "Test")
                .hasWarnMessage(TAG, "Test")
                .hasNoMoreMessages()
    }

    companion object {
        private val TAG = SimpleLogTest::class.java.simpleName
    }
}
