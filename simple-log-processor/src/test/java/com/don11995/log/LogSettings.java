/*
 * Modified by Vladyslav Lozytskyi on 12.02.18 13:09
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

public interface LogSettings {

    @MapClassInner(methods = "innerTest", prefixes = "TEST")
    void mapClassToTest(ClassToTest classToTest);

    @MapFieldInner(method = "audioFocus", names = {
            "AUDIOFOCUS_GAIN",
            "AUDIOFOCUS_GAIN_TRANSIENT",
            "AUDIOFOCUS_LOSS",
            "AUDIOFOCUS_TRANSIENT",
            "AUDIOFOCUS_TRANSIENT_CAN_DUCK",
    })
    void audioFocus(Constants audioManager);
}
