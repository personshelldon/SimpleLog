/*
 * Modified by Vladyslav Lozytskyi on 12.02.18 13:14
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface MapFieldInner {
    String method();

    String[] names();
}
