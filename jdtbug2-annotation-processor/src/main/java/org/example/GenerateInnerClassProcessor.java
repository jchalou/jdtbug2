package org.example;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes(
        "org.example.GenerateInnerClassAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class GenerateInnerClassProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {
            List<? extends Element> annotatedElements
                    = new ArrayList<>(roundEnv.getElementsAnnotatedWith(annotation));

            if (annotatedElements.isEmpty()) {
                continue;
            }

            String className = ((TypeElement) annotatedElements.get(0)
                    .getEnclosingElement()).getQualifiedName().toString();

            try {
                writeBuilderFile(className);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }

        return true;
    }

    private void writeBuilderFile(String className) throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String generatedClassName = className + "Gen";
        String generatedClassSimpleName = generatedClassName
                .substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(generatedClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(generatedClassSimpleName);
            out.println(" {");
            out.println();

            out.print("    public static class Inner {}");

            out.println();

            out.println("}");
        }
    }
}