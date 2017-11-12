package com.don11995.log.example;

import android.view.KeyEvent;

import com.don11995.log.MapClassInner;


public interface LogSettings {

    @MapClassInner(methods = "keyCode", prefixes = "KEYCODE")
    void mapKeyCodes(KeyEvent keyEvent);
}
