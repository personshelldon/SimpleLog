/*
 * Modified by Vladyslav Lozytskyi on 12.02.18 13:09
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

@MapClass(methods = {"test", "example"},
        prefixes = {"TEST", "EXAMPLE"})
class ClassToTest {

    static final int TEST_0 = 0;
    static final int TEST_1 = 1;
    static final String TEST_2 = "2";
    @MapField("test")
    static final String ANOTHER_TEST_3 = "3";
    @MapField("test")
    static final int ANOTHER_TEST_4 = 4;
    static final float EXAMPLE_TEST_5 = 5.65f;
    static final char EXAMPLE_TEST_6 = '6';
    static final String UNKNOWN = "-1000";
    @MapField("example2")
    final int EXAMPLE_TEST_7 = 7;

}
