/*
 * Modified by Vladyslav Lozytskyi on 04.05.18 17:40
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class MapFieldInner(val method : String, val names : Array<String>)
