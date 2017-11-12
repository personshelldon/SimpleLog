package com.don11995.log;

import android.text.TextUtils;

import java.util.Locale;

public class Group {

    private String mGroupName;
    private String mText;
    private String mTag;

    public Group(String group) {
        mGroupName = group;
    }

    private void handleAppend(String text, Object... args) {
        if (!TextUtils.isEmpty(mText)) {
            mText += '\n';
        } else {
            mText = "";
        }
        if (args == null || args.length == 0) {
            mText += text;
        } else {
            mText += String.format(Locale.getDefault(), text, args);
        }
    }

    public Group append(String text, Object... args) {
        handleAppend(text, args);
        return this;
    }

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
