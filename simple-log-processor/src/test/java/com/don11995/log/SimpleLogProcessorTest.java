/*
 * Modified by Vladyslav Lozytskyi on 19.03.18 12:57
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SimpleLogProcessorTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testAnnotationProcessor() throws Throwable {
        Class valueMapperClass = Class.forName("com.don11995.log.ValueMapper");
        assertNotNull(valueMapperClass);
        Method valueMapperTestMethod = valueMapperClass.getDeclaredMethod("test", Object.class);
        assertNotNull(valueMapperTestMethod);
        Method valueMapperExampleMethod = valueMapperClass.getDeclaredMethod("example", Object
                .class);
        assertNotNull(valueMapperExampleMethod);
        Method valueMapperExampleMethod2 = valueMapperClass.getDeclaredMethod("example2", Object
                .class);
        assertNotNull(valueMapperExampleMethod2);
        Method innerTestMethod = valueMapperClass.getDeclaredMethod("innerTest", Object.class);
        assertNotNull(innerTestMethod);
        Method audioFocusMethod = valueMapperClass.getDeclaredMethod("audioFocus", Object.class);
        assertNotNull(innerTestMethod);

        int test0 = ClassToTest.TEST_0;
        int test1 = ClassToTest.TEST_1;
        String test2 = ClassToTest.TEST_2;
        String anotherTest3 = "3";
        int anotherTest4 = 4;
        float exampleTest5 = 5.65f;
        char exampleTest6 = '6';
        int exampleTest7 = 7;
        int exampleTest8 = Constants.AUDIOFOCUS_GAIN_TRANSIENT;

        String test0Mapped = (String) valueMapperTestMethod.invoke(null, test0);
        String test1Mapped = (String) valueMapperTestMethod.invoke(null, test1);
        String test2Mapped = (String) valueMapperTestMethod.invoke(null, test2);
        String anotherTest3Mapped = (String) valueMapperTestMethod.invoke(null, anotherTest3);
        String anotherTest4Mapped = (String) valueMapperTestMethod.invoke(null, anotherTest4);
        String exampleTest5Mapped = (String) valueMapperExampleMethod.invoke(null, exampleTest5);
        String exampleTest6Mapped = (String) valueMapperExampleMethod.invoke(null, exampleTest6);
        String exampleTest7Mapped = (String) valueMapperExampleMethod.invoke(null, exampleTest7);
        String exampleTest7Mapped2 = (String) valueMapperExampleMethod2.invoke(null, exampleTest7);
        String innerTest0Mapped = (String) innerTestMethod.invoke(null, test0);
        String innerTest1Mapped = (String) innerTestMethod.invoke(null, test1);
        String innerTest2Mapped = (String) innerTestMethod.invoke(null, test2);

        String unknown1 = (String) valueMapperTestMethod.invoke(null, ClassToTest.UNKNOWN);
        String nullString1 = (String) valueMapperTestMethod.invoke(null, (Object) null);
        String unknown2 = (String) valueMapperExampleMethod.invoke(null, ClassToTest.UNKNOWN);
        String nullString2 = (String) valueMapperExampleMethod.invoke(null, (Object) null);

        String exampleTest8Mapped = (String) audioFocusMethod.invoke(null, exampleTest8);

        assertEquals("TEST_0", test0Mapped);
        assertEquals("TEST_1", test1Mapped);
        assertEquals("TEST_2", test2Mapped);
        assertEquals("ANOTHER_TEST_3", anotherTest3Mapped);
        assertEquals("ANOTHER_TEST_4", anotherTest4Mapped);
        assertEquals("EXAMPLE_TEST_5", exampleTest5Mapped);
        assertEquals("EXAMPLE_TEST_6", exampleTest6Mapped);
        assertEquals("EXAMPLE_TEST_7", exampleTest7Mapped);
        assertEquals("EXAMPLE_TEST_7", exampleTest7Mapped2);
        assertEquals("TEST_0", innerTest0Mapped);
        assertEquals("TEST_1", innerTest1Mapped);
        assertEquals("TEST_2", innerTest2Mapped);

        assertEquals("-1000", unknown1);
        assertEquals("null", nullString1);
        assertEquals("-1000", unknown2);
        assertEquals("null", nullString2);

        assertEquals("AUDIOFOCUS_GAIN_TRANSIENT", exampleTest8Mapped);
    }
}