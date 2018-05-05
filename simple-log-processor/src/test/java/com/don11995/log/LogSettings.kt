/*
 * Modified by Vladyslav Lozytskyi on 05.05.18 16:50
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log

@Suppress("unused")
interface LogSettings {

    @MapClassInner(methods = ["innerTest"], prefixes = ["TEST"])
    fun mapClassToTest(classToTest: ClassToTest)

    @MapFieldInner(method = "audioFocus", names = ["AUDIOFOCUS_GAIN", "AUDIOFOCUS_GAIN_TRANSIENT", "AUDIOFOCUS_LOSS", "AUDIOFOCUS_TRANSIENT", "AUDIOFOCUS_TRANSIENT_CAN_DUCK"])
    fun audioFocus(audioManager: Constants)
}
