/*
 * Modified by Vladyslav Lozytskyi on 05.05.18 0:41
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class MapClass(val methods : Array<String>, val prefixes : Array<String>)
