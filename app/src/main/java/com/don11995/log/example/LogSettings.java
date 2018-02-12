/*
 * Modified by Vladyslav Lozytskyi on 12.02.18 13:08
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log.example;

import android.media.AudioManager;
import android.view.KeyEvent;

import com.don11995.log.MapClassInner;
import com.don11995.log.MapFieldInner;

public interface LogSettings {

    @MapClassInner(methods = "keyCode", prefixes = "KEYCODE")
    void mapKeyCodes(KeyEvent keyEvent);

    @MapFieldInner(method = "audioFocus", names = {
            "AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE",
            "AUDIOFOCUS_GAIN_TRANSIENT",
            "AUDIOFOCUS_LOSS",
            "AUDIOFOCUS_LOSS_TRANSIENT",
            "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK",
            "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK",
            "AUDIOFOCUS_GAIN"
    })
    void audioFocus(AudioManager audioManager);
}
