package zlc.season.rxroutercompiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import zlc.season.rxrouterannotation.Router;
import zlc.season.rxrouterannotation.Provider;
import zlc.season.rxrouterannotation.Uri;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@AutoService(Processor.class)
public class AnnotationProcess extends AbstractProcessor {
    private Messager messager;
    private Elements elementUtils;
    private Filer filer;

    private int moduleAnnotationSize = 0;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Uri.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> uriAnnotations = roundEnvironment.getElementsAnnotatedWith(Uri.class);
        Set<? extends Element> moduleAnnotations = roundEnvironment.getElementsAnnotatedWith(Router.class);
        moduleAnnotationSize += moduleAnnotations.size();

        for (Element element : moduleAnnotations) {
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            try {
                generateRoutingTable(packageName, element.getSimpleName().toString(), uriAnnotations);
            } catch (IOException e) {
                printError(e.getMessage());
            }
        }

        if (roundEnvironment.processingOver()) {
            if (moduleAnnotationSize == 0) {
                printError("You need to add a class that is annotated by @Router to your module!");
            }
            if (moduleAnnotationSize > 1) {
                printError("Too many classes annotated by @Router in your module!");
            }
        }

        return true;
    }

    private void generateRoutingTable(String packageName, String className, Set<? extends Element> uriAnnotations) throws IOException {
        TypeName classWithWildcard = ParameterizedTypeName.get(ClassName.get(Class.class),
                WildcardTypeName.subtypeOf(Object.class));

        TypeName hashMap = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ParameterizedTypeName.get(String.class),
                classWithWildcard);

        FieldSpec routerTable = FieldSpec
                .builder(hashMap, "table", PUBLIC, FINAL, STATIC)
                .initializer(CodeBlock.builder().add("new $T<>()", ClassName.get(HashMap.class)).build())
                .build();

        CodeBlock.Builder staticBlock = CodeBlock.builder();

        for (Element element : uriAnnotations) {
            TypeElement typeElement = (TypeElement) element;
            ClassName activity = ClassName.get(typeElement);
            String uri = element.getAnnotation(Uri.class).value();
            staticBlock.add("table.put($S,$T.class);\n", uri, activity);
        }

        MethodSpec provideMethod = MethodSpec.methodBuilder("provide")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Override.class).build())
                .addParameter(String.class, "uri")
                .returns(classWithWildcard)
                .addStatement("return table.get(uri)")
                .build();

        TypeSpec routerTableProvider = TypeSpec.classBuilder(className + "Provider")
                .addModifiers(PUBLIC, FINAL)
                .addSuperinterface(ClassName.get(Provider.class))
                .addField(routerTable)
                .addStaticBlock(staticBlock.build())
                .addMethod(provideMethod)
                .build();

        JavaFile.builder(packageName, routerTableProvider)
                .build()
                .writeTo(filer);
    }

    private void printError(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    private void printWaring(String waring) {
        messager.printMessage(Diagnostic.Kind.WARNING, waring);
    }
}
