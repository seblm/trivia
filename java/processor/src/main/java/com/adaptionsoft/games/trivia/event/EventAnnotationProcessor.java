package com.adaptionsoft.games.trivia.event;

import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@SupportedAnnotationTypes({EventAnnotationProcessor.PACKAGE + ".EventAnnotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EventAnnotationProcessor extends AbstractProcessor {

    public static final String PACKAGE = "com.adaptionsoft.games.trivia.event";

    private static final String CLASS_NAME = "EventsListener";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        TypeElement eventAnnotation = annotations.iterator().next();

        TypeSpec eventsListenerInterface = TypeSpec.interfaceBuilder(CLASS_NAME).addModifiers(Modifier.PUBLIC)
                .addField(createNoopListener(allEvents(roundEnv, eventAnnotation)))
                .addMethods(createContractMethods(roundEnv, eventAnnotation))
                .addMethod(createToEventMethod())
                .addMethod(createDispatchMethod(roundEnv, eventAnnotation))
                .build();

        JavaFile eventsListenerFile = JavaFile.builder(PACKAGE, eventsListenerInterface).indent("    ").build();

        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(PACKAGE + "." + CLASS_NAME);
            try (Writer out = sourceFile.openWriter()) {
                eventsListenerFile.writeTo(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

    private Stream<? extends Element> allEvents(RoundEnvironment roundEnv, TypeElement eventAnnotation) {
        return roundEnv.getElementsAnnotatedWith(eventAnnotation).stream()
                .sorted(Comparator.<Element, Boolean>comparing(element -> element.getAnnotation(EventAnnotation.class).unknownEvent())
                        .thenComparing(element -> element.getSimpleName().toString()));
    }

    private MethodSpec createDispatchMethod(RoundEnvironment roundEnv, TypeElement eventAnnotation) {
        final String eventVariable = "event";
        final String eventsListenersVariable = "eventsListeners";
        final String matcherVariable = "matcher";
        final Optional<? extends Element> unknownEvent = allEvents(roundEnv, eventAnnotation)
                .filter(event -> event.getAnnotation(EventAnnotation.class).unknownEvent())
                .findFirst();

        MethodSpec.Builder dispatchMethod = MethodSpec.methodBuilder("dispatch")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeVariableName.get("Event"))
                .addParameter(String.class, eventVariable)
                .varargs()
                .addParameter(ArrayTypeName.of(TypeVariableName.get(CLASS_NAME)), eventsListenersVariable);

        dispatchMethod.addStatement("$T $L", Matcher.class, matcherVariable)
                .addCode("\n");

        allEvents(roundEnv, eventAnnotation)
                .filter(event -> !event.getAnnotation(EventAnnotation.class).unknownEvent())
                .forEach(event -> {
                    String name = event.getSimpleName().toString().substring(0, 1).toLowerCase() + event.getSimpleName().toString().substring(1);
                    String pattern = event.getAnnotation(EventAnnotation.class).pattern();
                    dispatchMethod.addStatement("$L = $T.compile($S).matcher($L)", matcherVariable, Pattern.class, pattern, eventVariable);
                    dispatchMethod.beginControlFlow("if ($L.matches())", matcherVariable);
                    {
                        dispatchMethod.beginControlFlow("try");
                        {
                            createEventAndNotify(eventsListenersVariable, matcherVariable, dispatchMethod, event, name);
                        }
                        dispatchMethod.nextControlFlow("catch ($T e)", IllegalArgumentException.class);
                        {
                            if (unknownEvent.isPresent()) {
                                createEventAndNotify(eventsListenersVariable, eventVariable + "+ \": \" + e.getMessage()", dispatchMethod, unknownEvent.get(), "unknownEvent");
                            } else {
                                dispatchMethod.addStatement("return null");
                            }
                        }
                        dispatchMethod.endControlFlow();
                    }
                    dispatchMethod.endControlFlow();
                    dispatchMethod.addCode("\n");
                });

        if (unknownEvent.isPresent()) {
            createEventAndNotify(eventsListenersVariable, eventVariable, dispatchMethod, unknownEvent.get(), "unknownEvent");
        } else {
            dispatchMethod.addStatement("return null");
        }

        return dispatchMethod.build();
    }

    private void createEventAndNotify(String eventsListenersVariable, String matcherVariable, MethodSpec.Builder dispatchMethod, Element event, String name) {
        String eventsListenerVariable = eventsListenersVariable.substring(0, eventsListenersVariable.length() - 1);
        dispatchMethod.addStatement("$1T $2L = new $1T($3L)", event.asType(), name, matcherVariable);
        dispatchMethod.addStatement("$1T.stream($2L).forEach($3L -> $3L.on($4L))", Arrays.class, eventsListenersVariable, eventsListenerVariable, name);
        dispatchMethod.addStatement("return $L", name);
    }

    private MethodSpec createToEventMethod() {
        return MethodSpec.methodBuilder("toEvent")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeVariableName.get("Event"))
                .addParameter(String.class, "event")
                .addStatement("return dispatch(event, noopListener)").build();
    }

    private List<MethodSpec> createContractMethods(RoundEnvironment roundEnv, TypeElement eventAnnotation) {
        return allEvents(roundEnv, eventAnnotation)
                .map(event -> MethodSpec.methodBuilder("on")
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .returns(TypeName.VOID)
                        .addParameter(TypeName.get(event.asType()), "event")
                        .build())
                .collect(toList());
    }

    private FieldSpec createNoopListener(Stream<? extends Element> stream) {
        TypeSpec.Builder noopListener = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(TypeVariableName.get(CLASS_NAME));

        stream.forEach(event -> noopListener.addMethod(MethodSpec.methodBuilder("on")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(TypeName.get(event.asType()), "event")
                .build()));

        return FieldSpec.builder(TypeVariableName.get(CLASS_NAME), "noopListener", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$L", noopListener.build())
                .build();
    }
}