/*
 * Modified by Vladyslav Lozytskyi on 05.05.18 22:36
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log

import java.util.*

/**
 * Create group for text and print it in one block
 *
 * @param mGroupName name of the group
 */
class Group(internal val mGroupName : String) {

    internal var mText: String = ""
    internal var mTag: String? = null

    private fun handleAppend(text: String, vararg args: Any) {
        if (!mText.isEmpty()) {
            mText += "\n\t"
        }
        mText += if (args.isEmpty()) {
            text
        } else {
            String.format(Locale.getDefault(), text, *args)
        }
    }

    /**
     * Append new line to group
     *
     * @param text format text
     * @param args args to use with [String.format]
     * @return current group
     */
    fun append(text: String, vararg args: Any): Group {
        handleAppend(text, *args)
        return this
    }

    /**
     * Set custom tag to output in logcat
     *
     * @param tag custom tag for output log
     * @return current group
     */
    fun tag(tag: String): Group {
        mTag = tag
        return this
    }
}