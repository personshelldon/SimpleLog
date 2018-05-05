/*
 * Modified by Vladyslav Lozytskyi on 05.05.18 16:54
 * Copyright (c) 2018. All rights reserved.
 */

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
