package com.don11995.log;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes({
        "com.don11995.log.MapClass",
        "com.don11995.log.MapField",
        "com.don11995.log.MapClassInner"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ValueMapperProcessor
        extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.don11995.log";
    private static final String CLASS_NAME = "ValueMapper";

    private Map<String, Map<Object, String>> mVariablesMapping = new HashMap<>();

    private Map<Object, String> getAndAddGroup(String name) {
        return mVariablesMapping.computeIfAbsent(name, k -> new HashMap<>());
    }

    private void processMapField(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(MapField.class)) {
            if (element.getKind() != ElementKind.FIELD) continue;
            VariableElement variableElement = (VariableElement) element;
            Object value = variableElement.getConstantValue();
            String name = variableElement.getSimpleName().toString();
            TypeMirror typeClass = variableElement.asType();
            TypeKind typeClassKind = typeClass.getKind();
            String typeClassName = variableElement.asType().toString();
            MapField annotation = variableElement.getAnnotation(MapField.class);
            String groupName = annotation.value();
            if (groupName.isEmpty()) {
                throw new IllegalArgumentException("@MapField \"value\" can not be empty");
            }
            if (!variableElement.getModifiers().contains(Modifier.FINAL)) {
                throw new IllegalArgumentException("To process @MapField annotation the variable \""
                                                           + name
                                                           + "\" must be final");
            }
            if (!typeClassKind.isPrimitive()
                    && !"java.lang.String".equals(typeClassName)) {
                throw new IllegalArgumentException("To process @MapField annotation the variable \""
                                                           + name
                                                           + "\" must be a primitive or a String"
                                                           + ", but the variable is of type \""
                                                           + typeClass
                                                           + "\"");
            }
            if (value == null) {
                throw new IllegalArgumentException("To process @MapField annotation the variable \""
                                                           + name
                                                           + "\" must not be null");
            }
            Map<Object, String> groupMap = getAndAddGroup(groupName);
            groupMap.put(value, name);
        }
    }

    private void processMapClass(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(MapClass.class)) {
            if (element.getKind() != ElementKind.CLASS
                    && element.getKind() != ElementKind.INTERFACE) {
                continue;
            }
            MapClass annotation = element.getAnnotation(MapClass.class);
            String[] methodsArray = annotation.methods();
            String[] prefixesArray = annotation.prefixes();
            if (methodsArray.length == 0 || methodsArray.length != prefixesArray.length) {
                throw new IllegalArgumentException(
                        "@MapClass \"methods\" and \"prefixes\" arrays "
                                + "must be of the same size");
            }
            for (int i = 0; i < methodsArray.length; i++) {
                String method = methodsArray[i];
                String prefix = prefixesArray[i];
                if (method.isEmpty() || prefix.isEmpty()) {
                    throw new IllegalArgumentException(
                            "@MapClass \"methods\" and \"prefixes\" can not have any empty "
                                    + "Strings");
                }
            }
            for (int i = 0; i < methodsArray.length; i++) {
                String method = methodsArray[i];
                String prefix = prefixesArray[i];
                Map<Object, String> groupMap = getAndAddGroup(method);
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() != ElementKind.FIELD) continue;
                    VariableElement variableElement = (VariableElement) enclosedElement;
                    Object value = variableElement.getConstantValue();
                    String name = variableElement.getSimpleName().toString();
                    if (value == null) continue;
                    if (enclosedElement.getSimpleName().toString().startsWith(prefix)) {
                        groupMap.put(value, name);
                    }
                }
            }
        }
    }

    private void processMapClassInner(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(MapClassInner.class)) {
            if (element.getKind() != ElementKind.METHOD) continue;
            MapClassInner annotation = element.getAnnotation(MapClassInner.class);
            String[] methodsArray = annotation.methods();
            String[] prefixesArray = annotation.prefixes();
            ExecutableType executableMethod = (ExecutableType) element.asType();
            if (methodsArray.length == 0 || methodsArray.length != prefixesArray.length) {
                throw new IllegalArgumentException(
                        "@MapClassInner  \"methods\" and \"prefixes\" arrays must be the same length");
            }

            for (int i = 0; i < methodsArray.length; i++) {
                String func = methodsArray[i];
                String startsWith = prefixesArray[i];
                if (func.isEmpty() || startsWith.isEmpty()) {
                    throw new IllegalArgumentException(
                            "@MapClassInner  \"methods\" and \"prefixes\" can not have any empty "
                                    + "Strings");
                }
            }

            if (executableMethod.getParameterTypes().size() != 1) {
                throw new IllegalArgumentException("@MapClassInner wrong number of method "
                                                           + "parameters. Must be 1");
            }

            TypeMirror param = executableMethod.getParameterTypes().get(0);
            Types TypeUtils = processingEnv.getTypeUtils();
            TypeElement typeElement = (TypeElement) TypeUtils.asElement(param);

            Elements elementUtil = processingEnv.getElementUtils();
            TypeElement typeClass = elementUtil.getTypeElement(typeElement.getQualifiedName()
                                                                          .toString());
            for (int i = 0; i < methodsArray.length; i++) {
                String func = methodsArray[i];
                String prefix = prefixesArray[i];
                Map<Object, String> groupMap = getAndAddGroup(func);
                for (Element enclosedElement : typeClass.getEnclosedElements()) {
                    if (enclosedElement.getKind() != ElementKind.FIELD) continue;
                    VariableElement variableElement = (VariableElement) enclosedElement;
                    Object value = variableElement.getConstantValue();
                    String name = variableElement.getSimpleName().toString();
                    if (value == null) continue;
                    if (name.startsWith(prefix)) {
                        groupMap.put(value, name);
                    }
                }
            }
        }
    }

    private void createJavaFile(String text) {
        try {
            JavaFileObject source = processingEnv.getFiler()
                                                 .createSourceFile(PACKAGE_NAME + '.' +
                                                                           CLASS_NAME);
            Writer writer = source.openWriter();
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        StringBuilder builder = new StringBuilder();

        processMapField(roundEnvironment);
        processMapClass(roundEnvironment);
        processMapClassInner(roundEnvironment);

        builder.append("package " + PACKAGE_NAME + ";\n\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n\n");
        builder.append("public class " + CLASS_NAME + " {\n\n");
        builder.append("\tprivate static final Map<String, Map<String, String>> sMapping ");
        builder.append("= new HashMap<>();\n\n");
        builder.append("\tstatic {\n");

        for (String group : mVariablesMapping.keySet()) {
            builder.append("\t\tsMapping.put(\"");
            builder.append(group);
            builder.append("\", new HashMap<String, String>());\n");
        }

        for (Map.Entry<String, Map<Object, String>> entry : mVariablesMapping.entrySet()) {
            String groupName = entry.getKey();
            Map<Object, String> values = entry.getValue();
            for (Map.Entry<Object, String> var : values.entrySet()) {
                builder.append("\t\tsMapping.get(\"");
                builder.append(groupName);
                builder.append("\").put(\"");
                builder.append(var.getKey());
                builder.append("\", \"");
                builder.append(var.getValue());
                builder.append("\");\n");
            }
        }

        builder.append("\t}\n\n");

        for (String groupName : mVariablesMapping.keySet()) {
            builder.append("\tpublic static String ");
            builder.append(groupName);
            builder.append("(Object value) {\n");
            builder.append("\t\tString text = sMapping.get(\"");
            builder.append(groupName);
            builder.append("\").get(String.valueOf(value));\n");
            builder.append("\t\tif (text == null && value != null) {\n");
            builder.append("\t\t\ttext = value.toString();\n");
            builder.append("\t\t}\n");
            builder.append("\t\treturn String.valueOf(text);\n");
            builder.append("\t}\n\n");
        }

        builder.append("}");

        createJavaFile(builder.toString());

        return true;
    }

}
