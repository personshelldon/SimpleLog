package com.don11995.log

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class SimpleLogProcessorTest {

    @Test
    @Throws(Throwable::class)
    fun testAnnotationProcessor() {
        val result = KotlinCompilation().apply {
            sources = listOf(constantsClass, logSettingsClass, testClass)

            annotationProcessors = listOf(ValueMapperProcessor())

            inheritClassPath = true
            messageOutputStream = System.out // see diagnostics in real time
        }.compile()
        val valueMapperClass = result.classLoader.loadClass("com.don11995.log.ValueMapper")
        assertNotNull(valueMapperClass)
        val valueMapperTestMethod = valueMapperClass.getDeclaredMethod("test", Any::class.java)
        assertNotNull(valueMapperTestMethod)
        val valueMapperExampleMethod = valueMapperClass.getDeclaredMethod("example", Any::class.java)
        assertNotNull(valueMapperExampleMethod)
        val valueMapperExampleMethod2 = valueMapperClass.getDeclaredMethod("example2", Any::class.java)
        assertNotNull(valueMapperExampleMethod2)
        val innerTestMethod = valueMapperClass.getDeclaredMethod("innerTest", Any::class.java)
        assertNotNull(innerTestMethod)
        val audioFocusMethod = valueMapperClass.getDeclaredMethod("audioFocus", Any::class.java)
        assertNotNull(innerTestMethod)

        val test0 = TEST_0
        val test1 = TEST_1
        val test2 = TEST_2
        val anotherTest3 = "3"
        val anotherTest4 = 4
        val exampleTest5 = 5.65f
        val exampleTest6 = '6'
        val exampleTest7 = 7
        val exampleTest8 = AUDIOFOCUS_GAIN_TRANSIENT

        val test0Mapped = valueMapperTestMethod.invoke(null, test0) as String
        val test1Mapped = valueMapperTestMethod.invoke(null, test1) as String
        val test2Mapped = valueMapperTestMethod.invoke(null, test2) as String
        val anotherTest3Mapped = valueMapperTestMethod.invoke(null, anotherTest3) as String
        val anotherTest4Mapped = valueMapperTestMethod.invoke(null, anotherTest4) as String
        val exampleTest5Mapped = valueMapperExampleMethod.invoke(null, exampleTest5) as String
        val exampleTest6Mapped = valueMapperExampleMethod.invoke(null, exampleTest6) as String
        val exampleTest7Mapped = valueMapperExampleMethod.invoke(null, exampleTest7) as String
        val exampleTest7Mapped2 = valueMapperExampleMethod2.invoke(null, exampleTest7) as String
        val innerTest0Mapped = innerTestMethod.invoke(null, test0) as String
        val innerTest1Mapped = innerTestMethod.invoke(null, test1) as String
        val innerTest2Mapped = innerTestMethod.invoke(null, test2) as String

        val unknown1 = valueMapperTestMethod.invoke(null, UNKNOWN) as String
        val nullString1 = valueMapperTestMethod.invoke(null, null as Any?) as String
        val unknown2 = valueMapperExampleMethod.invoke(null, UNKNOWN) as String
        val nullString2 = valueMapperExampleMethod.invoke(null, null as Any?) as String

        val exampleTest8Mapped = audioFocusMethod.invoke(null, exampleTest8) as String

        assertEquals("TEST_0", test0Mapped)
        assertEquals("TEST_1", test1Mapped)
        assertEquals("TEST_2", test2Mapped)
        assertEquals("ANOTHER_TEST_3", anotherTest3Mapped)
        assertEquals("ANOTHER_TEST_4", anotherTest4Mapped)
        assertEquals("EXAMPLE_TEST_5", exampleTest5Mapped)
        assertEquals("EXAMPLE_TEST_6", exampleTest6Mapped)
        assertEquals("EXAMPLE_TEST_7", exampleTest7Mapped)
        assertEquals("EXAMPLE_TEST_7", exampleTest7Mapped2)
        assertEquals("TEST_0", innerTest0Mapped)
        assertEquals("TEST_1", innerTest1Mapped)
        assertEquals("TEST_2", innerTest2Mapped)

        assertEquals("-1000", unknown1)
        assertEquals("null", nullString1)
        assertEquals("-1000", unknown2)
        assertEquals("null", nullString2)

        assertEquals("AUDIOFOCUS_GAIN_TRANSIENT", exampleTest8Mapped)
    }

    companion object {
        private const val TEST_0 = 0
        private const val TEST_1 = 1
        private const val TEST_2 = "2"
        private const val UNKNOWN = "-1000"
        private const val AUDIOFOCUS_GAIN_TRANSIENT = 1

        private val constantsClass = SourceFile.kotlin("Constants.kt", """
            package com.don11995.log
    
            @Suppress("unused")
            object Constants {
            
                const val AUDIOFOCUS_GAIN = 0
                const val AUDIOFOCUS_GAIN_TRANSIENT = 1
                const val AUDIOFOCUS_LOSS = 2
                const val AUDIOFOCUS_TRANSIENT = 3
                const val AUDIOFOCUS_TRANSIENT_CAN_DUCK = 4
            
            }
        """)
        private val logSettingsClass = SourceFile.kotlin("LogSettings.kt", """
            package com.don11995.log

            @Suppress("unused")
            interface LogSettings {
            
                @MapClassInner(methods = ["innerTest"], prefixes = ["TEST"])
                fun mapClassToTest(classToTest: ClassToTest)
            
                @MapFieldInner(method = "audioFocus", names = [
                    "AUDIOFOCUS_GAIN", 
                    "AUDIOFOCUS_GAIN_TRANSIENT", 
                    "AUDIOFOCUS_LOSS", 
                    "AUDIOFOCUS_TRANSIENT", 
                    "AUDIOFOCUS_TRANSIENT_CAN_DUCK"
                ])
                fun audioFocus(audioManager: Constants)
            }    
        """)
        private val testClass = SourceFile.kotlin("ClassToTest.kt", """
            @file:Suppress("unused")

            package com.don11995.log
            
            @MapClass(methods = ["test", "example"], prefixes = ["TEST", "EXAMPLE"])
            object ClassToTest {
            
                @MapField("example2")
                const val EXAMPLE_TEST_7 = 7
                const val TEST_0 = 0
                const val TEST_1 = 1
                const val TEST_2 = "2"
            
                @MapField("test")
                const val ANOTHER_TEST_3 = "3"
            
                @MapField("test")
                const val ANOTHER_TEST_4 = 4
                const val EXAMPLE_TEST_5 = 5.65f
                const val EXAMPLE_TEST_6 = '6'
                const val UNKNOWN = "-1000"
            
            }
        """)
    }
}
