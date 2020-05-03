package com.don11995.log

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class MapClassInner(val methods: Array<String>, val prefixes: Array<String>)
