/*
 * Modified by Vladyslav Lozytskyi on 05.05.18 16:55
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log.example

import android.media.AudioManager
import android.view.KeyEvent

import com.don11995.log.MapClassInner
import com.don11995.log.MapFieldInner

@Suppress("unused")
interface LogSettings {

    /**
     * Mapping key names in [KeyEvent] with their values
     * To get key name call ValueMapper.keyCode(int code)
     *
     * @param keyEvent value ignored, only class matters
     */
    @MapClassInner(methods = ["keyCode"], prefixes = ["KEYCODE"])
    fun mapKeyCodes(keyEvent: KeyEvent)

    /**
     * Mapping audio focus in [AudioManager] with their values
     * To get audio focus name call ValueMapper.audioFocus(int value)
     *
     * @param audioManager value ignored, only class matters
     */
    @MapFieldInner(method = "audioFocus", names = ["AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE", "AUDIOFOCUS_GAIN_TRANSIENT", "AUDIOFOCUS_LOSS", "AUDIOFOCUS_LOSS_TRANSIENT", "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK", "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK", "AUDIOFOCUS_GAIN"])
    fun audioFocus(audioManager: AudioManager)
}
