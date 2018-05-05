/*
 * Modified by Vladyslav Lozytskyi on 04.05.18 17:39
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class MapClassInner(val methods: Array<String>, val prefixes: Array<String>)