/*
 * Modified by Vladyslav Lozytskyi on 05.05.18 18:46
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log

fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any {
        it.annotationClass.qualifiedName == "kotlin.Metadata"
    }
}