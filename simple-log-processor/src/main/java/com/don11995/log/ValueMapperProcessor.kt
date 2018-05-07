/*
 * Modified by Vladyslav Lozytskyi on 07.05.18 13:39
 * Copyright (c) 2018. All rights reserved.
 */

package com.don11995.log

import java.io.IOException
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.ExecutableType

@SupportedAnnotationTypes("com.don11995.log.MapField",
        "com.don11995.log.MapFieldInner",
        "com.don11995.log.MapClass",
        "com.don11995.log.MapClassInner")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ValueMapperProcessor : AbstractProcessor() {

    companion object {
        private const val PACKAGE_NAME = "com.don11995.log"
        private const val CLASS_NAME = "ValueMapper"
    }

    private val mVariablesMapping = HashMap<String, MutableMap<Any, String>>()

    private fun getAndAddGroup(name: String): MutableMap<Any, String> {
        return mVariablesMapping.computeIfAbsent(name) { HashMap() }
    }

    private fun processMapField(roundEnvironment: RoundEnvironment) {
        for (element in roundEnvironment.getElementsAnnotatedWith(MapField::class.java)) {
            if (element.kind != ElementKind.FIELD) continue
            val variableElement = element as VariableElement
            val value = variableElement.constantValue
            val name = variableElement.simpleName.toString()
            val typeClass = variableElement.asType()
            val typeClassKind = typeClass.kind
            val typeClassName = variableElement.asType().toString()
            val annotation = variableElement.getAnnotation(MapField::class.java)
            val groupName = annotation.value
            if (groupName.isEmpty()) {
                throw IllegalArgumentException("@MapField \"value\" can not be empty")
            }
            if (!variableElement.modifiers.contains(Modifier.FINAL)) {
                throw IllegalArgumentException("To process @MapField annotation "
                        + "the variable \"$name\" must be final")
            }
            if (!typeClassKind.isPrimitive && "java.lang.String" != typeClassName) {
                throw IllegalArgumentException("To process @MapField annotation "
                        + "the variable \"$name\" must be a primitive or a String, "
                        + "but the variable is of type \"$typeClass\"")
            }
            if (value == null) {
                throw IllegalArgumentException("To process @MapField annotation "
                        + "the variable \"$name\" must not be null")
            }
            val groupMap = getAndAddGroup(groupName)
            groupMap[value] = name
        }
    }

    private fun processMapFieldInner(roundEnvironment: RoundEnvironment) {
        for (element in roundEnvironment.getElementsAnnotatedWith(MapFieldInner::class.java)) {
            if (element.kind != ElementKind.METHOD) continue
            val annotation = element.getAnnotation(MapFieldInner::class.java)
            val method = annotation.method
            val namesArray = annotation.names
            val executableMethod = element.asType() as ExecutableType
            if (method.isEmpty()) {
                throw IllegalArgumentException(
                        "@MapFieldInner \"method\" can not be empty")
            }
            if (namesArray.isEmpty()) {
                throw IllegalArgumentException(
                        "@MapFieldInner \"names\" can not be empty")
            }

            for (name in namesArray) {
                if (name.isEmpty()) {
                    throw IllegalArgumentException("@MapFieldInner some names are empty or null")
                }
            }

            if (executableMethod.parameterTypes.size != 1) {
                throw IllegalArgumentException("@MapFieldInner wrong number of method "
                        + "parameters. Must be 1")
            }

            val param = executableMethod.parameterTypes[0]
            val typeUtils = processingEnv.typeUtils
            val typeElement = typeUtils.asElement(param) as TypeElement

            val elementUtil = processingEnv.elementUtils
            val typeClass = elementUtil.getTypeElement(typeElement.qualifiedName
                    .toString())
            for (prefix in namesArray) {
                val groupMap = getAndAddGroup(method)
                for (enclosedElement in typeClass.enclosedElements) {
                    if (enclosedElement.kind != ElementKind.FIELD) continue
                    val variableElement = enclosedElement as VariableElement
                    val value = variableElement.constantValue
                    val name = variableElement.simpleName.toString()
                    if (value == null) continue
                    if (name == prefix) {
                        groupMap[value] = name
                    }
                }
            }
        }
    }

    private fun processMapClass(roundEnvironment: RoundEnvironment) {
        for (element in roundEnvironment.getElementsAnnotatedWith(MapClass::class.java)) {
            if (element.kind != ElementKind.CLASS && element.kind != ElementKind.INTERFACE) {
                continue
            }
            val annotation = element.getAnnotation(MapClass::class.java)
            val methodsArray = annotation.methods
            val prefixesArray = annotation.prefixes
            if (methodsArray.isEmpty() || methodsArray.size != prefixesArray.size) {
                throw IllegalArgumentException(
                        "@MapClass \"methods\" and \"prefixes\" arrays "
                                + "must be of the same size")
            }
            for (i in methodsArray.indices) {
                val method = methodsArray[i]
                val prefix = prefixesArray[i]
                if (method.isEmpty() || prefix.isEmpty()) {
                    throw IllegalArgumentException(
                            "@MapClass \"methods\" and \"prefixes\" can not have any empty "
                                    + "Strings")
                }
            }
            for (i in methodsArray.indices) {
                val method = methodsArray[i]
                val prefix = prefixesArray[i]
                val groupMap = getAndAddGroup(method)
                for (enclosedElement in element.enclosedElements) {
                    if (enclosedElement.kind != ElementKind.FIELD) continue
                    val variableElement = enclosedElement as VariableElement
                    val value = variableElement.constantValue
                    val name = variableElement.simpleName.toString()
                    if (value == null) continue
                    if (enclosedElement.getSimpleName().toString().startsWith(prefix)) {
                        groupMap[value] = name
                    }
                }
            }
        }
    }

    private fun processMapClassInner(roundEnvironment: RoundEnvironment) {
        for (element in roundEnvironment.getElementsAnnotatedWith(MapClassInner::class.java)) {
            if (element.kind != ElementKind.METHOD) continue
            val annotation = element.getAnnotation(MapClassInner::class.java)
            val methodsArray = annotation.methods
            val prefixesArray = annotation.prefixes
            val executableMethod = element.asType() as ExecutableType
            if (methodsArray.isEmpty() || methodsArray.size != prefixesArray.size) {
                throw IllegalArgumentException(
                        "@MapClassInner \"methods\" and \"prefixes\" arrays must be the same " + "length")
            }

            for (i in methodsArray.indices) {
                val func = methodsArray[i]
                val startsWith = prefixesArray[i]
                if (func.isEmpty() || startsWith.isEmpty()) {
                    throw IllegalArgumentException(
                            "@MapClassInner \"methods\" and \"prefixes\" can not have any empty "
                                    + "Strings")
                }
            }

            if (executableMethod.parameterTypes.size != 1) {
                throw IllegalArgumentException("@MapClassInner wrong number of method "
                        + "parameters. Must be 1")
            }

            val param = executableMethod.parameterTypes[0]
            val typeUtils = processingEnv.typeUtils
            val typeElement = typeUtils.asElement(param) as TypeElement

            val elementUtil = processingEnv.elementUtils
            val typeClass = elementUtil.getTypeElement(typeElement.qualifiedName
                    .toString())
            for (i in methodsArray.indices) {
                val func = methodsArray[i]
                val prefix = prefixesArray[i]
                val groupMap = getAndAddGroup(func)
                for (enclosedElement in typeClass.enclosedElements) {
                    if (enclosedElement.kind != ElementKind.FIELD) continue
                    val variableElement = enclosedElement as VariableElement
                    val value = variableElement.constantValue
                    val name = variableElement.simpleName.toString()
                    if (value == null) continue
                    if (name.startsWith(prefix)) {
                        groupMap[value] = name
                    }
                }
            }
        }
    }

    private fun createJavaFile(text: String) {
        try {
            val source = processingEnv.filer.createSourceFile("$PACKAGE_NAME.$CLASS_NAME")
            val writer = source.openWriter()
            writer.write(text)
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }

    }

    override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {

        val builder = StringBuilder()

        processMapField(roundEnvironment)
        processMapFieldInner(roundEnvironment)
        processMapClass(roundEnvironment)
        processMapClassInner(roundEnvironment)

        builder.append("package $PACKAGE_NAME;\n\n")
        builder.append("import java.util.HashMap;\n")
        builder.append("import java.util.Map;\n\n")
        builder.append("public class $CLASS_NAME {\n\n")
        builder.append("\tprivate static final Map<String, Map<String, String>> sMapping ")
        builder.append("= new HashMap<>();\n\n")
        builder.append("\tstatic {\n")

        for (group in mVariablesMapping.keys) {
            builder.append("\t\tsMapping.put(\"")
            builder.append(group)
            builder.append("\", new HashMap<String, String>());\n")
        }

        for ((groupName, values) in mVariablesMapping) {
            for ((key, value) in values) {
                builder.append("\t\tsMapping.get(\"")
                builder.append(groupName)
                builder.append("\").put(\"")
                builder.append(key)
                builder.append("\", \"")
                builder.append(value)
                builder.append("\");\n")
            }
        }

        builder.append("\t}\n\n")

        for (groupName in mVariablesMapping.keys) {
            builder.append("\tpublic static String ")
            builder.append(groupName)
            builder.append("(Object value) {\n")
            builder.append("\t\tString text = sMapping.get(\"")
            builder.append(groupName)
            builder.append("\").get(String.valueOf(value));\n")
            builder.append("\t\tif (text == null && value != null) {\n")
            builder.append("\t\t\ttext = value.toString();\n")
            builder.append("\t\t}\n")
            builder.append("\t\treturn String.valueOf(text);\n")
            builder.append("\t}\n\n")
        }

        builder.append("}")

        createJavaFile(builder.toString())

        return true
    }

}
