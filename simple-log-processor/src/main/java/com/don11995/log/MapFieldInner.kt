package com.don11995.log

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class MapFieldInner(val method: String, val names: Array<String>)
