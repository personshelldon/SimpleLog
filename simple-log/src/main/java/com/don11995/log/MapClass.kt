package com.don11995.log

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class MapClass(val methods: Array<String>, val prefixes: Array<String>)
