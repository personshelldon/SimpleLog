/*
 * Modified by Vladyslav Lozytskyi on 11.04.18 1:37
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

import android.text.TextUtils;

import java.util.Locale;

public class Group {

    private String mGroupName;
    private String mText;
    private String mTag;

    /**
     * Create group for text and print it in one block
     *
     * @param group name of the group
     */
    public Group(String group) {
        mGroupName = group;
    }

    private void handleAppend(String text, Object... args) {
        if (!TextUtils.isEmpty(mText)) {
            mText += "\n\t";
        } else {
            mText = "";
        }
        if (args == null || args.length == 0) {
            mText += text;
        } else {
            mText += String.format(Locale.getDefault(), text, args);
        }
    }

    /**
     * Append new line to group
     *
     * @param text format text
     * @param args args to use with {@link String#format(String, Object...)}
     * @return current group
     */
    public Group append(String text, Object... args) {
        handleAppend(text, args);
        return this;
    }

    /**
     * Set custom tag to output in logcat
     *
     * @param tag custom tag for output log
     * @return current group
     */
    public Group tag(String tag) {
        mTag = tag;
        return this;
    }

    String getGroupName() {
        return mGroupName;
    }

    String getText() {
        return mText;
    }

    public String getTag() {
        return mTag;
    }
}
