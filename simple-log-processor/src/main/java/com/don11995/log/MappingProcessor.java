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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
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
public class MappingProcessor
        extends AbstractProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        StringBuilder builder = new StringBuilder();

        // generate groups from MapClass
        Map<String, Map<Object, String>> mVariablesMapping = new HashMap<>();
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
                        "@MapClass \"methods\" and \"startsWith\" arrays can not be empty and " +
                                "must be of the same " +
                                "size");
            }
            for (int i = 0; i < methodsArray.length; i++) {
                String method = methodsArray[i];
                String startsWith = prefixesArray[i];
                if (method.isEmpty() || startsWith.isEmpty()) {
                    throw new IllegalArgumentException(
                            "@MapClass \"methods\" and \"startsWith\" can not be empty");
                }
                Map<Object, String> groupMap = mVariablesMapping.get(method);
                if (groupMap == null) {
                    groupMap = new HashMap<>();
                }
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement.getKind() != ElementKind.FIELD) continue;
                    VariableElement variableElement = (VariableElement) enclosedElement;
                    if (enclosedElement.getSimpleName().toString().startsWith(startsWith)) {
                        groupMap.put(variableElement.getConstantValue(),
                                     variableElement.getSimpleName().toString());
                    }
                }
                mVariablesMapping.put(method, groupMap);
            }
        }

        // generate groups from MapField
        for (Element element : roundEnvironment.getElementsAnnotatedWith(MapField.class)) {
            if (element.getKind() != ElementKind.FIELD) continue;
            VariableElement variableElement = (VariableElement) element;
            MapField annotation = variableElement.getAnnotation(MapField.class);
            String group = annotation.value();
            if (group.isEmpty()) {
                throw new IllegalArgumentException("@MapField \"value\" can not be empty");
            }
            Map<Object, String> groupMap = mVariablesMapping.get(group);
            if (groupMap == null) {
                groupMap = new HashMap<>();
            }
            groupMap.put(variableElement.getConstantValue(), variableElement.getSimpleName()
                                                                            .toString());
            mVariablesMapping.put(group, groupMap);
        }

        // generate groups from MapClassInner
        for (Element element : roundEnvironment.getElementsAnnotatedWith(MapClassInner.class)) {
            if (element.getKind() != ElementKind.METHOD) continue;
            MapClassInner annotation = element.getAnnotation(MapClassInner.class);
            String[] methodsArray = annotation.methods();
            String[] prefixesArray = annotation.prefixes();
            if (methodsArray.length == 0 || methodsArray.length != prefixesArray.length) {
                throw new IllegalArgumentException(
                        "@MapClassInner  \"methods\" and \"startsWith\" arrays can not be empty "
                                + "and must be the same length");
            }
            ExecutableType method = (ExecutableType) element.asType();
            if (method.getParameterTypes().size() != 1) {
                throw new IllegalArgumentException("@MapClassInner wrong number of method "
                                                           + "parameters. Must be 1");
            }

            TypeMirror param = method.getParameterTypes().get(0);
            Types TypeUtils = this.processingEnv.getTypeUtils();
            TypeElement typeElement = (TypeElement) TypeUtils.asElement(param);

            Elements elementUtil = processingEnv.getElementUtils();
            TypeElement typeClass = elementUtil.getTypeElement(typeElement.getQualifiedName()
                                                                          .toString());
            for (int i = 0; i < methodsArray.length; i++) {
                String func = methodsArray[i];
                String startsWith = prefixesArray[i];
                if (func.isEmpty() || startsWith.isEmpty()) {
                    throw new IllegalArgumentException(
                            "@MapClassInner  \"methods\" and \"startsWith\" can not be empty");
                }
                for (Element enclosedElement : typeClass.getEnclosedElements()) {
                    if (enclosedElement.getKind() != ElementKind.FIELD) continue;
                    VariableElement variableElement = (VariableElement) enclosedElement;
                    if (enclosedElement.getSimpleName().toString().startsWith(startsWith)) {
                        Map<Object, String> groupMap = mVariablesMapping.get(func);
                        if (groupMap == null) {
                            groupMap = new HashMap<>();
                        }
                        groupMap.put(variableElement.getConstantValue(),
                                     variableElement.getSimpleName().toString());
                        mVariablesMapping.put(func, groupMap);
                    }
                }
            }
        }

        builder.append("package com.don11995.log;\n\n");
        builder.append("import java.util.HashMap;\n");
        builder.append("import java.util.Map;\n\n");
        builder.append("public class ValueMapper {\n\n");
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
            builder.append("\t\t\ttext = value.toString() + \" (unknown)\";\n");
            builder.append("\t\t}\n");
            builder.append("\t\treturn text;\n");
            builder.append("\t}\n\n");
        }

        builder.append("}");

        try {
            JavaFileObject source = processingEnv.getFiler()
                                                 .createSourceFile("com.don11995.log.ValueMapper");
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
        return true;
    }

}
