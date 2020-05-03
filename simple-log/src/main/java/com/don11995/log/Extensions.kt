package com.don11995.log

fun Class<*>.isKotlinClass(): Boolean {
    return declaredAnnotations.any {
        it.annotationClass.qualifiedName == "kotlin.Metadata"
    }
}
